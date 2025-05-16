package brainacad.dao;

import brainacad.model.OrderItem;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderItemDao {

    private final JdbcTemplate jdbc;

    public OrderItemDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public void create(OrderItem item) {
        String sql = "INSERT INTO order_items (order_id, item_type, item_id, quantity) VALUES (?, ?, ?, ?)";
        jdbc.update(sql,
                item.getOrderId(),
                item.getItemType(),
                item.getItemId(),
                item.getQuantity());
    }

    public List<OrderItem> readByOrderId(int orderId) {
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
}
