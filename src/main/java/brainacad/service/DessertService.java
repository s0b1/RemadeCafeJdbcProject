package brainacad.service;

import brainacad.model.Dessert;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DessertService {
    private final Connection connection;

    public DessertService(Connection connection) {
        this.connection = connection;
    }

    public void addDessert(Dessert dessert) throws SQLException {
        String sql = "INSERT INTO dessert (name_en, name_local, price) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, dessert.getNameEn());
            stmt.setString(2, dessert.getNameLocal());
            stmt.setDouble(3, dessert.getPrice());
            stmt.executeUpdate();
        }
    }

    public List<Dessert> getAllDesserts() throws SQLException {
        List<Dessert> desserts = new ArrayList<>();
        String sql = "SELECT * FROM dessert";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql))
        {
            while (rs.next()) {
                desserts.add(new Dessert(
                        rs.getInt("id"),
                        rs.getString("name_en"),
                        rs.getString("name_local"),
                        rs.getDouble("price")
                ));
            }
        }
        return desserts;
    }

    public Dessert getDessertById(int id) throws SQLException
    {
        String sql = "SELECT * FROM dessert WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql))
        {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
            {
                return new Dessert(
                        rs.getInt("id"),
                        rs.getString("name_en"),
                        rs.getString("name_local"),
                        rs.getDouble("price")
                );
            }
        }
        return null;
    }

    public void updateDessert(Dessert dessert) throws SQLException
    {
        String sql = "UPDATE dessert SET name_en = ?, name_local = ?, price = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql))
        {
            stmt.setString(1, dessert.getNameEn());
            stmt.setString(2, dessert.getNameLocal());
            stmt.setDouble(3, dessert.getPrice());
            stmt.setInt(4, dessert.getId());
            stmt.executeUpdate();
        }
    }

    public void deleteDessert(int id) throws SQLException
    {
        String sql = "DELETE FROM dessert WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql))
        {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}