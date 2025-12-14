package com.brasilburger.model.validators;

import com.brasilburger.model.entities.Zone;
import com.brasilburger.exceptions.ValidationException;

public class ZoneValidator {
    public static void validate(Zone zone) throws ValidationException {
        if (zone.getNom() == null || zone.getNom().isEmpty())
            throw new ValidationException("Le nom de la zone est obligatoire.");
        if (zone.getPrixLivraison() < 0)
            throw new ValidationException("Le prix de livraison doit être positif.");
    }
}
