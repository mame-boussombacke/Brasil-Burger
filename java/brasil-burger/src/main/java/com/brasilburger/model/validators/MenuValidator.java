package com.brasilburger.model.validators;

import com.brasilburger.model.entities.Menu;
import com.brasilburger.exceptions.ValidationException;

public class MenuValidator {
    public static void validate(Menu menu) throws ValidationException {
        if (menu.getNom() == null || menu.getNom().isEmpty())
            throw new ValidationException("Le nom du menu est obligatoire.");
        if (menu.getBurgers() == null || menu.getBurgers().isEmpty())
            throw new ValidationException("Un menu doit contenir au moins un burger.");
    }
}
