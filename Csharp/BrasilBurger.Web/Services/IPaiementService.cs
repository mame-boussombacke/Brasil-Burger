// Services/IPaiementService.cs
using BrasilBurger.Models;

namespace BrasilBurger.Services
{
    public interface IPaiementService
    {
        Task<bool> SimulerPaiementWaveAsync(decimal montant, string telephone);
        Task<bool> SimulerPaiementOMAsync(decimal montant, string telephone);
        Task<bool> VerifierPaiementAsync(string reference);
        string GenererReferencePaiement();
    }
}