package com.brasilburger.repositories;
import com.brasilburger.core.interfaces.IRepository;
import com.brasilburger.model.entities.Commande;
import com.brasilburger.exceptions.DataAccessException;
import com.brasilburger.config.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommandeRepository implements IRepository<Commande> {

    private Connection conn;

    public CommandeRepository() {
        conn = DatabaseConnection.getConnection();
    }

    @Override
    public void create(Commande cmd) throws DataAccessException {
        String sql = "INSERT INTO commandes (client_id, type_commande, statut, montant_total, date_commande, zone_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, cmd.getClientId());
            stmt.setString(2, cmd.getTypeCommande());
            stmt.setString(3, cmd.getStatut().name());
            stmt.setDouble(4, cmd.getMontantTotal());
            stmt.setTimestamp(5, Timestamp.valueOf(cmd.getDateCommande()));
            stmt.setInt(6, cmd.getZoneId());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) cmd.setId(rs.getInt(1));
        } catch (SQLException e) {
            throw new DataAccessException("Erreur lors de la création de la commande.", e);
        }
    }

    @Override
    public void update(Commande cmd) throws DataAccessException {
        String sql = "UPDATE commandes SET client_id=?, type_commande=?, statut=?, montant_total=?, date_commande=?, zone_id=? WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cmd.getClientId());
            stmt.setString(2, cmd.getTypeCommande());
            stmt.setString(3, cmd.getStatut().name());
            stmt.setDouble(4, cmd.getMontantTotal());
            stmt.setTimestamp(5, Timestamp.valueOf(cmd.getDateCommande()));
            stmt.setInt(6, cmd.getZoneId());
            stmt.setInt(7, cmd.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Erreur lors de la mise à jour de la commande.", e);
        }
    }

    @Override
    public void delete(int id) throws DataAccessException {
        String sql = "DELETE FROM commandes WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Erreur lors de la suppression de la commande.", e);
        }
    }

    @Override
    public Commande findById(int id) throws DataAccessException {
        String sql = "SELECT * FROM commandes WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return new Commande(rs); // constructeur Commande(ResultSet rs)
            return null;
        } catch (SQLException e) {
            throw new DataAccessException("Erreur lors de la récupération de la commande.", e);
        }
    }

    @Override
    public List<Commande> findAll() throws DataAccessException {
        List<Commande> list = new ArrayList<>();
        String sql = "SELECT * FROM commandes";
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                list.add(new Commande(rs));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Erreur lors de la récupération des commandes.", e);
        }
        return list;
    }

    public List<Commande> findByClient(int clientId) throws DataAccessException {
        List<Commande> list = new ArrayList<>();
        String sql = "SELECT * FROM commandes WHERE client_id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, clientId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new Commande(rs));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Erreur lors de la récupération des commandes par client.", e);
        }
        return list;
    }
}
