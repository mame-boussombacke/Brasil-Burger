package com.brasilburger.views;

import com.brasilburger.core.abstracts.BaseView;
import com.brasilburger.utils.ConsoleHelper;

public class ClientView extends BaseView {

    @Override
    public void show() {
        while (true) {
            ConsoleHelper.printHeader("CLIENT");
            System.out.println("1. Voir Burgers");
            System.out.println("2. Voir Menus");
            System.out.println("3. Passer une commande");
            System.out.println("4. Suivre mes commandes");
            System.out.println("0. Retour");

            int choix = ConsoleHelper.readInt("Votre choix");

            switch (choix) {
                case 1 -> new BurgerView().show();
                case 2 -> new MenuView().show();
                case 3 -> new CommandeView().show();
                case 4 -> System.out.println("Suivi des commandes - À implémenter");
                case 0 -> { return; }
                default -> System.out.println("Choix invalide !");
            }
        }
    }
}
