// Controllers/ClientController.cs
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using BrasilBurger.Data;
using BrasilBurger.Models;
using BrasilBurger.Services;

namespace BrasilBurger.Controllers
{
    public class ClientController : Controller
    {
        private readonly ApplicationDbContext _context;
        private readonly ICommandeService _commandeService;
        
        public ClientController(ApplicationDbContext context, ICommandeService commandeService)
        {
            _context = context;
            _commandeService = commandeService;
        }
        
        // GET: /Client/Login
        public IActionResult Login()
        {
            if (HttpContext.Session.GetInt32("ClientId") != null)
                return RedirectToAction("Catalogue", "Home");
            return View();
        }
        
        // POST: /Client/Login
        [HttpPost]
        public async Task<IActionResult> Login(string email, string password)
        {
            if (string.IsNullOrEmpty(email) || string.IsNullOrEmpty(password))
            {
                ModelState.AddModelError("", "Email et mot de passe requis");
                return View();
            }
            
            var client = await _context.Clients
                .FirstOrDefaultAsync(c => c.Email == email && c.Password == password);
                
            if (client == null)
            {
                ModelState.AddModelError("", "Email ou mot de passe incorrect");
                return View();
            }
            
            HttpContext.Session.SetInt32("ClientId", client.Id);
            HttpContext.Session.SetString("ClientNom", $"{client.Nom} {client.Prenom}");
            
            TempData["Success"] = $"Bienvenue {client.Prenom} !";
            return RedirectToAction("Catalogue", "Home");
        }
        
        // GET: /Client/Register
        public IActionResult Register()
        {
            return View();
        }
        
        // POST: /Client/Register
        [HttpPost]
        public async Task<IActionResult> Register(Client client)
        {
            if (ModelState.IsValid)
            {
                var emailExiste = await _context.Clients
                    .AnyAsync(c => c.Email == client.Email);
                    
                if (emailExiste)
                {
                    ModelState.AddModelError("Email", "Cet email est déjà utilisé");
                    return View(client);
                }
                
                _context.Clients.Add(client);
                await _context.SaveChangesAsync();
                
                HttpContext.Session.SetInt32("ClientId", client.Id);
                HttpContext.Session.SetString("ClientNom", $"{client.Nom} {client.Prenom}");
                
                TempData["Success"] = "Compte créé avec succès !";
                return RedirectToAction("Catalogue", "Home");
            }
            
            return View(client);
        }
        
        // GET: /Client/Commandes
        public async Task<IActionResult> Commandes()
        {
            var clientId = HttpContext.Session.GetInt32("ClientId");
            if (clientId == null)
            {
                TempData["Error"] = "Veuillez vous connecter";
                return RedirectToAction("Login");
            }
            
            var commandes = await _commandeService.GetCommandesClientAsync(clientId.Value);
            return View(commandes);
        }
        
        // GET: /Client/CommandeDetails/{id}
        public async Task<IActionResult> CommandeDetails(int id)
        {
            var clientId = HttpContext.Session.GetInt32("ClientId");
            if (clientId == null)
                return RedirectToAction("Login");
            
            var commande = await _commandeService.GetCommandeDetailsAsync(id, clientId.Value);
            
            if (commande == null)
            {
                TempData["Error"] = "Commande non trouvée";
                return RedirectToAction("Commandes");
            }
            
            return View(commande);
        }
        
        // POST: /Client/Logout
        [HttpPost]
        public IActionResult Logout()
        {
            HttpContext.Session.Clear();
            TempData["Success"] = "Vous êtes déconnecté";
            return RedirectToAction("Index", "Home");
        }
    }
}