// Services/CommandeService.cs
using Microsoft.EntityFrameworkCore;
using BrasilBurger.Data;
using BrasilBurger.Models;

namespace BrasilBurger.Services
{
    public class CommandeService : ICommandeService
    {
        private readonly ApplicationDbContext _context;
        
        public CommandeService(ApplicationDbContext context)
        {
            _context = context;
        }
        
        public async Task<Commande> CreerCommandeAsync(int clientId, List<PanierItem> panier, 
                                                       TypeLivraison typeLivraison, 
                                                       string adresse = null, string zone = null)
        {
            if (!panier.Any())
                throw new ArgumentException("Le panier est vide");
            
            // Vérifier client
            var client = await _context.Clients.FindAsync(clientId);
            if (client == null)
                throw new ArgumentException("Client non trouvé");
            
            // Créer commande
            var commande = new Commande
            {
                ClientId = clientId,
                NumeroCommande = GenerateNumeroCommande(),
                TypeLivraison = typeLivraison,
                AdresseLivraison = typeLivraison == TypeLivraison.Livraison ? adresse : null,
                ZoneLivraison = typeLivraison == TypeLivraison.Livraison ? zone : null,
                Total = 0 // Calculé après
            };
            
            _context.Commandes.Add(commande);
            await _context.SaveChangesAsync();
            
            decimal total = 0;
            
            // Ajouter lignes commande
            foreach (var item in panier)
            {
                if (item.TypeProduit == "burger")
                {
                    var burger = await _context.Burgers.FindAsync(item.ProduitId);
                    if (burger == null || !burger.EstDisponible)
                        continue;
                    
                    // Ligne burger
                    var ligneBurger = new LigneCommande
                    {
                        CommandeId = commande.Id,
                        BurgerId = item.ProduitId,
                        Quantite = item.Quantite,
                        PrixUnitaire = burger.Prix
                    };
                    _context.LigneCommandes.Add(ligneBurger);
                    total += burger.Prix * item.Quantite;
                    
                    // Lignes pour compléments
                    foreach (var complementId in item.ComplementsIds)
                    {
                        var complement = await _context.Complements.FindAsync(complementId);
                        if (complement != null && complement.EstDisponible)
                        {
                            var ligneComplement = new LigneCommande
                            {
                                CommandeId = commande.Id,
                                ComplementId = complementId,
                                Quantite = 1,
                                PrixUnitaire = complement.Prix
                            };
                            _context.LigneCommandes.Add(ligneComplement);
                            total += complement.Prix;
                        }
                    }
                }
                else if (item.TypeProduit == "menu")
                {
                    var menu = await _context.Menus
                        .Include(m => m.Burger)
                        .Include(m => m.Boisson)
                        .Include(m => m.Frites)
                        .FirstOrDefaultAsync(m => m.Id == item.ProduitId);
                    
                    if (menu == null || !menu.EstDisponible)
                        continue;
                    
                    var ligneMenu = new LigneCommande
                    {
                        CommandeId = commande.Id,
                        MenuId = item.ProduitId,
                        Quantite = item.Quantite,
                        PrixUnitaire = menu.PrixTotal
                    };
                    _context.LigneCommandes.Add(ligneMenu);
                    total += menu.PrixTotal * item.Quantite;
                }
            }
            
            // Mettre à jour total
            commande.Total = total;
            await _context.SaveChangesAsync();
            
            return commande;
        }
        
        public async Task<Paiement> CreerPaiementAsync(int commandeId, TypePaiement typePaiement)
        {
            var commande = await _context.Commandes
                .Include(c => c.Paiement)
                .FirstOrDefaultAsync(c => c.Id == commandeId);
            
            if (commande == null)
                throw new ArgumentException("Commande non trouvée");
            
            // UNE COMMANDE PAYÉE UNE SEULE FOIS
            if (commande.Paiement != null)
                throw new InvalidOperationException("Cette commande est déjà payée");
            
            var paiement = new Paiement
            {
                CommandeId = commandeId,
                Montant = commande.Total,
                Type = typePaiement
            };
            
            // Mettre à jour état commande
            commande.Etat = EtatCommande.Validee;
            
            _context.Paiements.Add(paiement);
            await _context.SaveChangesAsync();
            
            return paiement;
        }
        
        public async Task<List<Commande>> GetCommandesClientAsync(int clientId)
        {
            return await _context.Commandes
                .Where(c => c.ClientId == clientId)
                .Include(c => c.Paiement)
                .OrderByDescending(c => c.DateCommande)
                .ToListAsync();
        }
        
        public async Task<Commande> GetCommandeDetailsAsync(int commandeId, int clientId)
        {
            return await _context.Commandes
                .Include(c => c.Paiement)
                .Include(c => c.LigneCommandes)
                    .ThenInclude(l => l.Burger)
                .Include(c => c.LigneCommandes)
                    .ThenInclude(l => l.Menu)
                .Include(c => c.LigneCommandes)
                    .ThenInclude(l => l.Complement)
                .FirstOrDefaultAsync(c => c.Id == commandeId && c.ClientId == clientId);
        }
        
        private string GenerateNumeroCommande()
        {
            return "CMD-" + DateTime.Now.ToString("yyyyMMddHHmmssfff");
        }
    }
}