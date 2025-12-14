package com.brasilburger.services;
import com.brasilburger.model.entities.Commande;
import com.brasilburger.repositories.CommandeRepository;
import com.brasilburger.exceptions.BusinessException;
import com.brasilburger.exceptions.DataAccessException;
import java.util.List;

public class CommandeService {

    private final CommandeRepository repository;

    public CommandeService() {
        this.repository = new CommandeRepository();
    }

    public void ajouterCommande(Commande c) throws BusinessException {
        try {
            repository.create(c);
        } catch (DataAccessException e) {
            throw new BusinessException("Erreur lors de la création de la commande: " + e.getMessage());
        }
    }

    public void modifierCommande(Commande c) throws BusinessException {
        try {
            repository.update(c);
        } catch (DataAccessException e) {
            throw new BusinessException("Erreur lors de la modification de la commande: " + e.getMessage());
        }
    }

    public void supprimerCommande(int id) throws BusinessException {
        try {
            repository.delete(id);
        } catch (DataAccessException e) {
            throw new BusinessException("Erreur lors de la suppression de la commande: " + e.getMessage());
        }
    }

    public Commande getCommandeById(int id) throws BusinessException {
        try {
            return repository.findById(id);
        } catch (DataAccessException e) {
            throw new BusinessException("Erreur lors de la récupération de la commande: " + e.getMessage());
        }
    }

    public List<Commande> getAllCommandes() throws BusinessException {
        try {
            return repository.findAll();
        } catch (DataAccessException e) {
            throw new BusinessException("Erreur lors de la récupération des commandes: " + e.getMessage());
        }
    }

    public List<Commande> getCommandesByClient(int clientId) throws BusinessException {
        try {
            return repository.findByClient(clientId);
        } catch (DataAccessException e) {
            throw new BusinessException("Erreur lors de la récupération des commandes du client: " + e.getMessage());
        }
    }
}
