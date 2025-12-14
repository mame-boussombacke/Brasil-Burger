package com.brasilburger.repositories;

import com.brasilburger.core.interfaces.IRepository;
import com.brasilburger.model.entities.Menu;
import com.brasilburger.exceptions.DataAccessException;
import com.brasilburger.config.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuRepository implements IRepository<Menu> {

    private Connection conn;

    public MenuRepository() {
        this.conn = DatabaseConnection.getConnection();
    }

    @Override
    public void create(Menu menu) throws DataAccessException {
        String sql = "INSERT INTO menus (nom, image_url) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, menu.getNom());
            stmt.setString(2, menu.getImageUrl());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) menu.setId(rs.getInt(1));
        } catch (SQLException e) {
            throw new DataAccessException("Erreur lors de la création du menu.", e);
        }
    }

    @Override
    public void update(Menu menu) throws DataAccessException {
        String sql = "UPDATE menus SET nom=?, image_url=? WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, menu.getNom());
            stmt.setString(2, menu.getImageUrl());
            stmt.setInt(3, menu.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Erreur lors de la mise à jour du menu.", e);
        }
    }

    @Override
    public void delete(int id) throws DataAccessException {
        String sql = "DELETE FROM menus WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Erreur lors de la suppression du menu.", e);
        }
    }

    @Override
    public Menu findById(int id) throws DataAccessException {
        String sql = "SELECT * FROM menus WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Menu menu = new Menu(rs.getInt("id"), rs.getString("nom"), rs.getString("image_url"));
                return menu;
            }
            return null;
        } catch (SQLException e) {
            throw new DataAccessException("Erreur lors de la récupération du menu.", e);
        }
    }

    @Override
    public List<Menu> findAll() throws DataAccessException {
        List<Menu> list = new ArrayList<>();
        String sql = "SELECT * FROM menus";
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                list.add(new Menu(rs.getInt("id"), rs.getString("nom"), rs.getString("image_url")));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Erreur lors de la récupération des menus.", e);
        }
        return list;
    }
}
