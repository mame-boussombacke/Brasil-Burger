package com.brasilburger.repositories;
import com.brasilburger.core.interfaces.IRepository;
import com.brasilburger.model.entities.Client;
import com.brasilburger.exceptions.DataAccessException;
import com.brasilburger.config.DatabaseConnection;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientRepository implements IRepository<Client> {

    private Connection conn;

    public ClientRepository() {
        conn = DatabaseConnection.getConnection();
    }

    @Override
    public void create(Client client) throws DataAccessException {
        String sql = "INSERT INTO clients (nom, prenom, telephone) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, client.getNom());
            stmt.setString(2, client.getPrenom());
            stmt.setString(3, client.getTelephone());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) client.setId(rs.getInt(1));
        } catch (SQLException e) {
            throw new DataAccessException("Erreur lors de la création du client.", e);
        }
    }

    @Override
    public void update(Client client) throws DataAccessException {
        String sql = "UPDATE clients SET nom=?, prenom=?, telephone=? WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, client.getNom());
            stmt.setString(2, client.getPrenom());
            stmt.setString(3, client.getTelephone());
            stmt.setInt(4, client.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Erreur lors de la mise à jour du client.", e);
        }
    }

    @Override
    public void delete(int id) throws DataAccessException {
        String sql = "DELETE FROM clients WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Erreur lors de la suppression du client.", e);
        }
    }

    @Override
    public Client findById(int id) throws DataAccessException {
        String sql = "SELECT * FROM clients WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return new Client(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"), rs.getString("telephone"), "");
            return null;
        } catch (SQLException e) {
            throw new DataAccessException("Erreur lors de la récupération du client.", e);
        }
    }

    @Override
    public List<Client> findAll() throws DataAccessException {
        List<Client> list = new ArrayList<>();
        String sql = "SELECT * FROM clients";
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                list.add(new Client(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"), rs.getString("telephone"), ""));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Erreur lors de la récupération des clients.", e);
        }
        return list;
    }

    public Client findByTelephone(String tel) throws DataAccessException {
        String sql = "SELECT * FROM clients WHERE telephone=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tel);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return new Client(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"), rs.getString("telephone"), "");
            return null;
        } catch (SQLException e) {
            throw new DataAccessException("Erreur lors de la récupération du client par téléphone.", e);
        }
    }
}
