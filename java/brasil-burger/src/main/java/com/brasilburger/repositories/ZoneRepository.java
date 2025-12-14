package com.brasilburger.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.brasilburger.config.DatabaseConnection;
import com.brasilburger.core.interfaces.IRepository;
import com.brasilburger.exceptions.DataAccessException;
import com.brasilburger.model.entities.Zone;
import com.brasilburger.model.enums.StatutEntity;

public class ZoneRepository implements IRepository<Zone> {

    private Connection conn;

    public ZoneRepository() {
        conn = DatabaseConnection.getConnection();
    }

    @Override
    public void create(Zone zone) throws DataAccessException {
        String sql = "INSERT INTO zone (nom, prix_livraison, statut) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, zone.getNom());
            stmt.setDouble(2, zone.getPrixLivraison());
            stmt.setString(3, zone.getStatut().name());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) zone.setId(rs.getInt(1));
        } catch (SQLException e) {
            throw new DataAccessException("Erreur lors de la création de la zone.", e);
        }
    }

    @Override
    public void update(Zone zone) throws DataAccessException {
        String sql = "UPDATE zone SET nom=?, prix_livraison=?, statut=? WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, zone.getNom());
            stmt.setDouble(2, zone.getPrixLivraison());
            stmt.setString(3, zone.getStatut().name());
            stmt.setInt(4, zone.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Erreur lors de la mise à jour de la zone.", e);
        }
    }

    @Override
    public void delete(int id) throws DataAccessException {
        String sql = "DELETE FROM zone WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Erreur lors de la suppression de la zone.", e);
        }
    }

    @Override
    public Zone findById(int id) throws DataAccessException {
        String sql = "SELECT * FROM zone WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Zone zone = new Zone(rs.getInt("id"), rs.getString("nom"), rs.getDouble("prix_livraison"));
                zone.setStatut(StatutEntity.valueOf(rs.getString("statut")));
                return zone;
            }
            return null;
        } catch (SQLException e) {
            throw new DataAccessException("Erreur lors de la récupération de la zone.", e);
        }
    }

    @Override
    public List<Zone> findAll() throws DataAccessException {
        List<Zone> list = new ArrayList<>();
        String sql = "SELECT * FROM zone";
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Zone zone = new Zone(rs.getInt("id"), rs.getString("nom"), rs.getDouble("prix_livraison"));
                zone.setStatut(StatutEntity.valueOf(rs.getString("statut")));
                list.add(zone);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Erreur lors de la récupération des zones.", e);
        }
        return list;
    }
}
