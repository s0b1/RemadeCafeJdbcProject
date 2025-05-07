package brainacad.dao;

import brainacad.model.Dessert;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DessertDao {
    private final Connection connection;

    public DessertDao(Connection connection) {
        this.connection = connection;
    }

    public void create(Dessert dessert) throws SQLException
    {
        String sql = "INSERT INTO dessert (name_en, name_local, price) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql))
        {
            stmt.setString(1, dessert.getNameEn());
            stmt.setString(2, dessert.getNameLocal());
            stmt.setDouble(3, dessert.getPrice());
            stmt.executeUpdate();
        }
    }

    public List<Dessert> readAll() throws SQLException
    {
        List<Dessert> list = new ArrayList<>();
        String sql = "SELECT * FROM dessert";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql))
        {
            while (rs.next())
            {
                list.add(new Dessert(
                        rs.getInt("id"),
                        rs.getString("name_en"),
                        rs.getString("name_local"),
                        rs.getDouble("price")
                ));
            }
        }
        return list;
    }

    public Dessert readById(int id) throws SQLException
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

    public void update(Dessert dessert) throws SQLException
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

    public void delete(int id) throws SQLException
    {
        String sql = "DELETE FROM dessert WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql))
        {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}