package com.brasilburger.services;

import java.util.List;

import com.brasilburger.exceptions.BusinessException;
import com.brasilburger.exceptions.DataAccessException;
import com.brasilburger.exceptions.ValidationException;
import com.brasilburger.model.entities.Utilisateur;
import com.brasilburger.model.validators.UtilisateurValidator;
import com.brasilburger.repositories.UtilisateurRepository;

public class UtilisateurService {

    private final UtilisateurRepository repository;

    public UtilisateurService() {
        this.repository = new UtilisateurRepository();
    }

    public void ajouterUtilisateur(Utilisateur u) throws BusinessException {
        try {
            UtilisateurValidator.validate(u);
            repository.create(u);
        } catch (ValidationException | DataAccessException e) {
            throw new BusinessException("Erreur lors de la création de l'utilisateur: " + e.getMessage());
        }
    }

    public void modifierUtilisateur(Utilisateur u) throws BusinessException {
        try {
            UtilisateurValidator.validate(u);
            repository.update(u);
        } catch (ValidationException | DataAccessException e) {
            throw new BusinessException("Erreur lors de la modification de l'utilisateur: " + e.getMessage());
        }
    }

    public void supprimerUtilisateur(int id) throws BusinessException {
        try {
            repository.delete(id);
        } catch (DataAccessException e) {
            throw new BusinessException("Erreur lors de la suppression de l'utilisateur: " + e.getMessage());
        }
    }

    public Utilisateur getUtilisateurById(int id) throws BusinessException {
        try {
            return repository.findById(id);
        } catch (DataAccessException e) {
            throw new BusinessException("Erreur lors de la récupération de l'utilisateur: " + e.getMessage());
        }
    }

    public List<Utilisateur> getAllUtilisateurs() throws BusinessException {
        try {
            return repository.findAll();
        } catch (DataAccessException e) {
            throw new BusinessException("Erreur lors de la récupération des utilisateurs: " + e.getMessage());
        }
    }

    // Méthode corrigée pour login avec email + mot de passe
    public Utilisateur login(String email, String motDePasse) throws BusinessException {
        try {
            Utilisateur u = repository.findByEmail(email);
            if (u == null || !u.getMotDePasse().equals(motDePasse)) {
                return null; // Email ou mot de passe incorrect
            }
            return u;
        } catch (DataAccessException e) {
            throw new BusinessException("Erreur lors de la connexion: " + e.getMessage());
        }
    }
}
