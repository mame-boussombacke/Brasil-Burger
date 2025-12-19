// Controllers/PaiementController.cs
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using BrasilBurger.Data;
using BrasilBurger.Models;
using BrasilBurger.Services;

namespace BrasilBurger.Controllers
{
    public class PaiementController : Controller
    {
        private readonly ApplicationDbContext _context;
        private readonly ICommandeService _commandeService;
        private readonly IPaiementService _paiementService;
        
        public PaiementController(
            ApplicationDbContext context, 
            ICommandeService commandeService,
            IPaiementService paiementService)
        {
            _context = context;
            _commandeService = commandeService;
            _paiementService = paiementService;
        }
        
        // GET: /Paiement/{commandeId}
        public async Task<IActionResult> Index(int commandeId)
        {
            var clientId = HttpContext.Session.GetInt32("ClientId");
            if (clientId == null)
            {
                TempData["Error"] = "Veuillez vous connecter";
                return RedirectToAction("Login", "Client");
            }
            
            var commande = await _context.Commandes
                .Include(c => c.Client)
                .Include(c => c.Paiement)
                .Include(c => c.LigneCommandes)
                    .ThenInclude(l => l.Burger)
                .Include(c => c.LigneCommandes)
                    .ThenInclude(l => l.Menu)
                .FirstOrDefaultAsync(c => c.Id == commandeId && c.ClientId == clientId);
            
            if (commande == null)
            {
                TempData["Error"] = "Commande non trouvée";
                return RedirectToAction("Commandes", "Client");
            }
            
            // Une commande payée une seule fois
            if (commande.Paiement != null)
            {
                TempData["Error"] = "Commande déjà payée";
                return RedirectToAction("Commandes", "Client");
            }
            
            return View(commande);
        }
        
        // POST: /Paiement/Payer
        [HttpPost]
        public async Task<IActionResult> Payer(int commandeId, TypePaiement typePaiement)
        {
            var clientId = HttpContext.Session.GetInt32("ClientId");
            if (clientId == null)
            {
                TempData["Error"] = "Veuillez vous connecter";
                return RedirectToAction("Login", "Client");
            }
            
            try
            {
                // Simuler le paiement
                var commande = await _context.Commandes
                    .Include(c => c.Client)
                    .FirstOrDefaultAsync(c => c.Id == commandeId && c.ClientId == clientId);
                
                if (commande == null)
                {
                    TempData["Error"] = "Commande non trouvée";
                    return RedirectToAction("Commandes", "Client");
                }
                
                bool paiementReussi = false;
                
                if (typePaiement == TypePaiement.Wave)
                {
                    paiementReussi = await _paiementService.SimulerPaiementWaveAsync(
                        commande.Total, commande.Client.Telephone);
                }
                else if (typePaiement == TypePaiement.OM)
                {
                    paiementReussi = await _paiementService.SimulerPaiementOMAsync(
                        commande.Total, commande.Client.Telephone);
                }
                
                if (paiementReussi)
                {
                    // Créer le paiement
                    var paiement = await _commandeService.CreerPaiementAsync(commandeId, typePaiement);
                    
                    TempData["Success"] = $"Paiement réussi via {typePaiement}";
                    return RedirectToAction("Success", new { commandeId });
                }
                else
                {
                    TempData["Error"] = "Paiement échoué, veuillez réessayer";
                    return RedirectToAction("Index", new { commandeId });
                }
            }
            catch (InvalidOperationException ex)
            {
                TempData["Error"] = ex.Message;
                return RedirectToAction("Index", new { commandeId });
            }
        }
        
        // GET: /Paiement/Success/{commandeId}
        public async Task<IActionResult> Success(int commandeId)
        {
            var clientId = HttpContext.Session.GetInt32("ClientId");
            if (clientId == null)
                return RedirectToAction("Login", "Client");
            
            var commande = await _context.Commandes
                .Include(c => c.Paiement)
                .Include(c => c.Client)
                .FirstOrDefaultAsync(c => c.Id == commandeId && c.ClientId == clientId);
            
            if (commande == null || commande.Paiement == null)
            {
                TempData["Error"] = "Paiement non trouvé";
                return RedirectToAction("Commandes", "Client");
            }
            
            return View(commande);
        }
        
        // GET: /Paiement/Annuler/{commandeId}
        public async Task<IActionResult> Annuler(int commandeId)
        {
            var clientId = HttpContext.Session.GetInt32("ClientId");
            if (clientId == null)
                return RedirectToAction("Login", "Client");
            
            var commande = await _context.Commandes
                .Include(c => c.Paiement)
                .FirstOrDefaultAsync(c => c.Id == commandeId && c.ClientId == clientId);
            
            if (commande == null)
            {
                TempData["Error"] = "Commande non trouvée";
                return RedirectToAction("Commandes", "Client");
            }
            
            if (commande.Paiement != null)
            {
                TempData["Error"] = "Impossible d'annuler une commande payée";
                return RedirectToAction("Commandes", "Client");
            }
            
            commande.Etat = EtatCommande.Annulee;
            await _context.SaveChangesAsync();
            
            TempData["Success"] = "Commande annulée";
            return RedirectToAction("Commandes", "Client");
        }
    }
}