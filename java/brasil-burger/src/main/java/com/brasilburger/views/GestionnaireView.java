package com.brasilburger.views;

import com.brasilburger.core.abstracts.BaseView;
import com.brasilburger.utils.ConsoleHelper;

public class GestionnaireView extends BaseView {

    @Override
    public void show() {
        while (true) {
            ConsoleHelper.printHeader("GESTIONNAIRE");
            System.out.println("1. Gérer Burgers");
            System.out.println("2. Gérer Menus");
            System.out.println("3. Gérer Compléments");
            System.out.println("4. Gérer Commandes");
            System.out.println("5. Gérer Zones");
            System.out.println("0. Retour");

            int choix = ConsoleHelper.readInt("Votre choix");

            switch (choix) {
                case 1 -> new BurgerView().show();
                case 2 -> new MenuView().show();
                case 3 -> new ComplementView().show();
                case 4 -> new CommandeView().show();
                case 5 -> new ZoneView().show();
                case 0 -> { return; }
                default -> System.out.println("Choix invalide !");
            }
        }
    }
}
