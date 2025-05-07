package brainacad.dao;

import brainacad.model.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDao {
    private final Connection connection;

    public ClientDao(Connection connection) {
        this.connection = connection;
    }

    public void create(Client client) throws SQLException
    {
        String sql = "INSERT INTO client (full_name, birth_date, phone, email, discount) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, client.getFullName());
            stmt.setDate(2, Date.valueOf(client.getBirthDate()));
            stmt.setString(3, client.getPhone());
            stmt.setString(4, client.getEmail());
            stmt.setDouble(5, client.getDiscount());
            stmt.executeUpdate();
        }
    }

    public List<Client> readAll() throws SQLException {
        List<Client> list = new ArrayList<>();
        String sql = "SELECT * FROM client";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql))
        {
            while (rs.next()) {
                list.add(new Client(
                        rs.getInt("id"),
                        rs.getString("full_name"),
                        rs.getDate("birth_date").toLocalDate(),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getDouble("discount")
                ));
            }
        }
        return list;
    }

    public Client readById(int id) throws SQLException {
        String sql = "SELECT * FROM client WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Client(
                        rs.getInt("id"),
                        rs.getString("full_name"),
                        rs.getDate("birth_date").toLocalDate(),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getDouble("discount")
                );
            }
        }
        return null;
    }

    public void update(Client client) throws SQLException {
        String sql = "UPDATE client SET full_name = ?, birth_date = ?, phone = ?, email = ?, discount = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, client.getFullName());
            stmt.setDate(2, Date.valueOf(client.getBirthDate()));
            stmt.setString(3, client.getPhone());
            stmt.setString(4, client.getEmail());
            stmt.setDouble(5, client.getDiscount());
            stmt.setInt(6, client.getId());
            stmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM client WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}