using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Configuration;

namespace BrasilBurger.Web.Controllers
{
    public class HomeController : Controller
    {
        private readonly IConfiguration _configuration;

        public HomeController(IConfiguration configuration)
        {
            _configuration = configuration;
        }

        // Page d'accueil (facultative)
                [HttpGet("/")]

        public IActionResult Index()
        {
            return Content("Bienvenue sur Brasil Burger !");
        }

        
    }
}
