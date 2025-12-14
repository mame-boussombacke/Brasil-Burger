package com.brasilburger.views;

import java.util.List;

import com.brasilburger.core.abstracts.BaseView;
import com.brasilburger.exceptions.BusinessException;
import com.brasilburger.model.entities.Complement;
import com.brasilburger.services.ComplementService;
import com.brasilburger.utils.ConsoleHelper;

public class ComplementView extends BaseView {

    private final ComplementService service = new ComplementService();

    @Override
    public void show() {
        while (true) {
            ConsoleHelper.printHeader("GESTION DES COMPLÉMENTS");
            System.out.println("1. Ajouter Complément");
            System.out.println("2. Modifier Complément");
            System.out.println("3. Supprimer Complément");
            System.out.println("4. Lister Compléments");
            System.out.println("0. Retour");

            int choix = ConsoleHelper.readInt("Votre choix");

            switch (choix) {
                case 1 -> ajouterComplement();
                case 2 -> modifierComplement();
                case 3 -> supprimerComplement();
                case 4 -> listerComplements();
                case 0 -> { return; }
                default -> System.out.println("Choix invalide !");
            }
        }
    }

    private void ajouterComplement() {
        String nom = ConsoleHelper.readString("Nom du complément");
        double prix = ConsoleHelper.readDouble("Prix du complément");

        System.out.println("Types possibles : FRITES / BOISSON / AUTRE");
        String type = ConsoleHelper.readString("Type du complément").toUpperCase();

        System.out.println("Exemple URL image: https://i.imgur.com/abcd123.jpg");
        String imageUrl = ConsoleHelper.readString("URL de l'image (publique)");

        Complement complement = new Complement();
        complement.setNom(nom);
        complement.setPrix(prix);
        complement.setType(type);
        complement.setImageUrl(imageUrl);

        try {
            service.ajouterComplement(complement);
            System.out.println("✅ Complément ajouté avec succès !");
        } catch (BusinessException e) {
            System.out.println("❌ Erreur : " + e.getMessage());
        }
        ConsoleHelper.pause();
    }

    private void modifierComplement() {
        int id = ConsoleHelper.readInt("ID du complément à modifier");

        try {
            Complement c = service.getComplementById(id);
            if (c == null) {
                System.out.println("❌ Complément introuvable !");
                return;
            }

            String nom = ConsoleHelper.readString("Nouveau nom (" + c.getNom() + ")");
            double prix = ConsoleHelper.readDouble("Nouveau prix (" + c.getPrix() + ")");

            System.out.println("Types possibles : FRITES / BOISSON / AUTRE");
            String type = ConsoleHelper.readString("Nouveau type (" + c.getType() + ")").toUpperCase();

            System.out.println("URL actuelle : " + c.getImageUrl());
            String imageUrl = ConsoleHelper.readString("Nouvelle URL image");

            c.setNom(nom);
            c.setPrix(prix);
            c.setType(type);
            c.setImageUrl(imageUrl);

            service.modifierComplement(c);
            System.out.println("✅ Complément modifié avec succès !");
        } catch (BusinessException e) {
            System.out.println("❌ Erreur : " + e.getMessage());
        }
        ConsoleHelper.pause();
    }

    private void supprimerComplement() {
        int id = ConsoleHelper.readInt("ID du complément à supprimer");

        try {
            service.supprimerComplement(id);
            System.out.println("✅ Complément supprimé !");
        } catch (BusinessException e) {
            System.out.println("❌ Erreur : " + e.getMessage());
        }
        ConsoleHelper.pause();
    }

    private void listerComplements() {
        try {
            List<Complement> list = service.getAllComplements();
            if (list.isEmpty()) {
                System.out.println("⚠ Aucun complément trouvé !");
                return;
            }

            ConsoleHelper.printHeader("LISTE DES COMPLÉMENTS");
            for (Complement c : list) {
                System.out.println(
                    c.getId() + " | " +
                    c.getNom() +
                    " | Type: " + c.getType() +
                    " | Prix: " + c.getPrix() + " FCFA" +
                    " | Image: " + c.getImageUrl()
                );
            }
        } catch (BusinessException e) {
            System.out.println("❌ Erreur : " + e.getMessage());
        }
        ConsoleHelper.pause();
    }
}
