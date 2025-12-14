package com.brasilburger.views;

import java.util.List;

import com.brasilburger.core.abstracts.BaseView;
import com.brasilburger.exceptions.BusinessException;
import com.brasilburger.model.entities.Burger;
import com.brasilburger.services.BurgerService;
import com.brasilburger.utils.ConsoleHelper;

public class BurgerView extends BaseView {

    private final BurgerService service = new BurgerService();

    @Override
    public void show() {
        while (true) {
            ConsoleHelper.printHeader("GESTION DES BURGERS");
            System.out.println("1. Ajouter Burger");
            System.out.println("2. Modifier Burger");
            System.out.println("3. Supprimer Burger");
            System.out.println("4. Lister Burgers");
            System.out.println("0. Retour");

            int choix = ConsoleHelper.readInt("Votre choix");

            switch (choix) {
                case 1 -> ajouterBurger();
                case 2 -> modifierBurger();
                case 3 -> supprimerBurger();
                case 4 -> listerBurgers();
                case 0 -> { return; }
                default -> System.out.println("Choix invalide !");
            }
        }
    }

    private void ajouterBurger() {
        String nom = ConsoleHelper.readString("Nom du burger");
        double prix = ConsoleHelper.readDouble("Prix du burger");
        System.out.println("Exemple URL image: https://i.imgur.com/abcd123.jpg");
        String imageUrl = ConsoleHelper.readString("URL de l'image (publique)");

        Burger burger = new Burger();
        burger.setNom(nom);
        burger.setPrix(prix);
        burger.setImageUrl(imageUrl);

        try {
            service.ajouterBurger(burger);
            System.out.println("Burger ajouté avec succès !");
        } catch (BusinessException e) {
            System.out.println("Erreur: " + e.getMessage());
        }
        ConsoleHelper.pause();
    }

    private void modifierBurger() {
        int id = ConsoleHelper.readInt("ID du burger à modifier");
        try {
            Burger burger = service.getBurgerById(id);
            if (burger == null) { System.out.println("Burger introuvable !"); return; }

            String nom = ConsoleHelper.readString("Nouveau nom (" + burger.getNom() + ")");
            double prix = ConsoleHelper.readDouble("Nouveau prix (" + burger.getPrix() + ")");
            System.out.println("URL actuelle: " + burger.getImageUrl());
            String imageUrl = ConsoleHelper.readString("Nouvelle URL image (publique)");

            burger.setNom(nom);
            burger.setPrix(prix);
            burger.setImageUrl(imageUrl);

            service.modifierBurger(burger);
            System.out.println("Burger modifié avec succès !");
        } catch (BusinessException e) {
            System.out.println("Erreur: " + e.getMessage());
        }
        ConsoleHelper.pause();
    }

    private void supprimerBurger() {
        int id = ConsoleHelper.readInt("ID du burger à supprimer");
        try {
            service.supprimerBurger(id);
            System.out.println("Burger supprimé avec succès !");
        } catch (BusinessException e) {
            System.out.println("Erreur: " + e.getMessage());
        }
        ConsoleHelper.pause();
    }

    private void listerBurgers() {
        try {
            List<Burger> burgers = service.getAllBurgers();
            if (burgers.isEmpty()) { System.out.println("Aucun burger trouvé !"); return; }

            for (Burger b : burgers) {
                System.out.println(b.getId() + " - " + b.getNom() + 
                                   " : " + b.getPrix() + " FCFA" + 
                                   " (Image: " + b.getImageUrl() + ")");
            }
        } catch (BusinessException e) {
            System.out.println("Erreur: " + e.getMessage());
        }
        ConsoleHelper.pause();
    }
}
