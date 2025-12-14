package com.brasilburger.views;

import java.util.List;

import com.brasilburger.core.abstracts.BaseView;
import com.brasilburger.exceptions.BusinessException;
import com.brasilburger.model.entities.Menu;
import com.brasilburger.services.MenuService;
import com.brasilburger.utils.ConsoleHelper;

public class MenuView extends BaseView {

    private final MenuService service = new MenuService();

    @Override
    public void show() {
        while (true) {
            ConsoleHelper.printHeader("GESTION DES MENUS");
            System.out.println("1. Ajouter Menu");
            System.out.println("2. Modifier Menu");
            System.out.println("3. Supprimer Menu");
            System.out.println("4. Lister Menus");
            System.out.println("0. Retour");

            int choix = ConsoleHelper.readInt("Votre choix");

            switch (choix) {
                case 1 -> ajouterMenu();
                case 2 -> modifierMenu();
                case 3 -> supprimerMenu();
                case 4 -> listerMenus();
                case 0 -> { return; }
                default -> System.out.println("Choix invalide !");
            }
        }
    }

    private void ajouterMenu() {
        String nom = ConsoleHelper.readString("Nom du menu");
        System.out.println("Exemple URL image: https://i.imgur.com/abcd123.jpg");
        String imageUrl = ConsoleHelper.readString("URL de l'image (publique)");
        Menu menu = new Menu();
        menu.setNom(nom);
        menu.setImageUrl(imageUrl);

        try {
            service.ajouterMenu(menu);
            System.out.println("Menu ajouté !");
        } catch (BusinessException e) {
            System.out.println("Erreur: " + e.getMessage());
        }
        ConsoleHelper.pause();
    }

    private void modifierMenu() {
        int id = ConsoleHelper.readInt("ID du menu à modifier");
        try {
            Menu menu = service.getMenuById(id);
            if (menu == null) {
                System.out.println("Menu introuvable !");
                return;
            }
            String nom = ConsoleHelper.readString("Nouveau nom (" + menu.getNom() + ")");
            System.out.println("URL actuelle: " + menu.getImageUrl());
            String imageUrl = ConsoleHelper.readString("Nouvelle URL image (publique)");
            menu.setNom(nom);
            menu.setImageUrl(imageUrl);

            service.modifierMenu(menu);
            System.out.println("Menu modifié !");
        } catch (BusinessException e) {
            System.out.println("Erreur: " + e.getMessage());
        }
        ConsoleHelper.pause();
    }

    private void supprimerMenu() {
        int id = ConsoleHelper.readInt("ID du menu à supprimer");
        try {
            service.supprimerMenu(id);
            System.out.println("Menu supprimé !");
        } catch (BusinessException e) {
            System.out.println("Erreur: " + e.getMessage());
        }
        ConsoleHelper.pause();
    }

    private void listerMenus() {
        try {
            List<Menu> list = service.getAllMenus();
            if (list.isEmpty()) {
                System.out.println("Aucun menu trouvé !");
                return;
            }
            System.out.println("\n=== Menus Disponibles ===");
            for (Menu m : list) {
                System.out.println(m.getId() + " - " + m.getNom() + 
                                   " (Burgers: " + m.getBurgers().size() + 
                                   ", Compléments: " + m.getComplements().size() + 
                                   ", Image: " + m.getImageUrl() + ")");
            }
        } catch (BusinessException e) {
            System.out.println("Erreur: " + e.getMessage());
        }
        ConsoleHelper.pause();
    }
}
