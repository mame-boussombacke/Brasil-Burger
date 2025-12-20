using BrasilBurger.Models;

namespace BrasilBurger.Data
{
    public static class SeedData
    {
        public static void Initialize(ApplicationDbContext context)
        {
            Console.WriteLine("🌱 Vérification des données Brasil Burger...");

            if (context.Burgers.Any() || context.Menus.Any())
            {
                Console.WriteLine("ℹ️ Données déjà présentes, SeedData ignoré.");
                return;
            }

            Console.WriteLine("🧹 Aucune donnée trouvée, exécution de SeedData...");

            // ===============================
            // Testez d'abord avec Unsplash (garanti)
            // ===============================
            var imageUrls = new
            {
                // Burgers - 6 différents
                ClassicBurger = "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=600&h=400&fit=crop",
                CheeseBurger = "https://images.unsplash.com/photo-1603064752734-4c48eff53d05?w=600&h=400&fit=crop",
                ChickenBurger = "https://images.unsplash.com/photo-1626082895612-9e6d8e8c2b1d?w=600&h=400&fit=crop",
                VeggieBurger = "https://images.unsplash.com/photo-1594212699903-ec8a3eca50f5?w=600&h=400&fit=crop",
                FromageBurger = "https://images.unsplash.com/photo-1586190848861-99aa4a171e90?w=600&h=400&fit=crop",
                ClassicDouble = "https://images.unsplash.com/photo-1553979459-d2229ba7433c?w=600&h=400&fit=crop",
                
                // Complements
                Frites = "https://images.unsplash.com/photo-1573080496219-bb080dd4f877?w=600&h=400&fit=crop",
                Coca = "https://images.unsplash.com/photo-1622483767028-3f66f32aef97?w=600&h=400&fit=crop",
                Jus = "https://images.unsplash.com/photo-1621506289937-a8e4df240d0b?w=600&h=400&fit=crop",
                
                // Menus - 3 différents
                MenuClassic = "https://images.unsplash.com/photo-1550547660-d9450f859349?w=600&h=400&fit=crop",
                MenuCheese = "https://images.unsplash.com/photo-1467003909585-2f8a72700288?w=600&h=400&fit=crop",
                MenuChic = "https://images.unsplash.com/photo-1553979459-d2229ba7433c?w=600&h=400&fit=crop"
            };

            // ===============================
            // 1. Compléments
            // ===============================
            var frites = new Complement
            {
                Nom = "Frites Maison",
                Type = "frites",
                Prix = 1500,
                EstDisponible = true,
                ImageUrl = imageUrls.Frites
            };

            var coca = new Complement
            {
                Nom = "Coca-Cola 33cl",
                Type = "boisson",
                Prix = 1000,
                EstDisponible = true,
                ImageUrl = imageUrls.Coca
            };

            var jus = new Complement
            {
                Nom = "Jus d'Orange",
                Type = "boisson",
                Prix = 1200,
                EstDisponible = true,
                ImageUrl = imageUrls.Jus
            };

            context.Complements.AddRange(frites, coca, jus);
            context.SaveChanges();

            // ===============================
            // 2. Burgers - 6 BURGERS DIFFÉRENTS
            // ===============================
            var burgers = new List<Burger>
            {
                new Burger
                {
                    Nom = "Classic Brasil",
                    Description = "Notre burger signature: steak haché 150g, salade, tomate, oignons, sauce maison",
                    Prix = 4500,
                    EstDisponible = true,
                    ImageUrl = imageUrls.ClassicBurger
                },
                new Burger
                {
                    Nom = "Cheese Brasil",
                    Description = "Double fromage cheddar fondu, bacon croustillant, oignons caramélisés",
                    Prix = 5500,
                    EstDisponible = true,
                    ImageUrl = imageUrls.CheeseBurger
                },
                new Burger
                {
                    Nom = "Chicken Brasil",
                    Description = "Filet de poulet pané croustillant, salade, tomate, sauce mayonnaise",
                    Prix = 4800,
                    EstDisponible = true,
                    ImageUrl = imageUrls.ChickenBurger
                },
                new Burger
                {
                    Nom = "Veggie Brasil",
                    Description = "Steak de légumes grillés, avocat, roquette, sauce au yaourt et fines herbes",
                    Prix = 4200,
                    EstDisponible = true,
                    ImageUrl = imageUrls.VeggieBurger
                },
                new Burger
                {
                    Nom = "Fromage Supreme",
                    Description = "Triple fromage: cheddar, gouda, emmental avec sauce spéciale",
                    Prix = 5800,
                    EstDisponible = true,
                    ImageUrl = imageUrls.FromageBurger
                },
                new Burger
                {
                    Nom = "Classic Double",
                    Description = "Double steak haché, double fromage, double bacon pour les gourmands",
                    Prix = 6200,
                    EstDisponible = true,
                    ImageUrl = imageUrls.ClassicDouble
                }
            };

            context.Burgers.AddRange(burgers);
            context.SaveChanges();

            // ===============================
            // 3. Menus - 3 MENUS DIFFÉRENTS
            // ===============================
            var menus = new List<Menu>
            {
                new Menu
                {
                    Nom = "Menu Classic",
                    Description = "Classic Brasil + Frites + Coca-Cola",
                    EstDisponible = true,
                    BurgerId = burgers[0].Id, // Classic Brasil
                    FritesId = frites.Id,
                    BoissonId = coca.Id,
                    ImageUrl = imageUrls.MenuClassic,
                    PrixTotal = burgers[0].Prix + frites.Prix + coca.Prix
                },
                new Menu
                {
                    Nom = "Menu Cheese",
                    Description = "Cheese Brasil + Frites + Jus d'Orange",
                    EstDisponible = true,
                    BurgerId = burgers[1].Id, // Cheese Brasil
                    FritesId = frites.Id,
                    BoissonId = jus.Id,
                    ImageUrl = imageUrls.MenuCheese,
                    PrixTotal = burgers[1].Prix + frites.Prix + jus.Prix
                },
                new Menu
                {
                    Nom = "Menu Chic",
                    Description = "Classic Double + Frites + Coca-Cola",
                    EstDisponible = true,
                    BurgerId = burgers[2].Id, // Classic Double
                    FritesId = frites.Id,
                    BoissonId = coca.Id,
                    ImageUrl = imageUrls.MenuChic,
                    PrixTotal = burgers[2].Prix + frites.Prix + coca.Prix
                }
            };

            context.Menus.AddRange(menus);
            context.SaveChanges();

            Console.WriteLine("✅ SeedData terminé avec succès");
            Console.WriteLine($"   🍔 {burgers.Count} burgers créés");
            Console.WriteLine($"   🍟 3 compléments créés");
            Console.WriteLine($"   📦 {menus.Count} menus créés");
            
            // Vérification
            Console.WriteLine("\n📋 Vérification:");
            Console.WriteLine($"   Burgers: {string.Join(", ", burgers.Select(b => b.Nom))}");
            Console.WriteLine($"   Menus: {string.Join(", ", menus.Select(m => m.Nom))}");
        }
    }
}