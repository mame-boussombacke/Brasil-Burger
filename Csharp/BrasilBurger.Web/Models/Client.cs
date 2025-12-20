// Models/Client.cs
using System.ComponentModel.DataAnnotations;

namespace BrasilBurger.Models
{
    public class Client
    {
        public int Id { get; set; }
        
        [Required(ErrorMessage = "Le nom est requis")]
        [Display(Name = "Nom")]
        public string? Nom { get; set; }
        
        [Required(ErrorMessage = "Le prénom est requis")]
        [Display(Name = "Prénom")]
        public string? Prenom { get; set; }
        
        [Required(ErrorMessage = "Le téléphone est requis")]
        [Phone(ErrorMessage = "Numéro de téléphone invalide")]
        [Display(Name = "Téléphone")]
        public string? Telephone { get; set; }
        
        [Required(ErrorMessage = "L'email est requis")]
        [EmailAddress(ErrorMessage = "Format email invalide")]
        [Display(Name = "Email")]
        public string? Email { get; set; }
        
        [Required(ErrorMessage = "Le mot de passe est requis")]
        [DataType(DataType.Password)]
        [MinLength(6, ErrorMessage = "Le mot de passe doit avoir au moins 6 caractères")]
        [Display(Name = "Mot de passe")]
        public string? Password { get; set; }
        
        public DateTime DateInscription { get; set; } = DateTime.UtcNow; 
        
        // Navigation properties
        public virtual ICollection<Commande> Commandes { get; set; }
        
        public Client()
        {
            Commandes = new List<Commande>();
        }
    }
}