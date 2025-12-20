// Services/ImageService.cs
using System.IO;

namespace BrasilBurger.Services
{
    public class ImageService : IImageService
    {
        private readonly IWebHostEnvironment _environment;
        private readonly ILogger<ImageService> _logger;
        private const string ImagesFolder = "images";

        public ImageService(IWebHostEnvironment environment, ILogger<ImageService> logger)
        {
            _environment = environment;
            _logger = logger;
        }

        public async Task<string> UploadImageAsync(IFormFile file, string folder = "products")
        {
            if (file == null || file.Length == 0)
            {
                throw new ArgumentException("Le fichier est vide");
            }

            // Vérifier le type de fichier
            var allowedExtensions = new[] { ".jpg", ".jpeg", ".png", ".gif", ".webp" };
            var extension = Path.GetExtension(file.FileName).ToLowerInvariant();
            
            if (!allowedExtensions.Contains(extension))
            {
                throw new ArgumentException($"Extension non autorisée. Extensions autorisées: {string.Join(", ", allowedExtensions)}");
            }

            // Vérifier la taille (max 5MB)
            if (file.Length > 5 * 1024 * 1024)
            {
                throw new ArgumentException("Le fichier est trop volumineux. Taille maximale: 5MB");
            }

            // Créer le dossier s'il n'existe pas
            var uploadFolder = Path.Combine(_environment.WebRootPath, ImagesFolder, folder);
            if (!Directory.Exists(uploadFolder))
            {
                Directory.CreateDirectory(uploadFolder);
            }

            // Générer un nom de fichier unique
            var fileName = $"{Guid.NewGuid()}{extension}";
            var filePath = Path.Combine(uploadFolder, fileName);

            // Sauvegarder le fichier
            using (var stream = new FileStream(filePath, FileMode.Create))
            {
                await file.CopyToAsync(stream);
            }

            // Retourner l'URL relative
            var imageUrl = $"/{ImagesFolder}/{folder}/{fileName}";
            _logger.LogInformation($"Image uploadée: {imageUrl}");
            
            return imageUrl;
        }

        public bool DeleteImage(string imageUrl)
        {
            if (string.IsNullOrEmpty(imageUrl))
                return false;

            try
            {
                // Convertir l'URL en chemin physique
                var relativePath = imageUrl.TrimStart('/');
                var filePath = Path.Combine(_environment.WebRootPath, relativePath);

                if (File.Exists(filePath))
                {
                    File.Delete(filePath);
                    _logger.LogInformation($"Image supprimée: {imageUrl}");
                    return true;
                }
            }
            catch (Exception ex)
            {
                _logger.LogError(ex, $"Erreur lors de la suppression de l'image: {imageUrl}");
            }

            return false;
        }

        public string GetImageUrl(string fileName, string folder = "products")
        {
            return $"/{ImagesFolder}/{folder}/{fileName}";
        }
    }
}

