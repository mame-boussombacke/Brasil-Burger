package com.brasilburger.views;

import java.util.List;

import com.brasilburger.core.abstracts.BaseView;
import com.brasilburger.exceptions.BusinessException;
import com.brasilburger.model.entities.Zone;
import com.brasilburger.services.ZoneService;
import com.brasilburger.utils.ConsoleHelper;

public class ZoneView extends BaseView {

    private final ZoneService service = new ZoneService();

    @Override
    public void show() {
        while (true) {
            ConsoleHelper.printHeader("GESTION DES ZONES");
            System.out.println("1. Ajouter Zone");
            System.out.println("2. Modifier Zone");
            System.out.println("3. Supprimer Zone");
            System.out.println("4. Lister Zones");
            System.out.println("0. Retour");

            int choix = ConsoleHelper.readInt("Votre choix");

            switch (choix) {
                case 1 -> ajouterZone();
                case 2 -> modifierZone();
                case 3 -> supprimerZone();
                case 4 -> listerZones();
                case 0 -> { return; }
                default -> System.out.println("Choix invalide !");
            }
        }
    }

    private void ajouterZone() {
        String nom = ConsoleHelper.readString("Nom de la zone");
        double prix = ConsoleHelper.readDouble("Prix de livraison");
        Zone zone = new Zone();
        zone.setNom(nom);
        zone.setPrixLivraison(prix);

        try {
            service.createZone(zone);
            System.out.println("Zone ajoutée !");
        } catch (BusinessException e) {
            System.out.println("Erreur: " + e.getMessage());
        }
        ConsoleHelper.pause();
    }

    private void modifierZone() {
        int id = ConsoleHelper.readInt("ID de la zone à modifier");
        try {
            Zone zone = service.getZoneById(id);
            if (zone == null) {
                System.out.println("Zone introuvable !");
                return;
            }
            String nom = ConsoleHelper.readString("Nouveau nom (" + zone.getNom() + ")");
            double prix = ConsoleHelper.readDouble("Nouveau prix (" + zone.getPrixLivraison() + ")");
            zone.setNom(nom);
            zone.setPrixLivraison(prix);
            service.updateZone(zone);
            System.out.println("Zone modifiée !");
        } catch (BusinessException e) {
            System.out.println("Erreur: " + e.getMessage());
        }
        ConsoleHelper.pause();
    }

    private void supprimerZone() {
        int id = ConsoleHelper.readInt("ID de la zone à supprimer");
        try {
            service.deleteZone(id);
            System.out.println("Zone supprimée !");
        } catch (BusinessException e) {
            System.out.println("Erreur: " + e.getMessage());
        }
        ConsoleHelper.pause();
    }

    private void listerZones() {
        try {
            List<Zone> zones = service.getAllZones();
            if (zones.isEmpty()) {
                System.out.println("Aucune zone trouvée !");
                return;
            }
            for (Zone z : zones) {
                System.out.println(z.getId() + " - " + z.getNom() + " : " + z.getPrixLivraison() + " FCFA");
            }
        } catch (BusinessException e) {
            System.out.println("Erreur: " + e.getMessage());
        }
        ConsoleHelper.pause();
    }
}
