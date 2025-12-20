// Services/ICommandeService.cs
using BrasilBurger.Models;

namespace BrasilBurger.Services
{
    public interface ICommandeService
    {
        Task<Commande> CreerCommandeAsync(int clientId, List<PanierItem> panier,
                                        TypeLivraison typeLivraison,
                                        string? adresse = null, string? zone = null);
        
        Task<Paiement> CreerPaiementAsync(int commandeId, TypePaiement typePaiement);
        
        Task<List<Commande>> GetCommandesClientAsync(int clientId);
        
        // ✅ CORRIGÉ : La commande peut être null si non trouvée
        Task<Commande?> GetCommandeDetailsAsync(int commandeId, int clientId);
    }
}