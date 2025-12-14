package com.brasilburger.views;

import com.brasilburger.core.abstracts.BaseView;
import com.brasilburger.utils.ConsoleHelper;

public class MainView extends BaseView {

    @Override
    public void show() {
        while (true) {
            ConsoleHelper.printHeader("BRASIL BURGER - Menu Principal");
            System.out.println("1. Gestionnaire");
            System.out.println("2. Client");
            System.out.println("3. Utilisateurs");
            System.out.println("0. Quitter");

            int choix = ConsoleHelper.readInt("Votre choix");

            switch (choix) {
                case 1 -> new GestionnaireView().show();
                case 2 -> new ClientView().show();
                case 3 -> new UtilisateurView().show();
                case 0 -> {
                    System.out.println("Merci d'avoir utilisé Brasil Burger !");
                    return;
                }
                default -> System.out.println("Choix invalide !");
            }
        }
    }
}
