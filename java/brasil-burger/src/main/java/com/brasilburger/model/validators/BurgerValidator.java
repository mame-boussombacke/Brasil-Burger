package com.brasilburger.model.validators;

import com.brasilburger.model.entities.Burger;
import com.brasilburger.exceptions.ValidationException;

public class BurgerValidator {
    public static void validate(Burger burger) throws ValidationException {
        if (burger.getNom() == null || burger.getNom().isEmpty()) {
            throw new ValidationException("Le nom du burger est obligatoire.");
        }
        if (burger.getPrix() <= 0) {
            throw new ValidationException("Le prix du burger doit être positif.");
        }
        if (burger.getImageUrl() == null || burger.getImageUrl().isEmpty()) {
            throw new ValidationException("L'image du burger est obligatoire.");
        }
    }
}
