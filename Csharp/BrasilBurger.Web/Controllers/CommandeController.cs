// Controllers/CommandeController.cs
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using BrasilBurger.Data;
using BrasilBurger.Models;
using BrasilBurger.Services;

namespace BrasilBurger.Controllers
{
    public class CommandeController : Controller
    {
        private readonly ApplicationDbContext _context;
        private readonly IPanierService _panierService;
        private readonly ICommandeService _commandeService;
        
        public CommandeController(
            ApplicationDbContext context, 
            IPanierService panierService,
            ICommandeService commandeService)
        {
            _context = context;
            _panierService = panierService;
            _commandeService = commandeService;
        }
        
        // GET: /Commande/Panier
        public IActionResult Panier()
        {
            ViewBag.NombrePanier = _panierService.GetNombreItems();
            ViewBag.TotalPanier = _panierService.GetTotal();
            return View(_panierService.GetPanier());
        }
        
        // POST: /Commande/AjouterPanier
        [HttpPost]
        public async Task<IActionResult> AjouterPanier(
            int produitId, 
            string typeProduit, 
            List<int>? complementsIds, 
            int quantite = 1)
        {
            if (quantite < 1) quantite = 1;
            
            if (typeProduit == "burger")
            {
                var burger = await _context.Burgers
                    .FirstOrDefaultAsync(b => b.Id == produitId && b.EstDisponible);
                    
                if (burger == null)
                {
                    TempData["Error"] = "Burger non disponible";
                    return RedirectToAction("Catalogue", "Home", new { type = "burgers" });
                }
                
                var item = new PanierItem
                {
                    ProduitId = produitId,
                    TypeProduit = "burger",
                    Nom = burger.Nom,
                    Prix = burger.Prix,
                    ImageUrl = burger.ImageUrl,
                    Quantite = quantite,
                    ComplementsIds = complementsIds ?? new List<int>()
                };
                
                _panierService.AjouterAuPanier(item);
                TempData["Success"] = $"{burger.Nom} ajouté au panier";
            }
            else if (typeProduit == "menu")
            {
                var menu = await _context.Menus
                    .Include(m => m.Burger)
                    .Include(m => m.Boisson)
                    .Include(m => m.Frites)
                    .FirstOrDefaultAsync(m => m.Id == produitId && m.EstDisponible);
                    
                if (menu == null)
                {
                    TempData["Error"] = "Menu non disponible";
                    return RedirectToAction("Catalogue", "Home", new { type = "menus" });
                }
                
                var item = new PanierItem
                {
                    ProduitId = produitId,
                    TypeProduit = "menu",
                    Nom = menu.Nom,
                    Prix = menu.PrixTotal,
                    ImageUrl = menu.ImageUrl,
                    Quantite = quantite,
                    ComplementsIds = new List<int>()
                };
                
                _panierService.AjouterAuPanier(item);
                TempData["Success"] = $"{menu.Nom} ajouté au panier";
            }
            
            return RedirectToAction("Panier");
        }
        
        // POST: /Commande/UpdateQuantite
        [HttpPost]
        public IActionResult UpdateQuantite(int produitId, string typeProduit, int quantite)
        {
            if (quantite < 1)
            {
                _panierService.Supprimer(produitId, typeProduit);
                TempData["Success"] = "Produit retiré du panier";
            }
            else
            {
                _panierService.UpdateQuantite(produitId, typeProduit, quantite);
                TempData["Success"] = "Quantité mise à jour";
            }
            
            return RedirectToAction("Panier");
        }
        
        // POST: /Commande/Supprimer
        [HttpPost]
        public IActionResult Supprimer(int produitId, string typeProduit)
        {
            _panierService.Supprimer(produitId, typeProduit);
            TempData["Success"] = "Produit retiré du panier";
            return RedirectToAction("Panier");
        }
        
        // GET: /Commande/Checkout
        public IActionResult Checkout()
        {
            var panier = _panierService.GetPanier();
            if (!panier.Any())
            {
                TempData["Error"] = "Votre panier est vide";
                return RedirectToAction("Catalogue", "Home");
            }
            
            ViewBag.NombrePanier = _panierService.GetNombreItems();
            ViewBag.TotalPanier = _panierService.GetTotal();
            return View();
        }
        
        // POST: /Commande/Checkout
        [HttpPost]
        public async Task<IActionResult> Checkout(
            TypeLivraison typeLivraison, 
            string? adresse = null,
            string? zone = null)
        {
            var clientId = HttpContext.Session.GetInt32("ClientId");
            if (clientId == null)
            {
                TempData["Error"] = "Veuillez vous connecter";
                return RedirectToAction("Login", "Client");
            }
            
            var panier = _panierService.GetPanier();
            if (!panier.Any())
            {
                TempData["Error"] = "Votre panier est vide";
                return RedirectToAction("Catalogue", "Home");
            }
            
            if (typeLivraison == TypeLivraison.Livraison)
            {
                if (string.IsNullOrWhiteSpace(adresse))
                {
                    ModelState.AddModelError("adresse", "Adresse requise");
                    return View();
                }
            }
            
            try
            {
                var commande = await _commandeService.CreerCommandeAsync(
                    clientId.Value, panier, typeLivraison, adresse, zone);
                
                _panierService.ViderPanier();
                TempData["Success"] = "Commande créée !";
                return RedirectToAction("Index", "Paiement", new { commandeId = commande.Id });
            }
            catch (Exception ex)
            {
                TempData["Error"] = $"Erreur: {ex.Message}";
                return View();
            }
        }
        
        // POST: /Commande/ViderPanier
        [HttpPost]
        public IActionResult ViderPanier()
        {
            _panierService.ViderPanier();
            TempData["Success"] = "Panier vidé";
            return RedirectToAction("Panier");
        }
    }
}