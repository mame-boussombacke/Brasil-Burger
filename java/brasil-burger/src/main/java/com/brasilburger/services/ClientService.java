package com.brasilburger.services;

import com.brasilburger.model.entities.Client;
import com.brasilburger.repositories.ClientRepository;
import com.brasilburger.exceptions.BusinessException;
import com.brasilburger.exceptions.DataAccessException;

import java.util.List;

public class ClientService {

    private final ClientRepository repository;

    public ClientService() {
        this.repository = new ClientRepository();
    }

    public void ajouterClient(Client c) throws BusinessException {
        try {
            repository.create(c);
        } catch (DataAccessException e) {
            throw new BusinessException("Erreur lors de la création du client: " + e.getMessage());
        }
    }

    public void modifierClient(Client c) throws BusinessException {
        try {
            repository.update(c);
        } catch (DataAccessException e) {
            throw new BusinessException("Erreur lors de la modification du client: " + e.getMessage());
        }
    }

    public void supprimerClient(int id) throws BusinessException {
        try {
            repository.delete(id);
        } catch (DataAccessException e) {
            throw new BusinessException("Erreur lors de la suppression du client: " + e.getMessage());
        }
    }

    public Client getClientById(int id) throws BusinessException {
        try {
            return repository.findById(id);
        } catch (DataAccessException e) {
            throw new BusinessException("Erreur lors de la récupération du client: " + e.getMessage());
        }
    }

    public Client getClientByTelephone(String tel) throws BusinessException {
        try {
            return repository.findByTelephone(tel);
        } catch (DataAccessException e) {
            throw new BusinessException("Erreur lors de la recherche du client par téléphone: " + e.getMessage());
        }
    }

    public List<Client> getAllClients() throws BusinessException {
        try {
            return repository.findAll();
        } catch (DataAccessException e) {
            throw new BusinessException("Erreur lors de la récupération des clients: " + e.getMessage());
        }
    }
}
