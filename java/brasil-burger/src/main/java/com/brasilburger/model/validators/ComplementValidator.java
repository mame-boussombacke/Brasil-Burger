package com.brasilburger.model.validators;

import com.brasilburger.exceptions.ValidationException;
import com.brasilburger.model.entities.Complement;

public class ComplementValidator {

    public static void validate(Complement complement) throws ValidationException {

        if (complement == null)
            throw new ValidationException("Complément invalide.");

        if (complement.getNom() == null || complement.getNom().trim().isEmpty())
            throw new ValidationException("Le nom du complément est obligatoire.");

        if (complement.getPrix() <= 0)
            throw new ValidationException("Le prix du complément doit être positif.");

        if (complement.getType() == null || complement.getType().trim().isEmpty())
            throw new ValidationException("Le type du complément est obligatoire.");

        if (!isTypeValide(complement.getType()))
            throw new ValidationException("Type de complément invalide (boisson, frites, autre).");

        if (complement.getImageUrl() == null || complement.getImageUrl().trim().isEmpty())
            throw new ValidationException("L'image du complément est obligatoire.");
    }

    private static boolean isTypeValide(String type) {
        return type.equalsIgnoreCase("boisson")
            || type.equalsIgnoreCase("frites")
            || type.equalsIgnoreCase("autre");
    }
}
