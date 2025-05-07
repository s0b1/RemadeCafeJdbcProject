package brainacad.dao;

import brainacad.model.Drink;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DrinkDao
{
    private final Connection connection;

    public DrinkDao(Connection connection)
    {
        this.connection = connection;
    }

    public void create(Drink drink) throws SQLException
    {
        String sql = "INSERT INTO drink (name_en, name_local, price) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql))
        {
            stmt.setString(1, drink.getNameEn());
            stmt.setString(2, drink.getNameLocal());
            stmt.setDouble(3, drink.getPrice());
            stmt.executeUpdate();
        }
    }

    public List<Drink> readAll() throws SQLException
    {
        List<Drink> drinks = new ArrayList<>();
        String sql = "SELECT * FROM drink";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql))
        {
            while (rs.next())
            {
                drinks.add(new Drink(
                        rs.getInt("id"),
                        rs.getString("name_en"),
                        rs.getString("name_local"),
                        rs.getDouble("price")
                ));
            }
        }
        return drinks;
    }

    public Drink readById(int id) throws SQLException
    {
        String sql = "SELECT * FROM drink WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql))
        {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Drink(
                        rs.getInt("id"),
                        rs.getString("name_en"),
                        rs.getString("name_local"),
                        rs.getDouble("price")
                );
            }
        }
        return null;
    }

    public void update(Drink drink) throws SQLException
    {
        String sql = "UPDATE drink SET name_en = ?, name_local = ?, price = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql))
        {
            stmt.setString(1, drink.getNameEn());
            stmt.setString(2, drink.getNameLocal());
            stmt.setDouble(3, drink.getPrice());
            stmt.setInt(4, drink.getId());
            stmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException
    {
        String sql = "DELETE FROM drink WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql))
        {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}