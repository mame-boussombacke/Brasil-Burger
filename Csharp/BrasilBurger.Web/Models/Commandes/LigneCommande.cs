// Models/LigneCommande.cs
using System.ComponentModel.DataAnnotations;

namespace BrasilBurger.Models
{
    public class LigneCommande
    {
        public int Id { get; set; }
        
        [Required(ErrorMessage = "La commande est requise")]
        [Display(Name = "Commande")]
        public int? CommandeId { get; set; }
        public virtual Commande Commande { get; set; }
        
        // Un item peut être un burger, un menu ou un complément
        [Display(Name = "Burger")]
        public int? BurgerId { get; set; }
        public virtual Burger Burger { get; set; }
        
        [Display(Name = "Menu")]
        public int? MenuId { get; set; }
        public virtual Menu Menu { get; set; }
        
        [Display(Name = "Complément")]
        public int? ComplementId { get; set; }
        public virtual Complement Complement { get; set; }
        
        [Required(ErrorMessage = "La quantité est requise")]
        [Range(1, 100, ErrorMessage = "La quantité doit être entre 1 et 100")]
        [Display(Name = "Quantité")]
        public int? Quantite { get; set; } = 1;
        
        [Required(ErrorMessage = "Le prix unitaire est requis")]
        [Range(0.01, 100000, ErrorMessage = "Le prix doit être positif")]
        [Display(Name = "Prix unitaire (FCFA)")]
        public decimal? PrixUnitaire { get; set; }
        
        [Display(Name = "Sous-total")]
        public decimal? SousTotal => PrixUnitaire * Quantite;
    }
}