using System.Text.Json;
using BrasilBurger.Models;

namespace BrasilBurger.Services
{
    public class PanierService : IPanierService
    {
        private readonly IHttpContextAccessor _httpContextAccessor;
        private const string PanierKey = "PanierItems";
        
        public PanierService(IHttpContextAccessor httpContextAccessor)
        {
            _httpContextAccessor = httpContextAccessor;
        }
        
        public List<PanierItem> GetPanier()
        {
            var session = _httpContextAccessor.HttpContext.Session;
            var panierJson = session.GetString(PanierKey);
            
            if (string.IsNullOrEmpty(panierJson))
                return new List<PanierItem>();
            
            return JsonSerializer.Deserialize<List<PanierItem>>(panierJson) ?? new List<PanierItem>();
        }
        
        public void AjouterAuPanier(PanierItem item)
        {
            var panier = GetPanier();
            panier.Add(item);
            SavePanier(panier);
        }
        
        public void UpdateQuantite(int produitId, string typeProduit, int quantite)
        {
            var panier = GetPanier();
            var item = panier.FirstOrDefault(p => p.ProduitId == produitId && p.TypeProduit == typeProduit);
            
            if (item != null)
            {
                if (quantite <= 0)
                    panier.Remove(item);
                else
                    item.Quantite = quantite;
                
                SavePanier(panier);
            }
        }
        
        public void Supprimer(int produitId, string typeProduit)
        {
            var panier = GetPanier();
            var item = panier.FirstOrDefault(p => p.ProduitId == produitId && p.TypeProduit == typeProduit);
            
            if (item != null)
            {
                panier.Remove(item);
                SavePanier(panier);
            }
        }
        
        public decimal GetTotal()
        {
            return GetPanier().Sum(item => item.Prix * item.Quantite);
        }
        
        public int GetNombreItems()
        {
            return GetPanier().Sum(item => item.Quantite);
        }
        
        public void ViderPanier()
        {
            _httpContextAccessor.HttpContext.Session.Remove(PanierKey);
        }
        
        private void SavePanier(List<PanierItem> panier)
        {
            var session = _httpContextAccessor.HttpContext.Session;
            var panierJson = JsonSerializer.Serialize(panier);
            session.SetString(PanierKey, panierJson);
        }
    }
}