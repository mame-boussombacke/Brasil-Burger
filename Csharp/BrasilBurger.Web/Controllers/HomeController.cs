using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Configuration;
using Npgsql;

public class HomeController : Controller
{
    private readonly IConfiguration _configuration;

    // Injection de IConfiguration via le constructeur
    public HomeController(IConfiguration configuration)
    {
        _configuration = configuration;
    }

    public IActionResult TestDb()
    {
        string connString = _configuration.GetConnectionString("DefaultConnection");

        try
        {
            using var conn = new NpgsqlConnection(connString);
            conn.Open();
            using var cmd = new NpgsqlCommand("SELECT NOW()", conn);
            var result = cmd.ExecuteScalar();
            return Content($"Connexion BD réussie ! Date du serveur : {result}");
        }
        catch (Exception ex)
        {
            return Content($"Erreur connexion BD : {ex.Message}");
        }
    }
}
