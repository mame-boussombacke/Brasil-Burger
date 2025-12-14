package com.brasilburger.services;
import com.brasilburger.model.entities.Burger;
import com.brasilburger.repositories.BurgerRepository;
import com.brasilburger.exceptions.BusinessException;
import com.brasilburger.exceptions.DataAccessException;
import com.brasilburger.exceptions.ValidationException;
import com.brasilburger.model.validators.BurgerValidator;

import java.util.List;

public class BurgerService {

    private final BurgerRepository repository;

    public BurgerService() {
        this.repository = new BurgerRepository();
    }

    public void ajouterBurger(Burger burger) throws BusinessException {
        try {
            BurgerValidator.validate(burger);
            repository.create(burger);
        } catch (ValidationException | DataAccessException e) {
            throw new BusinessException("Erreur lors de l'ajout du burger: " + e.getMessage());
        }
    }

    public void modifierBurger(Burger burger) throws BusinessException {
        try {
            BurgerValidator.validate(burger);
            repository.update(burger);
        } catch (ValidationException | DataAccessException e) {
            throw new BusinessException("Erreur lors de la modification du burger: " + e.getMessage());
        }
    }

    public void supprimerBurger(int id) throws BusinessException {
        try {
            repository.delete(id);
        } catch (DataAccessException e) {
            throw new BusinessException("Erreur lors de la suppression du burger: " + e.getMessage());
        }
    }

    public Burger getBurgerById(int id) throws BusinessException {
        try {
            return repository.findById(id);
        } catch (DataAccessException e) {
            throw new BusinessException("Erreur lors de la récupération du burger: " + e.getMessage());
        }
    }

    public List<Burger> getAllBurgers() throws BusinessException {
        try {
            return repository.findAll();
        } catch (DataAccessException e) {
            throw new BusinessException("Erreur lors de la récupération des burgers: " + e.getMessage());
        }
    }
}
