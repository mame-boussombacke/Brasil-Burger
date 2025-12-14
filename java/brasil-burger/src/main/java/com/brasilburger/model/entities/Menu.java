package com.brasilburger.model.entities;

import com.brasilburger.core.interfaces.IEntity;
import com.brasilburger.model.enums.StatutEntity;
import java.util.ArrayList;
import java.util.List;

public class Menu implements IEntity {
    private int id;
    private String nom;
    private String imageUrl;
    private List<Burger> burgers;
    private List<Complement> complements;
    private StatutEntity statut;

    public Menu() {
        burgers = new ArrayList<>();
        complements = new ArrayList<>();
        statut = StatutEntity.ACTIF;
    }

    public Menu(int id, String nom, String imageUrl) {
        this();
        this.id = id;
        this.nom = nom;
        this.imageUrl = imageUrl;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public List<Burger> getBurgers() { return burgers; }
    public void setBurgers(List<Burger> burgers) { this.burgers = burgers; }

    public List<Complement> getComplements() { return complements; }
    public void setComplements(List<Complement> complements) { this.complements = complements; }

    public StatutEntity getStatut() { return statut; }
    public void setStatut(StatutEntity statut) { this.statut = statut; }

    public double calculerPrix() {
        double total = 0;
        for (Burger b : burgers) total += b.getPrix();
        for (Complement c : complements) total += c.getPrix();
        return total;
    }

    @Override
    public String toString() {
        return nom + " (" + calculerPrix() + " FCFA)";
    }
}
