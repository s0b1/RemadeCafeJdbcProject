package brainacad.service;

import brainacad.model.OrderItem;

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