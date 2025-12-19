// Models/Commande.cs
using System.ComponentModel.DataAnnotations;

namespace BrasilBurger.Models
{
    public enum EtatCommande
    {
        EnAttente,
        Validee,
        EnPreparation,
        Prete,
        EnLivraison,
        Terminee,
        Annulee
    }
    
    public enum TypeLivraison
    {
        SurPlace,
        AEmporter,
        Livraison
    }
    
    public class Commande
    {
        public int Id { get; set; }
        
        [Display(Name = "N° Commande")]
        public string NumeroCommande { get; set; } = "CMD-" + DateTime.Now.ToString("yyyyMMddHHmmss");
        
        [Display(Name = "Date commande")]
        public DateTime DateCommande { get; set; } = DateTime.Now;
        
        [Display(Name = "État")]
        public EtatCommande Etat { get; set; } = EtatCommande.EnAttente;
        
        [Required(ErrorMessage = "Le type de livraison est requis")]
        [Display(Name = "Type de service")]
        public TypeLivraison TypeLivraison { get; set; }
        
        [Display(Name = "Adresse livraison")]
        public string AdresseLivraison { get; set; }
        
        [Display(Name = "Zone livraison")]
        public string ZoneLivraison { get; set; }
        
        [Required(ErrorMessage = "Le total est requis")]
        [Range(0.01, 1000000, ErrorMessage = "Le total doit être positif")]
        [Display(Name = "Total (FCFA)")]
        public decimal Total { get; set; }
        
        // Relations
        [Display(Name = "Client")]
        public int ClientId { get; set; }
        public virtual Client Client { get; set; }
        
        // Navigation properties
        public virtual ICollection<LigneCommande> LigneCommandes { get; set; }
        public virtual Paiement Paiement { get; set; } // UN SEUL paiement
    }
}