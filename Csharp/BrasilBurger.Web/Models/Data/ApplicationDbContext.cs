// Data/ApplicationDbContext.cs
using Microsoft.EntityFrameworkCore;
using BrasilBurger.Models;

namespace BrasilBurger.Data
{
    public class ApplicationDbContext : DbContext
    {
        public ApplicationDbContext(DbContextOptions<ApplicationDbContext> options)
            : base(options)
        {
        }
        
        // Tables
        public DbSet<Client> Clients { get; set; }
        public DbSet<Burger> Burgers { get; set; }
        public DbSet<Complement> Complements { get; set; }
        public DbSet<Menu> Menus { get; set; }
        public DbSet<Commande> Commandes { get; set; }
        public DbSet<LigneCommande> LigneCommandes { get; set; }
        public DbSet<Paiement> Paiements { get; set; }
        
        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            base.OnModelCreating(modelBuilder);
            
            // Configuration pour PostgreSQL
            modelBuilder.Entity<Client>()
                .Property(c => c.DateInscription)
                .HasDefaultValueSql("CURRENT_TIMESTAMP");
                
            modelBuilder.Entity<Commande>()
                .Property(c => c.DateCommande)
                .HasDefaultValueSql("CURRENT_TIMESTAMP");
                
            modelBuilder.Entity<Paiement>()
                .Property(p => p.Date)
                .HasDefaultValueSql("CURRENT_TIMESTAMP");
            
            // UNE COMMANDE EST PAYÉE UNE SEULE FOIS
            modelBuilder.Entity<Commande>()
                .HasOne(c => c.Paiement)
                .WithOne(p => p.Commande)
                .HasForeignKey<Paiement>(p => p.CommandeId)
                .OnDelete(DeleteBehavior.Cascade);
            
            // Email unique
            modelBuilder.Entity<Client>()
                .HasIndex(c => c.Email)
                .IsUnique();
            
            // Numéro de commande unique
            modelBuilder.Entity<Commande>()
                .HasIndex(c => c.NumeroCommande)
                .IsUnique();
        }
    }
}