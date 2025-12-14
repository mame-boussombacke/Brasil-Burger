package com.brasilburger.model.entities;

import com.brasilburger.core.interfaces.IEntity;
import com.brasilburger.model.enums.StatutEntity;

public class Burger implements IEntity {
    private int id;
    private String nom;
    private double prix;
    private String imageUrl;
    private StatutEntity statut;

    public Burger() { }

    public Burger(int id, String nom, double prix, String imageUrl) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.imageUrl = imageUrl;
        this.statut = StatutEntity.ACTIF;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public double getPrix() { return prix; }
    public void setPrix(double prix) { this.prix = prix; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public StatutEntity getStatut() { return statut; }
    public void setStatut(StatutEntity statut) { this.statut = statut; }

    @Override
    public String toString() {
        return nom + " (" + prix + " FCFA)";
    }
}
