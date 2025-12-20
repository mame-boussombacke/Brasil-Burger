// Models/Paiement.cs
using System.ComponentModel.DataAnnotations;

namespace BrasilBurger.Models
{
    public enum TypePaiement
    {
        Wave,
        OM
    }
    
    public class Paiement
    {
        public int Id { get; set; }
        
        [Display(Name = "Date")]
        public DateTime Date { get; set; } = DateTime.Now;
        
        [Required(ErrorMessage = "Le montant est requis")]
        [Range(0.01, 1000000, ErrorMessage = "Le montant doit être positif")]
        [Display(Name = "Montant (FCFA)")]
        public decimal? Montant { get; set; }
        
        [Required(ErrorMessage = "Le type de paiement est requis")]
        [Display(Name = "Type de paiement")]
        public TypePaiement Type { get; set; }
        
        // UNE COMMANDE PAYÉE UNE SEULE FOIS
        [Display(Name = "Commande")]
        public int? CommandeId { get; set; }
        public virtual Commande Commande { get; set; }
    }
}