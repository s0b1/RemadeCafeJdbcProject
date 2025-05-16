package brainacad.service;

import brainacad.model.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {
    private final JdbcTemplate jdbc;

    public OrderService(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public void addOrder(Order order) {
        String sql = "INSERT INTO orders (client_id, order_date) VALUES (?, ?)";
        jdbc.update(sql, order.getClientId(), order.getOrderDate());
    }

    public List<Order> getAllOrders() {
        return jdbc.query("SELECT * FROM orders", orderRowMapper());
    }

    public List<Order> getOrdersByClient(int clientId) {
        return jdbc.query("SELECT * FROM orders WHERE client_id = ?", orderRowMapper(), clientId);
    }

    public void deleteOrder(int id) {
        jdbc.update("DELETE FROM orders WHERE id = ?", id);
    }

    public void updateOrderClient(int orderId, Integer clientId) {
        jdbc.update("UPDATE orders SET client_id = ? WHERE id = ?", clientId, orderId);
    }

    // zadanie 3

    public List<Order> getOrdersByDate(LocalDate date) {
        String sql = """
            SELECT * FROM orders
            WHERE DATE(order_date) = ?
        """;
        return jdbc.query(sql, orderRowMapper(), date);
    }

    public List<Order> getOrdersInRange(LocalDate start, LocalDate end) {
        String sql = """
            SELECT * FROM orders
            WHERE DATE(order_date) BETWEEN ? AND ?
        """;
        return jdbc.query(sql, orderRowMapper(), start, end);
    }

    private RowMapper<Order> orderRowMapper() {
        return (rs, rowNum) -> new Order(
                rs.getInt("id"),
                rs.getObject("client_id", Integer.class),
                rs.getTimestamp("order_date").toLocalDateTime()
        );
    }

    public Double getAverageOrderTotalOnDate(LocalDate date) {
        String sql = """
            SELECT AVG(total) FROM (
                SELECT SUM(CASE
                    WHEN oi.item_type = 'drink' THEN d.price * oi.quantity
                    WHEN oi.item_type = 'dessert' THEN ds.price * oi.quantity
                END) AS total
                FROM orders o
                JOIN order_items oi ON o.id = oi.order_id
                LEFT JOIN drink d ON oi.item_id = d.id AND oi.item_type = 'drink'
                LEFT JOIN dessert ds ON oi.item_id = ds.id AND oi.item_type = 'dessert'
                WHERE DATE(o.order_date) = ?
                GROUP BY o.id
            ) AS order_totals
        """;
        return jdbc.queryForObject(sql, Double.class, date);
    }

    public Double getMaxOrderTotalOnDate(LocalDate date) {
        String sql = """
            SELECT MAX(total) FROM (
                SELECT SUM(CASE
                    WHEN oi.item_type = 'drink' THEN d.price * oi.quantity
                    WHEN oi.item_type = 'dessert' THEN ds.price * oi.quantity
                END) AS total
                FROM orders o
                JOIN order_items oi ON o.id = oi.order_id
                LEFT JOIN drink d ON oi.item_id = d.id AND oi.item_type = 'drink'
                LEFT JOIN dessert ds ON oi.item_id = ds.id AND oi.item_type = 'dessert'
                WHERE DATE(o.order_date) = ?
                GROUP BY o.id
            ) AS order_totals
        """;
        return jdbc.queryForObject(sql, Double.class, date);
    }

    public String getClientWithMaxOrderOnDate(LocalDate date) {
        String sql = """
            SELECT c.full_name
            FROM client c
            JOIN orders o ON c.id = o.client_id
            JOIN order_items oi ON o.id = oi.order_id
            LEFT JOIN drink d ON oi.item_type = 'drink' AND d.id = oi.item_id
            LEFT JOIN dessert ds ON oi.item_type = 'dessert' AND ds.id = oi.item_id
            WHERE DATE(o.order_date) = ?
            GROUP BY c.full_name, o.id
            ORDER BY SUM(CASE
                WHEN oi.item_type = 'drink' THEN d.price * oi.quantity
                WHEN oi.item_type = 'dessert' THEN ds.price * oi.quantity
            END) DESC
            LIMIT 1
        """;
        return jdbc.queryForObject(sql, String.class, date);
    }

}
