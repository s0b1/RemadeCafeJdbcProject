package brainacad.service;

import brainacad.model.Order;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderService
{
    private final Connection connection;

    public OrderService(Connection connection)
    {
        this.connection = connection;
    }

    public void addOrder(Order order) throws SQLException
    {
        String sql = "INSERT INTO orders (client_id, order_date) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql))
        {
            if (order.getClientId() != null)
            {
                stmt.setInt(1, order.getClientId());
            }
            else
            {
                stmt.setNull(1, Types.INTEGER);
            }
            stmt.setTimestamp(2, Timestamp.valueOf(order.getOrderDate()));
            stmt.executeUpdate();
        }
    }

    public void updateOrderClient(int orderId, Integer clientId) throws SQLException {
        String sql = "UPDATE orders SET client_id = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            if (clientId != null) {
                stmt.setInt(1, clientId);
            } else {
                stmt.setNull(1, Types.INTEGER);
            }
            stmt.setInt(2, orderId);
            stmt.executeUpdate();
        }
    }

    public void deleteOrder(int id) throws SQLException {
        String sql = "DELETE FROM orders WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public List<Order> getOrdersByClient(int clientId) throws SQLException {
        String sql = "SELECT * FROM orders WHERE client_id = ?";
        List<Order> result = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, clientId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.add(new Order(
                        rs.getInt("id"),
                        rs.getObject("client_id", Integer.class),
                        rs.getTimestamp("order_date").toLocalDateTime()
                ));
            }
        }
        return result;
    }


    public List<Order> getAllOrders() throws SQLException
    {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM orders";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql))
        {
            while (rs.next()) {
                list.add(new Order(
                        rs.getInt("id"),
                        rs.getObject("client_id", Integer.class),
                        rs.getTimestamp("order_date").toLocalDateTime()
                ));
            }
        }
        return list;
    }
}