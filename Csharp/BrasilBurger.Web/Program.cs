// Program.cs - Ajouter PaiementService
using BrasilBurger.Data;
using BrasilBurger.Services;
using Microsoft.EntityFrameworkCore;

var builder = WebApplication.CreateBuilder(args);

// Configuration Neon
var connectionString = builder.Configuration.GetConnectionString("NeonConnection");
builder.Services.AddDbContext<ApplicationDbContext>(options =>
    options.UseNpgsql(connectionString));

// ✅ TOUS LES SERVICES
builder.Services.AddScoped<IPanierService, PanierService>();
builder.Services.AddScoped<ICommandeService, CommandeService>();
builder.Services.AddScoped<IPaiementService, PaiementService>(); // Nouveau
builder.Services.AddHttpContextAccessor();

// Session
builder.Services.AddDistributedMemoryCache();
builder.Services.AddSession();

// Contrôleurs
builder.Services.AddControllersWithViews();

var app = builder.Build();

// ... reste du code inchangé