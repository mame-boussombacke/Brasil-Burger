package com.brasilburger.model.entities;
import com.brasilburger.core.abstracts.BaseEntity;
import com.brasilburger.model.enums.TypeUtilisateur;

public class Utilisateur extends BaseEntity {

    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;
    private TypeUtilisateur type; // CLIENT ou GESTIONNAIRE

    // Constructeur vide
    public Utilisateur() {
    }

    // Constructeur complet
    public Utilisateur(int id, String nom, String prenom, String email, String motDePasse, TypeUtilisateur type) {
        this.setId(id);  // utilise le setter de BaseEntity
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.type = type;
    }

    // Getters et Setters
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public TypeUtilisateur getType() {
        return type;
    }

    public void setType(TypeUtilisateur type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id=" + getId() +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", type=" + type +
                '}';
    }
}
