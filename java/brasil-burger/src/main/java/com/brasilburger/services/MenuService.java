package com.brasilburger.services;
import com.brasilburger.model.entities.Menu;
import com.brasilburger.repositories.MenuRepository;
import com.brasilburger.model.validators.MenuValidator;
import com.brasilburger.exceptions.BusinessException;
import com.brasilburger.exceptions.DataAccessException;
import com.brasilburger.exceptions.ValidationException;

import java.util.List;

public class MenuService {

    private final MenuRepository repository;

    public MenuService() {
        this.repository = new MenuRepository();
    }

    public void ajouterMenu(Menu menu) throws BusinessException {
        try {
            MenuValidator.validate(menu);
            repository.create(menu);
        } catch (ValidationException | DataAccessException e) {
            throw new BusinessException("Erreur lors de l'ajout du menu: " + e.getMessage());
        }
    }

    public void modifierMenu(Menu menu) throws BusinessException {
        try {
            MenuValidator.validate(menu);
            repository.update(menu);
        } catch (ValidationException | DataAccessException e) {
            throw new BusinessException("Erreur lors de la modification du menu: " + e.getMessage());
        }
    }

    public void supprimerMenu(int id) throws BusinessException {
        try {
            repository.delete(id);
        } catch (DataAccessException e) {
            throw new BusinessException("Erreur lors de la suppression du menu: " + e.getMessage());
        }
    }

    public Menu getMenuById(int id) throws BusinessException {
        try {
            return repository.findById(id);
        } catch (DataAccessException e) {
            throw new BusinessException("Erreur lors de la récupération du menu: " + e.getMessage());
        }
    }

    public List<Menu> getAllMenus() throws BusinessException {
        try {
            return repository.findAll();
        } catch (DataAccessException e) {
            throw new BusinessException("Erreur lors de la récupération des menus: " + e.getMessage());
        }
    }
}
