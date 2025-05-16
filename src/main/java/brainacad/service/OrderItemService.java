package brainacad.service;

import brainacad.model.Order;
import brainacad.model.OrderItem;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.time.LocalDate;

@Service
public class OrderItemService {
    private final JdbcTemplate jdbc;

    public OrderItemService(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public void addOrderItem(OrderItem item) {
        String sql = "INSERT INTO order_items (order_id, item_type, item_id, quantity) VALUES (?, ?, ?, ?)";
        jdbc.update(sql, item.getOrderId(), item.getItemType(), item.getItemId(), item.getQuantity());
    }

    public void deleteOrdersByDessert(int dessertId) {
        String findOrdersSql = """
            SELECT DISTINCT order_id FROM order_items
            WHERE item_type = 'dessert' AND item_id = ?
        """;

        List<Integer> orderIds = jdbc.query(findOrdersSql, (rs, rowNum) -> rs.getInt("order_id"), dessertId);
        for (int orderId : orderIds) {
            jdbc.update("DELETE FROM orders WHERE id = ?", orderId);
        }
    }

    public List<Order> findOrdersByDessert(int dessertId) {
        String sql = """
            SELECT DISTINCT o.* FROM orders o
            JOIN order_items oi ON o.id = oi.order_id
            WHERE oi.item_type = 'dessert' AND oi.item_id = ?
        """;
        return jdbc.query(sql, orderRowMapper(), dessertId);
    }

    public List<Order> findOrdersByWaiter(int waiterId) {
        String sql = """
            SELECT DISTINCT o.* FROM orders o
            JOIN order_items oi ON o.id = oi.order_id
            WHERE EXISTS (
                SELECT 1 FROM staff s WHERE s.id = ? AND s.position = 'Официант'
            )
        """;
        return jdbc.query(sql, orderRowMapper(), waiterId);
    }

    public List<OrderItem> getItemsByOrderId(int orderId) {
        String sql = "SELECT * FROM order_items WHERE order_id = ?";
        return jdbc.query(sql, orderItemRowMapper(), orderId);
    }

    private RowMapper<OrderItem> orderItemRowMapper() {
        return (rs, rowNum) -> new OrderItem(
                rs.getInt("id"),
                rs.getInt("order_id"),
                rs.getString("item_type"),
                rs.getInt("item_id"),
                rs.getInt("quantity")
        );
    }

    private RowMapper<Order> orderRowMapper() {
        return (rs, rowNum) -> new Order(
                rs.getInt("id"),
                rs.getObject("client_id", Integer.class),
                rs.getTimestamp("order_date").toLocalDateTime()
        );
    }

    // zadanie 3

    public int countDessertOrdersByDate(LocalDate date) {
        String sql = """
            SELECT COUNT(*) FROM order_items oi
            JOIN orders o ON oi.order_id = o.id
            WHERE oi.item_type = 'dessert' AND DATE(o.order_date) = ?
        """;
        return jdbc.queryForObject(sql, Integer.class, date);
    }

    public int countDrinkOrdersByDate(LocalDate date) {
        String sql = """
            SELECT COUNT(*) FROM order_items oi
            JOIN orders o ON oi.order_id = o.id
            WHERE oi.item_type = 'drink' AND DATE(o.order_date) = ?
        """;
        return jdbc.queryForObject(sql, Integer.class, date);
    }

    public List<ClientBaristaDTO> getClientsAndBaristasForTodayDrinks()
    {
        String sql = """
            SELECT c.full_name AS client_name, c.email AS client_email,
                   s.full_name AS barista_name, s.email AS barista_email
            FROM orders o
            JOIN client c ON o.client_id = c.id
            JOIN order_items oi ON o.id = oi.order_id
            JOIN staff s ON s.position = 'Бариста'
            WHERE oi.item_type = 'drink'
              AND DATE(o.order_date) = CURRENT_DATE
        """;

        return jdbc.query(sql, clientBaristaMapper());
    }

    private RowMapper<ClientBaristaDTO> clientBaristaMapper()
    {
        return (rs, rowNum) -> new ClientBaristaDTO(
                rs.getString("client_name"),
                rs.getString("client_email"),
                rs.getString("barista_name"),
                rs.getString("barista_email")
        );
    }

    public static class ClientBaristaDTO
    {
        private final String clientName;
        private final String clientEmail;
        private final String baristaName;
        private final String baristaEmail;

        public ClientBaristaDTO(String clientName, String clientEmail, String baristaName, String baristaEmail)
        {
            this.clientName = clientName;
            this.clientEmail = clientEmail;
            this.baristaName = baristaName;
            this.baristaEmail = baristaEmail;
        }

        @Override
        public String toString()
        {
            return "Client: %s (%s), Barista: %s (%s)".formatted(clientName, clientEmail, baristaName, baristaEmail);
        }
    }

}
