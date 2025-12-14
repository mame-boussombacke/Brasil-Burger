package com.brasilburger.views;

import com.brasilburger.core.abstracts.BaseView;
import com.brasilburger.exceptions.BusinessException;
import com.brasilburger.model.entities.Utilisateur;
import com.brasilburger.model.enums.TypeUtilisateur;
import com.brasilburger.services.UtilisateurService;
import com.brasilburger.utils.ConsoleHelper;

public class UtilisateurView extends BaseView {

    private final UtilisateurService service = new UtilisateurService();

    @Override
    public void show() {
        while (true) {
            ConsoleHelper.printHeader("GESTION UTILISATEURS");
            System.out.println("1. Se connecter");
            System.out.println("2. Créer un compte");
            System.out.println("0. Retour");

            int choix = ConsoleHelper.readInt("Votre choix");

            switch (choix) {
                case 1 -> connexion();
                case 2 -> creerCompte();
                case 0 -> { return; }
                default -> System.out.println("Choix invalide !");
            }
        }
    }

    private void connexion() {
        String email = ConsoleHelper.readString("Email");
        String mdp = ConsoleHelper.readString("Mot de passe");

        try {
            Utilisateur user = service.login(email, mdp);
            if (user == null) {
                System.out.println("Email ou mot de passe incorrect !");
            } else {
                System.out.println("Connexion réussie, bienvenue " + user.getNom() + " !");
                if (user.getType() == TypeUtilisateur.GESTIONNAIRE) {
                    new GestionnaireView().show();
                } else {
                    new ClientView().show();
                }
            }
        } catch (BusinessException e) {
            System.out.println("Erreur: " + e.getMessage());
        }
        ConsoleHelper.pause();
    }

    private void creerCompte() {
        String nom = ConsoleHelper.readString("Nom");
        String prenom = ConsoleHelper.readString("Prénom");
        String email = ConsoleHelper.readString("Email");
        String mdp = ConsoleHelper.readString("Mot de passe");

        Utilisateur user = new Utilisateur();
        user.setNom(nom);
        user.setPrenom(prenom);
        user.setEmail(email);
        user.setMotDePasse(mdp);
        user.setType(TypeUtilisateur.CLIENT);

        try {
            service.ajouterUtilisateur(user);
            System.out.println("Compte créé avec succès !");
        } catch (BusinessException e) {
            System.out.println("Erreur: " + e.getMessage());
        }
        ConsoleHelper.pause();
    }
}
