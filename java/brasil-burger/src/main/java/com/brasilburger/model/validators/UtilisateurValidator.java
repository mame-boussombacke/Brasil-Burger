package com.brasilburger.model.validators;
import com.brasilburger.model.entities.Utilisateur;
import com.brasilburger.exceptions.ValidationException;

public class UtilisateurValidator {

    public static void validate(Utilisateur user) throws ValidationException {
        if (user.getEmail() == null || user.getEmail().isEmpty())
            throw new ValidationException("L'email est obligatoire.");
        if (user.getMotDePasse() == null || user.getMotDePasse().isEmpty())
            throw new ValidationException("Le mot de passe est obligatoire.");
        if (user.getType() == null)
            throw new ValidationException("Le type de l'utilisateur est obligatoire.");
    }
}
