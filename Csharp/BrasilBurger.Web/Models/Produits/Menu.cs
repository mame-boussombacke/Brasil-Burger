// Models/Menu.cs
using System.ComponentModel.DataAnnotations;

namespace BrasilBurger.Models
{
    public class Menu
    {
        public int Id { get; set; }
        
        [Required(ErrorMessage = "Le nom est requis")]
        [Display(Name = "Nom du menu")]
        public string Nom { get; set; } = string.Empty; // Initialisation
        
        [Display(Name = "Image URL")]
        public string? ImageUrl { get; set; } // Rendue nullable
        
        [Display(Name = "Disponible")]
        public bool EstDisponible { get; set; } = true;
        
        public string? Description { get; set; }
        
        // Relations avec les éléments du menu
        [Required(ErrorMessage = "Le burger est requis")]
        [Display(Name = "Burger")]
        public int BurgerId { get; set; } // Changez en int (pas nullable si requis)
        
        public virtual Burger? Burger { get; set; } // Rendez nullable
        
        [Display(Name = "Boisson")]
        public int? BoissonId { get; set; }
        public virtual Complement? Boisson { get; set; } // Rendez nullable
        
        [Display(Name = "Frites")]
        public int? FritesId { get; set; }
        public virtual Complement? Frites { get; set; } // Rendez nullable
        
        // Prix calculé (SOMME DES PRIX - demandé)
        [Display(Name = "Prix total")]
        public decimal PrixTotal { get; set; }
        
        // Méthode pour calculer automatiquement le prix total
        public decimal CalculerPrixTotal()
        {
            decimal total = 0;
            total += Burger?.Prix ?? 0;
            total += Boisson?.Prix ?? 0;
            total += Frites?.Prix ?? 0;
            return total;
        }
        
        // Constructeur pour éviter les warnings
        public Menu()
        {
            Nom = string.Empty;
        }
    }
}