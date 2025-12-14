package com.brasilburger.model.entities;

import com.brasilburger.core.interfaces.IEntity;
import com.brasilburger.model.enums.StatutEntity;

public class Zone implements IEntity {
    private int id;
    private String nom;
    private double prixLivraison;
    private StatutEntity statut;

    public Zone() { this.statut = StatutEntity.ACTIF; }

    public Zone(int id, String nom, double prixLivraison) {
        this();
        this.id = id;
        this.nom = nom;
        this.prixLivraison = prixLivraison;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public double getPrixLivraison() { return prixLivraison; }
    public void setPrixLivraison(double prixLivraison) { this.prixLivraison = prixLivraison; }

    public StatutEntity getStatut() { return statut; }
    public void setStatut(StatutEntity statut) { this.statut = statut; }

    @Override
    public String toString() {
        return nom + " (Livraison : " + prixLivraison + " FCFA)";
    }
}
