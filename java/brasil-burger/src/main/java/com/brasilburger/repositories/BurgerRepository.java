package com.brasilburger.repositories;
import com.brasilburger.core.interfaces.IRepository;
import com.brasilburger.model.entities.Burger;
import com.brasilburger.exceptions.DataAccessException;
import com.brasilburger.config.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BurgerRepository implements IRepository<Burger> {

    private Connection conn;

    public BurgerRepository() {
        this.conn = DatabaseConnection.getConnection();
    }

    @Override
    public void create(Burger burger) throws DataAccessException {
        String sql = "INSERT INTO burgers (nom, prix, image_url) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, burger.getNom());
            stmt.setDouble(2, burger.getPrix());
            stmt.setString(3, burger.getImageUrl());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Erreur lors de la création du burger.", e);
        }
    }

    @Override
    public void update(Burger burger) throws DataAccessException {
        String sql = "UPDATE burgers SET nom=?, prix=?, image_url=? WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, burger.getNom());
            stmt.setDouble(2, burger.getPrix());
            stmt.setString(3, burger.getImageUrl());
            stmt.setInt(4, burger.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Erreur lors de la mise à jour du burger.", e);
        }
    }

    @Override
    public void delete(int id) throws DataAccessException {
        String sql = "DELETE FROM burgers WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Erreur lors de la suppression du burger.", e);
        }
    }

    @Override
    public Burger findById(int id) throws DataAccessException {
        String sql = "SELECT * FROM burgers WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Burger(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getDouble("prix"),
                    rs.getString("image_url")
                );
            }
            return null;
        } catch (SQLException e) {
            throw new DataAccessException("Erreur lors de la récupération du burger.", e);
        }
    }

    @Override
    public List<Burger> findAll() throws DataAccessException {
        List<Burger> burgers = new ArrayList<>();
        String sql = "SELECT * FROM burgers";
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                burgers.add(new Burger(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getDouble("prix"),
                    rs.getString("image_url")
                ));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Erreur lors de la récupération des burgers.", e);
        }
        return burgers;
    }
}
