package brainacad.dao;

import brainacad.model.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDao {
    private final Connection connection;

    public OrderDao(Connection connection) {
        this.connection = connection;
    }

    public void create(Order order) throws SQLException {
        String sql = "INSERT INTO orders (client_id, order_date) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            if (order.getClientId() != null) {
                stmt.setInt(1, order.getClientId());
            } else {
                stmt.setNull(1, Types.INTEGER);
            }
            stmt.setTimestamp(2, Timestamp.valueOf(order.getOrderDate()));
            stmt.executeUpdate();
        }
    }

    public List<Order> readAll() throws SQLException {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM orders";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
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