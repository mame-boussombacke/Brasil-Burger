package com.brasilburger.services;
import com.brasilburger.model.entities.CompositionMenu;
import com.brasilburger.repositories.CompositionMenuRepository;
import com.brasilburger.exceptions.BusinessException;
import com.brasilburger.exceptions.DataAccessException;
import java.util.List;

public class CompositionMenuService {

    private final CompositionMenuRepository repository;

    public CompositionMenuService() {
        this.repository = new CompositionMenuRepository();
    }

    public void ajouterComposition(CompositionMenu cm) throws BusinessException {
        try {
            repository.create(cm);
        } catch (DataAccessException e) {
            throw new BusinessException("Erreur lors de la création de la composition: " + e.getMessage());
        }
    }

    public void modifierComposition(CompositionMenu cm) throws BusinessException {
        try {
            repository.update(cm);
        } catch (DataAccessException e) {
            throw new BusinessException("Erreur lors de la modification de la composition: " + e.getMessage());
        }
    }

    public void supprimerComposition(int id) throws BusinessException {
        try {
            repository.delete(id);
        } catch (DataAccessException e) {
            throw new BusinessException("Erreur lors de la suppression de la composition: " + e.getMessage());
        }
    }

    public List<CompositionMenu> getByMenuId(int menuId) throws BusinessException {
        try {
            return repository.findByMenuId(menuId);
        } catch (DataAccessException e) {
            throw new BusinessException("Erreur lors de la récupération des compositions pour le menu: " + e.getMessage());
        }
    }

    public List<CompositionMenu> getAll() throws BusinessException {
        try {
            return repository.findAll();
        } catch (DataAccessException e) {
            throw new BusinessException("Erreur lors de la récupération des compositions: " + e.getMessage());
        }
    }
}
