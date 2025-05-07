package brainacad.service;

import brainacad.model.Client;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClientService
{
    private final Connection connection;

    public ClientService(Connection connection)
    {
        this.connection = connection;
    }

    public void addClient(Client client) throws SQLException
    {
        String sql = "INSERT INTO client (full_name, birth_date, phone, email, discount) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql))
        {
            stmt.setString(1, client.getFullName());
            stmt.setDate(2, Date.valueOf(client.getBirthDate()));
            stmt.setString(3, client.getPhone());
            stmt.setString(4, client.getEmail());
            stmt.setDouble(5, client.getDiscount());
            stmt.executeUpdate();
        }
    }

    public List<Client> getAllClients() throws SQLException
    {
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

    public void updateClientDiscount(int id, double discount) throws SQLException
    {
        String sql = "UPDATE client SET discount = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql))
        {
            stmt.setDouble(1, discount);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        }
    }

    public void deleteClient(int id) throws SQLException
    {
        String sql = "DELETE FROM client WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql))
        {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}