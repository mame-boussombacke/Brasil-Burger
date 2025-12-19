using BrasilBurger.Models;
namespace BrasilBurger.Services
{
    public interface IPanierService
    {
        List<PanierItem> GetPanier();
        void AjouterAuPanier(PanierItem item);
        void UpdateQuantite(int produitId, string typeProduit, int quantite);
        void Supprimer(int produitId, string typeProduit);
        decimal GetTotal();
        int GetNombreItems();
        void ViderPanier();
    }
}