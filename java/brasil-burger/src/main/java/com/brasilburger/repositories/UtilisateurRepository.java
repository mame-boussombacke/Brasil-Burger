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
import com.brasilburger.model.entities.Utilisateur;
import com.brasilburger.model.enums.TypeUtilisateur;

public class UtilisateurRepository implements IRepository<Utilisateur> {

    private Connection conn;

    public UtilisateurRepository() {
        this.conn = DatabaseConnection.getConnection();
    }

    @Override
    public void create(Utilisateur user) throws DataAccessException {
        String sql = "INSERT INTO utilisateur (nom, prenom, email, motdepasse, type) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getNom());
            stmt.setString(2, user.getPrenom());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getMotDePasse());
            stmt.setString(5, user.getType().name());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Erreur lors de la création de l'utilisateur.", e);
        }
    }

    @Override
    public void update(Utilisateur user) throws DataAccessException {
        String sql = "UPDATE utilisateur SET nom=?, prenom=?, email=?, motdepasse=?, type=? WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getNom());
            stmt.setString(2, user.getPrenom());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getMotDePasse());
            stmt.setString(5, user.getType().name());
            stmt.setInt(6, user.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Erreur lors de la mise à jour de l'utilisateur.", e);
        }
    }

    @Override
    public void delete(int id) throws DataAccessException {
        String sql = "DELETE FROM utilisateur WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Erreur lors de la suppression de l'utilisateur.", e);
        }
    }

    @Override
    public Utilisateur findById(int id) throws DataAccessException {
        String sql = "SELECT * FROM utilisateur WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Utilisateur(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("motdepasse"),
                        TypeUtilisateur.valueOf(rs.getString("type"))
                );
            }
            return null;
        } catch (SQLException e) {
            throw new DataAccessException("Erreur lors de la récupération de l'utilisateur.", e);
        }
    }

    @Override
    public List<Utilisateur> findAll() throws DataAccessException {
        List<Utilisateur> users = new ArrayList<>();
        String sql = "SELECT * FROM utilisateur";
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                users.add(new Utilisateur(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("motdepasse"),
                        TypeUtilisateur.valueOf(rs.getString("type"))
                ));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Erreur lors de la récupération des utilisateurs.", e);
        }
        return users;
    }

    // Méthode pour chercher un utilisateur par email (login)
    public Utilisateur findByEmail(String email) throws DataAccessException {
        String sql = "SELECT * FROM utilisateur WHERE email=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Utilisateur(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("motdepasse"),
                        TypeUtilisateur.valueOf(rs.getString("type"))
                );
            }
            return null;
        } catch (SQLException e) {
            throw new DataAccessException("Erreur lors de la récupération de l'utilisateur par email.", e);
        }
    }
}

