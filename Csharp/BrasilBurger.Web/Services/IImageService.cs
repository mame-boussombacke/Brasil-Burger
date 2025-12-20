// Services/IImageService.cs
namespace BrasilBurger.Services
{
    public interface IImageService
    {
        Task<string> UploadImageAsync(IFormFile file, string folder = "products");
        bool DeleteImage(string imageUrl);
        string GetImageUrl(string fileName, string folder = "products");
    }
}

