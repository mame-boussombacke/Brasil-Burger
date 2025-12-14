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
import com.brasilburger.model.entities.Complement;

public class ComplementRepository implements IRepository<Complement> {

    private final Connection conn;

    public ComplementRepository() {
        this.conn = DatabaseConnection.getConnection();
    }

    @Override
    public void create(Complement complement) throws DataAccessException {
        String sql = "INSERT INTO complements (nom, prix, image_url, type) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, complement.getNom());
            stmt.setDouble(2, complement.getPrix());
            stmt.setString(3, complement.getImageUrl());
            stmt.setString(4, complement.getType()); 
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Erreur lors de la création du complément.", e);
        }
    }

    @Override
    public void update(Complement complement) throws DataAccessException {
        String sql = "UPDATE complements SET nom=?, prix=?, image_url=?, type=? WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, complement.getNom());
            stmt.setDouble(2, complement.getPrix());
            stmt.setString(3, complement.getImageUrl());
            stmt.setString(4, complement.getType()); 
            stmt.setInt(5, complement.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Erreur lors de la mise à jour du complément.", e);
        }
    }

    @Override
    public void delete(int id) throws DataAccessException {
        String sql = "DELETE FROM complements WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Erreur lors de la suppression du complément.", e);
        }
    }

    @Override
    public Complement findById(int id) throws DataAccessException {
        String sql = "SELECT * FROM complements WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Complement(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getDouble("prix"),
                        rs.getString("image_url"),
                        rs.getString("type") 
                );
            }
            return null;
        } catch (SQLException e) {
            throw new DataAccessException("Erreur lors de la récupération du complément.", e);
        }
    }

    @Override
    public List<Complement> findAll() throws DataAccessException {
        List<Complement> list = new ArrayList<>();
        String sql = "SELECT * FROM complements";

        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                list.add(new Complement(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getDouble("prix"),
                        rs.getString("image_url"),
                        rs.getString("type") 
                ));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Erreur lors de la récupération des compléments.", e);
        }
        return list;
    }
}
