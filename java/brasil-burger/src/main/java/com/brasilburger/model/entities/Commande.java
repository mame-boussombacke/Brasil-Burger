package com.brasilburger.model.entities;
import com.brasilburger.core.interfaces.IEntity;
import com.brasilburger.model.enums.StatutEntity;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Commande implements IEntity {
    private int id;
    private Client client;
    private List<Burger> burgers;
    private List<Menu> menus;
    private Zone zone;
    private LocalDateTime dateCommande;
    private boolean payee;
    private StatutEntity statut;
    private int clientId;
    private int zoneId;
    private String typeCommande;
    private double montantTotal;

    public Commande() {
        burgers = new ArrayList<>();
        menus = new ArrayList<>();
        dateCommande = LocalDateTime.now();
        payee = false;
        statut = StatutEntity.ACTIF;
    }

    public Commande(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.clientId = rs.getInt("client_id");
        this.typeCommande = rs.getString("type_commande");
        this.statut = StatutEntity.valueOf(rs.getString("statut"));
        this.montantTotal = rs.getDouble("montant_total");
        this.dateCommande = rs.getTimestamp("date_commande").toLocalDateTime();
        this.zoneId = rs.getInt("zone_id");
        this.burgers = new ArrayList<>();
        this.menus = new ArrayList<>();
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }

    public List<Burger> getBurgers() { return burgers; }
    public void setBurgers(List<Burger> burgers) { this.burgers = burgers; }

    public List<Menu> getMenus() { return menus; }
    public void setMenus(List<Menu> menus) { this.menus = menus; }

    public Zone getZone() { return zone; }
    public void setZone(Zone zone) { this.zone = zone; }

    public LocalDateTime getDateCommande() { return dateCommande; }
    public void setDateCommande(LocalDateTime dateCommande) { this.dateCommande = dateCommande; }

    public boolean isPayee() { return payee; }
    public void setPayee(boolean payee) { this.payee = payee; }

    public StatutEntity getStatut() { return statut; }
    public void setStatut(StatutEntity statut) { this.statut = statut; }

    public int getClientId() { return clientId; }
    public void setClientId(int clientId) { this.clientId = clientId; }

    public int getZoneId() { return zoneId; }
    public void setZoneId(int zoneId) { this.zoneId = zoneId; }

    public String getTypeCommande() { return typeCommande; }
    public void setTypeCommande(String typeCommande) { this.typeCommande = typeCommande; }

    public double getMontantTotal() { return montantTotal; }
    public void setMontantTotal(double montantTotal) { this.montantTotal = montantTotal; }

    public double calculerPrixTotal() {
        double total = 0;
        for (Burger b : burgers) total += b.getPrix();
        for (Menu m : menus) total += m.calculerPrix();
        if (zone != null) total += zone.getPrixLivraison();
        return total;
    }

    @Override
    public String toString() {
        return "Commande #" + id + " - Client : " + client.getNom() + " - Total : " + calculerPrixTotal() + " FCFA";
    }
}
