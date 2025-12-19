using BrasilBurger.Data;
using BrasilBurger.Services;
using Microsoft.EntityFrameworkCore;
using System.Linq;

var builder = WebApplication.CreateBuilder(args);

// Configuration Neon - UTILISEZ DefaultConnection
var connectionString = builder.Configuration.GetConnectionString("DefaultConnection");

// ✅ LOG pour déboguer (retirez après)
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
builder.Services.AddHttpContextAccessor();

// Session
builder.Services.AddDistributedMemoryCache();
builder.Services.AddSession();

// Contrôleurs
builder.Services.AddControllersWithViews();

var app = builder.Build();

// ✅ CONFIGURATION PORT RENDER (ESSENTIEL !)
var port = Environment.GetEnvironmentVariable("PORT") ?? "8080";
app.Urls.Add($"http://*:{port}");
Console.WriteLine($"🚀 Port configuré: {port}");

// ✅ Appliquer les migrations de base de données ou créer la base si elle n'existe pas
try
{
    using (var scope = app.Services.CreateScope())
    {
        var dbContext = scope.ServiceProvider.GetRequiredService<ApplicationDbContext>();
        Console.WriteLine("🔄 Vérification de la base de données...");
        
        // Vérifier si la base de données peut être connectée
        if (dbContext.Database.CanConnect())
        {
            Console.WriteLine("✅ Connexion à la base de données réussie");
            
            // Vérifier si des migrations existent
            try
            {
                var pendingMigrations = dbContext.Database.GetPendingMigrations().ToList();
                if (pendingMigrations.Any())
                {
                    Console.WriteLine($"🔄 Application de {pendingMigrations.Count} migration(s) en attente...");
                    dbContext.Database.Migrate();
                    Console.WriteLine("✅ Migrations appliquées avec succès");
                }
                else
                {
                    Console.WriteLine("✅ Base de données à jour (aucune migration en attente)");
                    
                    // Vérifier si les tables existent, sinon les créer
                    try
                    {
                        var hasTables = dbContext.Database.GetService<Microsoft.EntityFrameworkCore.Storage.IRelationalDatabaseCreator>().HasTables();
                        if (!hasTables)
                        {
                            Console.WriteLine("🔄 Création des tables (aucune migration trouvée)...");
                            dbContext.Database.EnsureCreated();
                            Console.WriteLine("✅ Tables créées avec succès");
                        }
                        else
                        {
                            Console.WriteLine("✅ Tables déjà existantes");
                        }
                    }
                    catch
                    {
                        // Si HasTables() échoue, essayer EnsureCreated de toute façon
                        Console.WriteLine("🔄 Création des tables (vérification HasTables échouée)...");
                        dbContext.Database.EnsureCreated();
                        Console.WriteLine("✅ Tables créées avec EnsureCreated");
                    }
                }
            }
            catch (Exception migrationEx)
            {
                // Si les migrations échouent, essayer EnsureCreated comme fallback
                Console.WriteLine($"⚠️ Erreur avec les migrations: {migrationEx.Message}");
                Console.WriteLine("🔄 Tentative de création des tables avec EnsureCreated...");
                try
                {
                    dbContext.Database.EnsureCreated();
                    Console.WriteLine("✅ Tables créées avec EnsureCreated");
                }
                catch (Exception ensureEx)
                {
                    Console.WriteLine($"⚠️ Erreur lors de la création des tables: {ensureEx.Message}");
                }
            }
        }
        else
        {
            Console.WriteLine("⚠️ Impossible de se connecter à la base de données");
        }
    }
}
catch (Exception ex)
{
    Console.WriteLine($"❌ Erreur lors de la vérification de la base de données: {ex.Message}");
    Console.WriteLine($"   Stack trace: {ex.StackTrace}");
    // Ne pas arrêter l'application - elle pourra démarrer même si la DB a des problèmes
}

// Configure the HTTP request pipeline.
if (!app.Environment.IsDevelopment())
{
    app.UseExceptionHandler("/Home/Error");
    // HSTS désactivé pour Render (géré par le proxy)
}

// HTTPS Redirection désactivé pour Render (géré par le proxy)
app.UseStaticFiles();
app.UseRouting();
app.UseSession(); // Doit être après UseRouting
app.UseAuthorization();

app.MapControllerRoute(
    name: "default",
    pattern: "{controller=Home}/{action=Index}/{id?}");

Console.WriteLine("✅ Application prête à démarrer");
app.Run();