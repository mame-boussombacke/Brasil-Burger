// Models/PanierItem.cs
namespace BrasilBurger.Models
{
    public class PanierItem
    {
        public int ProduitId { get; set; }
        public string TypeProduit { get; set; } // "burger" ou "menu"
        public string Nom { get; set; }
        public decimal Prix { get; set; }
        public string ImageUrl { get; set; }
        public int Quantite { get; set; } = 1;
        public List<int> ComplementsIds { get; set; } = new List<int>();
        
        public decimal SousTotal => Prix * Quantite;
    }
}