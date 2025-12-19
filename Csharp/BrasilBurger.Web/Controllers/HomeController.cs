// Controllers/HomeController.cs
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using BrasilBurger.Data;
using BrasilBurger.Models;
using BrasilBurger.Services;

namespace BrasilBurger.Controllers
{
    public class HomeController : Controller
    {
        private readonly ApplicationDbContext _context;
        private readonly IPanierService _panierService;
        
        public HomeController(ApplicationDbContext context, IPanierService panierService)
        {
            _context = context;
            _panierService = panierService;
        }
        
        // GET: /Home/Index
        public IActionResult Index()
        {
            return View();
        }
        
        // GET: /Home/Catalogue
        public async Task<IActionResult> Catalogue(string type = "all")
        {
            ViewBag.TypeFilter = type;
            ViewBag.NombrePanier = _panierService.GetNombreItems();
            
            if (type == "burgers")
            {
                var burgers = await _context.Burgers
                    .Where(b => b.EstDisponible)
                    .ToListAsync();
                return View("CatalogueBurgers", burgers);
            }
            else if (type == "menus")
            {
                var menus = await _context.Menus
                    .Where(m => m.EstDisponible)
                    .Include(m => m.Burger)
                    .Include(m => m.Boisson)
                    .Include(m => m.Frites)
                    .ToListAsync();
                return View("CatalogueMenus", menus);
            }
            else
            {
                var viewModel = new CatalogueViewModel
                {
                    Burgers = await _context.Burgers
                        .Where(b => b.EstDisponible)
                        .Take(6)
                        .ToListAsync(),
                    Menus = await _context.Menus
                        .Where(m => m.EstDisponible)
                        .Include(m => m.Burger)
                        .Include(m => m.Boisson)
                        .Include(m => m.Frites)
                        .Take(4)
                        .ToListAsync()
                };
                return View(viewModel);
            }
        }
        
        // GET: /Home/Details/{id}
        public async Task<IActionResult> Details(int id, string type = "burger")
        {
            ViewBag.NombrePanier = _panierService.GetNombreItems();
            
            if (type == "burger")
            {
                var burger = await _context.Burgers
                    .FirstOrDefaultAsync(b => b.Id == id && b.EstDisponible);
                    
                if (burger == null)
                {
                    TempData["Error"] = "Burger non trouvé";
                    return RedirectToAction("Catalogue", new { type = "burgers" });
                }
                
                ViewBag.Complements = await _context.Complements
                    .Where(c => c.EstDisponible)
                    .ToListAsync();
                
                return View("DetailsBurger", burger);
            }
            else
            {
                var menu = await _context.Menus
                    .Include(m => m.Burger)
                    .Include(m => m.Boisson)
                    .Include(m => m.Frites)
                    .FirstOrDefaultAsync(m => m.Id == id && m.EstDisponible);
                    
                if (menu == null)
                {
                    TempData["Error"] = "Menu non trouvé";
                    return RedirectToAction("Catalogue", new { type = "menus" });
                }
                
                return View("DetailsMenu", menu);
            }
        }
        
        // GET: /Home/Search
        public async Task<IActionResult> Search(string query)
        {
            if (string.IsNullOrWhiteSpace(query))
                return RedirectToAction("Catalogue");
            
            var burgers = await _context.Burgers
                .Where(b => b.EstDisponible && b.Nom.Contains(query))
                .ToListAsync();
                
            var menus = await _context.Menus
                .Where(m => m.EstDisponible && m.Nom.Contains(query))
                .Include(m => m.Burger)
                .Include(m => m.Boisson)
                .Include(m => m.Frites)
                .ToListAsync();
            
            ViewBag.SearchQuery = query;
            ViewBag.NombrePanier = _panierService.GetNombreItems();
            
            var viewModel = new CatalogueViewModel
            {
                Burgers = burgers,
                Menus = menus
            };
            
            return View("Catalogue", viewModel);
        }
    }
    
    // ViewModel pour catalogue
    public class CatalogueViewModel
    {
        public List<Burger> Burgers { get; set; } = new();
        public List<Menu> Menus { get; set; } = new();
    }
}