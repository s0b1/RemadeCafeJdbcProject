package brainacad.service;

import brainacad.model.OrderItem;
import brainacad.model.Order;
import brainacad.service.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderItemService
{
    private final Connection connection;

    public OrderItemService(Connection connection)
    {
        this.connection = connection;
    }

    public void addOrderItem(OrderItem item) throws SQLException
    {
        String sql = "INSERT INTO order_items (order_id, item_type, item_id, quantity) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql))
        {
            stmt.setInt(1, item.getOrderId());
            stmt.setString(2, item.getItemType());
            stmt.setInt(3, item.getItemId());
            stmt.setInt(4, item.getQuantity());
            stmt.executeUpdate();
        }
    }

    public void deleteOrdersByDessert(int dessertId) throws SQLException {
        String findOrdersSql = """
        SELECT DISTINCT order_id
        FROM order_items
        WHERE item_type = 'dessert' AND item_id = ?
    """;

        try (PreparedStatement findStmt = connection.prepareStatement(findOrdersSql)) {
            findStmt.setInt(1, dessertId);
            ResultSet rs = findStmt.executeQuery();

            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                String deleteOrder = "DELETE FROM orders WHERE id = ?";
                try (PreparedStatement deleteStmt = connection.prepareStatement(deleteOrder)) {
                    deleteStmt.setInt(1, orderId);
                    deleteStmt.executeUpdate();
                }
            }
        }
    }


    public List<Order> findOrdersByDessert(int dessertId) throws SQLException {
        String sql = """
        SELECT DISTINCT o.* FROM orders o
        JOIN order_items oi ON o.id = oi.order_id
        WHERE oi.item_type = 'dessert' AND oi.item_id = ?
    """;
        List<Order> result = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, dessertId);
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

    public List<Order> findOrdersByWaiter(int waiterId) throws SQLException {
        String sql = """
        SELECT DISTINCT o.* FROM orders o
        JOIN order_items oi ON o.id = oi.order_id
        WHERE EXISTS (
            SELECT 1 FROM staff s WHERE s.id = ? AND s.position = 'Официант'
        )
    """;
        List<Order> result = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, waiterId);
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



    public List<OrderItem> getItemsByOrderId(int orderId) throws SQLException
    {
        List<OrderItem> items = new ArrayList<>();
        String sql = "SELECT * FROM order_items WHERE order_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql))
        {
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                items.add(new OrderItem(
                        rs.getInt("id"),
                        rs.getInt("order_id"),
                        rs.getString("item_type"),
                        rs.getInt("item_id"),
                        rs.getInt("quantity")
                ));
            }
        }
        return items;
    }
}