// Models/Complement.cs
using System.ComponentModel.DataAnnotations;

namespace BrasilBurger.Models
{
    public class Complement
    {
        public int Id { get; set; }
        
        [Required(ErrorMessage = "Le nom est requis")]
        [Display(Name = "Nom du complément")]
        public string Nom { get; set; }
        
        [Required(ErrorMessage = "Le prix est requis")]
        [Range(0.01, 100000, ErrorMessage = "Le prix doit être positif")]
        [Display(Name = "Prix (FCFA)")]
        public decimal Prix { get; set; }
        
        [Display(Name = "Image URL")]
        public string ImageUrl { get; set; }
        
        [Display(Name = "Disponible")]
        public bool EstDisponible { get; set; } = true;
        
        // Navigation properties
        public virtual ICollection<LigneCommande> LigneCommandes { get; set; }
    }
}