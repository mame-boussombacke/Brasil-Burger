package com.brasilburger;

import com.brasilburger.views.MainView;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== Bienvenue sur Brasil Burger Console ===\n");

        // Lancer la vue principale qui gère tout le menu
        MainView mainView = new MainView();
        mainView.show();

    }
}
