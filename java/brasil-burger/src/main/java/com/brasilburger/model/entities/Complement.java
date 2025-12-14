package com.brasilburger.model.entities;

import com.brasilburger.core.interfaces.IEntity;

public class Complement implements IEntity {

    private int id;
    private String nom;
    private double prix;
    private String imageUrl;
    private String type; // boisson, frites, autre

    public Complement() {
    }

    public Complement(int id, String nom, double prix, String imageUrl, String type) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.imageUrl = imageUrl;
        this.type = type;
    }

    // Getters & Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return nom + " (" + prix + " FCFA, " + type + ")";
    }
}
