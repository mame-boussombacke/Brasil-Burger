package com.brasilburger.services;

import com.brasilburger.model.entities.Complement;
import com.brasilburger.repositories.ComplementRepository;
import com.brasilburger.model.validators.ComplementValidator;
import com.brasilburger.exceptions.BusinessException;
import com.brasilburger.exceptions.DataAccessException;
import com.brasilburger.exceptions.ValidationException;

import java.util.List;

public class ComplementService {

    private final ComplementRepository repository;

    public ComplementService() {
        this.repository = new ComplementRepository();
    }

    public void ajouterComplement(Complement c) throws BusinessException {
        try {
            ComplementValidator.validate(c);
            repository.create(c);
        } catch (ValidationException | DataAccessException e) {
            throw new BusinessException("Erreur lors de l'ajout du complément: " + e.getMessage());
        }
    }

    public void modifierComplement(Complement c) throws BusinessException {
        try {
            ComplementValidator.validate(c);
            repository.update(c);
        } catch (ValidationException | DataAccessException e) {
            throw new BusinessException("Erreur lors de la modification du complément: " + e.getMessage());
        }
    }

    public void supprimerComplement(int id) throws BusinessException {
        try {
            repository.delete(id);
        } catch (DataAccessException e) {
            throw new BusinessException("Erreur lors de la suppression du complément: " + e.getMessage());
        }
    }

    public Complement getComplementById(int id) throws BusinessException {
        try {
            return repository.findById(id);
        } catch (DataAccessException e) {
            throw new BusinessException("Erreur lors de la récupération du complément: " + e.getMessage());
        }
    }

    public List<Complement> getAllComplements() throws BusinessException {
        try {
            return repository.findAll();
        } catch (DataAccessException e) {
            throw new BusinessException("Erreur lors de la récupération des compléments: " + e.getMessage());
        }
    }
}
