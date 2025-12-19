using BrasilBurger.Data;
using BrasilBurger.Services;
using Microsoft.EntityFrameworkCore;

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

// Configure the HTTP request pipeline.
if (!app.Environment.IsDevelopment())
{
    app.UseExceptionHandler("/Home/Error");
    app.UseHsts();
}

app.UseHttpsRedirection();
app.UseStaticFiles();
app.UseRouting();
app.UseAuthorization();
app.UseSession();

app.MapControllerRoute(
    name: "default",
    pattern: "{controller=Home}/{action=Index}/{id?}");

Console.WriteLine("✅ Application prête à démarrer");
app.Run();