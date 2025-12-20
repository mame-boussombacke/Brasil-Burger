using BrasilBurger.Data;
using BrasilBurger.Services;
using Microsoft.EntityFrameworkCore;
using System.Linq;

var builder = WebApplication.CreateBuilder(args);

// Configuration Neon
var connectionString = builder.Configuration.GetConnectionString("DefaultConnection");

Console.WriteLine($"🔧 ConnectionString: {connectionString?.Substring(0, Math.Min(50, connectionString?.Length ?? 0))}...");

if (string.IsNullOrEmpty(connectionString))
{
    Console.WriteLine("❌ ERREUR: ConnectionString est vide!");
}
else
{
    Console.WriteLine("✅ ConnectionString chargée");
}

builder.Services.AddDbContext<ApplicationDbContext>(options =>
    options.UseNpgsql(connectionString));

// Services
builder.Services.AddScoped<IPanierService, PanierService>();
builder.Services.AddScoped<ICommandeService, CommandeService>();
builder.Services.AddScoped<IPaiementService, PaiementService>();
builder.Services.AddScoped<IImageService, ImageService>();
builder.Services.AddHttpContextAccessor();

// Session
builder.Services.AddDistributedMemoryCache();
builder.Services.AddSession();

// Contrôleurs
builder.Services.AddControllersWithViews();

var app = builder.Build();

// ✅ CONFIGURATION PORT RENDER
var port = Environment.GetEnvironmentVariable("PORT") ?? "8080";
app.Urls.Add($"http://*:{port}");
Console.WriteLine($"🚀 Port configuré: {port}");

// ============================================
// ✅ INITIALISATION DE LA BASE + SEEDDATA
// ============================================
try
{
    using (var scope = app.Services.CreateScope())
    {
        var dbContext = scope.ServiceProvider.GetRequiredService<ApplicationDbContext>();
        Console.WriteLine("🔄 Initialisation de la base de données...");
        
        // 1. VÉRIFIER/CREER LES TABLES (sans migrations complexes)
        Console.WriteLine("📁 Création des tables si nécessaire...");
        await dbContext.Database.EnsureCreatedAsync();
        Console.WriteLine("✅ Tables vérifiées/créées");
        
        // 2. NETTOYER LES VIEILLES DONNÉES "Test Burger"
        var testBurgers = await dbContext.Burgers
            .Where(b => b.Nom.Contains("Test") || b.Nom.Contains("ECE") || b.Nom.Contains("Writ"))
            .ToListAsync();
            
        if (testBurgers.Any())
        {
            Console.WriteLine($"🧹 Suppression de {testBurgers.Count} vieux burgers de test...");
            dbContext.Burgers.RemoveRange(testBurgers);
            await dbContext.SaveChangesAsync();
        }
        
        // 3. COMPTER CE QUI EXISTE
        var burgerCount = await dbContext.Burgers.CountAsync();
        var menuCount = await dbContext.Menus.CountAsync();
        var complementCount = await dbContext.Complements.CountAsync();
        
        Console.WriteLine($"📊 État actuel: {burgerCount} burgers, {menuCount} menus, {complementCount} compléments");
        
        // 4. EXÉCUTER SEEDDATA SEULEMENT SI VIDE
        if (burgerCount == 0 && menuCount == 0)
        {
            Console.WriteLine("🌱 Aucune donnée trouvée, exécution de SeedData...");
            try
            {
                SeedData.Initialize(dbContext);
                Console.WriteLine("✅ SeedData exécuté avec succès");
            }
            catch (Exception seedEx)
            {
                Console.WriteLine($"❌ Erreur SeedData: {seedEx.Message}");
            }
        }
        else
        {
            Console.WriteLine("ℹ️ Données déjà présentes, SeedData ignoré");
            
            // DEBUG: Afficher ce qu'il y a
            var burgers = await dbContext.Burgers.ToListAsync();
            Console.WriteLine("📋 Burgers en base:");
            foreach (var burger in burgers)
            {
                var status = string.IsNullOrEmpty(burger.ImageUrl) ? "❌ SANS IMAGE" : "✅ AVEC IMAGE";
                Console.WriteLine($"  - {burger.Id}: {burger.Nom} - {status}");
            }
        }
    }
}
catch (Exception ex)
{
    Console.WriteLine($"❌ Erreur initialisation base: {ex.Message}");
    Console.WriteLine($"🔍 Détails: {ex.StackTrace}");
}

// ============================================
// ✅ MIDDLEWARE
// ============================================
if (!app.Environment.IsDevelopment())
{
    app.UseExceptionHandler("/Home/Error");
}

app.UseStaticFiles();
app.UseRouting();
app.UseSession();
app.UseAuthorization();

app.MapControllerRoute(
    name: "default",
    pattern: "{controller=Home}/{action=Index}/{id?}");

// ============================================
// ✅ ENDPOINTS DE DIAGNOSTIC
// ============================================
app.MapGet("/api/debug", async (ApplicationDbContext db) =>
{
    var burgers = await db.Burgers.ToListAsync();
    var menus = await db.Menus
        .Include(m => m.Burger)
        .Include(m => m.Frites)
        .Include(m => m.Boisson)
        .ToListAsync();
    
    return new
    {
        Database = db.Database.GetDbConnection().Database,
        Time = DateTime.UtcNow,
        Burgers = burgers.Select(b => new {
            b.Id,
            b.Nom,
            b.Prix,
            b.EstDisponible,
            b.ImageUrl,
            HasImage = !string.IsNullOrEmpty(b.ImageUrl)
        }),
        Menus = menus.Select(m => new {
            m.Id,
            m.Nom,
            m.PrixTotal,
            m.EstDisponible,
            m.ImageUrl,
            Burger = m.Burger?.Nom,
            Frites = m.Frites?.Nom,
            Boisson = m.Boisson?.Nom
        })
    };
});

app.MapPost("/api/reset", async (ApplicationDbContext db) =>
{
    try
    {
        // Supprimer toutes les données
        db.Burgers.RemoveRange(db.Burgers);
        db.Menus.RemoveRange(db.Menus);
        db.Complements.RemoveRange(db.Complements);
        
        await db.SaveChangesAsync();
        
        // Ré-exécuter SeedData
        SeedData.Initialize(db);
        
        return Results.Ok(new {
            success = true,
            message = "Base réinitialisée! Redémarrez l'application." 
        });
    }
    catch (Exception ex)
    {
        return Results.Problem($"Erreur: {ex.Message}");
    }
});

Console.WriteLine("🚀 Application démarrée avec succès");
await app.RunAsync();