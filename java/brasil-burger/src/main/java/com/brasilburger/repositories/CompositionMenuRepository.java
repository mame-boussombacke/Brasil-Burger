package com.brasilburger.repositories;
import com.brasilburger.core.interfaces.IRepository;
import com.brasilburger.model.entities.CompositionMenu;
import com.brasilburger.exceptions.DataAccessException;
import com.brasilburger.config.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompositionMenuRepository implements IRepository<CompositionMenu> {

    private Connection conn;

    public CompositionMenuRepository() {
        conn = DatabaseConnection.getConnection();
    }

    @Override
    public void create(CompositionMenu cm) throws DataAccessException {
        String sql = "INSERT INTO composition_menus (menu_id, burger_id, complement_id) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, cm.getMenuId());
            stmt.setInt(2, cm.getBurgerId());
            if (cm.getComplementId() != null) {
                stmt.setInt(3, cm.getComplementId());
            } else {
                stmt.setNull(3, Types.INTEGER);
            }
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) cm.setId(rs.getInt(1));
        } catch (SQLException e) {
            throw new DataAccessException("Erreur lors de la création de la composition du menu.", e);
        }
    }

    @Override
    public void update(CompositionMenu cm) throws DataAccessException {
        String sql = "UPDATE composition_menus SET menu_id=?, burger_id=?, complement_id=? WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cm.getMenuId());
            stmt.setInt(2, cm.getBurgerId());
            if (cm.getComplementId() != null) {
                stmt.setInt(3, cm.getComplementId());
            } else {
                stmt.setNull(3, Types.INTEGER);
            }
            stmt.setInt(4, cm.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Erreur lors de la mise à jour de la composition du menu.", e);
        }
    }

    @Override
    public void delete(int id) throws DataAccessException {
        String sql = "DELETE FROM composition_menus WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Erreur lors de la suppression de la composition du menu.", e);
        }
    }

    @Override
    public CompositionMenu findById(int id) throws DataAccessException {
        String sql = "SELECT * FROM composition_menus WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String type = rs.getObject("complement_id") != null ? "complement" : "burger";
                return new CompositionMenu(
                    rs.getInt("id"),
                    rs.getInt("menu_id"),
                    rs.getInt("burger_id"),
                    type
                );
            }
            return null;
        } catch (SQLException e) {
            throw new DataAccessException("Erreur lors de la récupération de la composition du menu.", e);
        }
    }

    @Override
    public List<CompositionMenu> findAll() throws DataAccessException {
        List<CompositionMenu> list = new ArrayList<>();
        String sql = "SELECT * FROM composition_menus";
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String type = rs.getObject("complement_id") != null ? "complement" : "burger";
                list.add(new CompositionMenu(
                    rs.getInt("id"),
                    rs.getInt("menu_id"),
                    rs.getInt("burger_id"),
                    type
                ));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Erreur lors de la récupération des compositions de menus.", e);
        }
        return list;
    }

    public List<CompositionMenu> findByMenuId(int menuId) throws DataAccessException {
        List<CompositionMenu> list = new ArrayList<>();
        String sql = "SELECT * FROM composition_menus WHERE menu_id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, menuId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String type = rs.getObject("complement_id") != null ? "complement" : "burger";
                list.add(new CompositionMenu(
                    rs.getInt("id"),
                    rs.getInt("menu_id"),
                    rs.getInt("burger_id"),
                    type
                ));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Erreur lors de la récupération des compositions par menu.", e);
        }
        return list;
    }
}
