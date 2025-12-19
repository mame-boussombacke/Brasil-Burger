// Models/Menu.cs
using System.ComponentModel.DataAnnotations;

namespace BrasilBurger.Models
{
    public class Menu
    {
        public int Id { get; set; }
        
        [Required(ErrorMessage = "Le nom est requis")]
        [Display(Name = "Nom du menu")]
        public string Nom { get; set; }
        
        [Display(Name = "Image URL")]
        public string ImageUrl { get; set; }
        
        [Display(Name = "Disponible")]
        public bool EstDisponible { get; set; } = true;
        
        // Relations avec les éléments du menu
        [Required(ErrorMessage = "Le burger est requis")]
        [Display(Name = "Burger")]
        public int BurgerId { get; set; }
        public virtual Burger Burger { get; set; }
        
        [Display(Name = "Boisson")]
        public int? BoissonId { get; set; }
        public virtual Complement Boisson { get; set; }
        
        [Display(Name = "Frites")]
        public int? FritesId { get; set; }
        public virtual Complement Frites { get; set; }
        
        // Prix calculé (SOMME DES PRIX - demandé)
        [Display(Name = "Prix total")]
        public decimal PrixTotal =>
            (Burger?.Prix ?? 0) +
            (Boisson?.Prix ?? 0) +
            (Frites?.Prix ?? 0);
    }
}