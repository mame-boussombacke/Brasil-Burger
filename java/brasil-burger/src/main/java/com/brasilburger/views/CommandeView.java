package com.brasilburger.views;

import java.util.List;

import com.brasilburger.core.abstracts.BaseView;
import com.brasilburger.exceptions.BusinessException;
import com.brasilburger.model.entities.Commande;
import com.brasilburger.services.CommandeService;
import com.brasilburger.utils.ConsoleHelper;

public class CommandeView extends BaseView {

    private final CommandeService service = new CommandeService();

    @Override
    public void show() {
        while (true) {
            ConsoleHelper.printHeader("GESTION DES COMMANDES");
            System.out.println("1. Passer une commande");
            System.out.println("2. Lister les commandes");
            System.out.println("3. Annuler une commande");
            System.out.println("0. Retour");

            int choix = ConsoleHelper.readInt("Votre choix");

            switch (choix) {
                case 1 -> passerCommande();
                case 2 -> listerCommandes();
                case 3 -> annulerCommande();
                case 0 -> { return; }
                default -> System.out.println("Choix invalide !");
            }
        }
    }

    private void passerCommande() {
        try {
            Commande commande = new Commande();
            service.ajouterCommande(commande);
            System.out.println("Commande enregistrée avec succès !");
        } catch (BusinessException e) {
            System.out.println("Erreur: " + e.getMessage());
        }
        ConsoleHelper.pause();
    }

    private void listerCommandes() {
        try {
            List<Commande> commandes = service.getAllCommandes();
            if (commandes.isEmpty()) {
                System.out.println("Aucune commande trouvée !");
                return;
            }
            for (Commande c : commandes) {
                System.out.println(c);
            }
        } catch (BusinessException e) {
            System.out.println("Erreur: " + e.getMessage());
        }
        ConsoleHelper.pause();
    }

    private void annulerCommande() {
        int id = ConsoleHelper.readInt("ID de la commande à annuler");
        try {
            service.supprimerCommande(id);
            System.out.println("Commande annulée !");
        } catch (BusinessException e) {
            System.out.println("Erreur: " + e.getMessage());
        }
        ConsoleHelper.pause();
    }
}
