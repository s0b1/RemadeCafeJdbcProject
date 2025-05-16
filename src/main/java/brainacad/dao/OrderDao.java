package brainacad.dao;

import brainacad.model.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class OrderDao {

    private final JdbcTemplate jdbc;

    public OrderDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public void create(Order order) {
        String sql = "INSERT INTO orders (client_id, order_date) VALUES (?, ?)";
        jdbc.update(sql,
                order.getClientId(),
                Timestamp.valueOf(order.getOrderDate()));
    }

    public List<Order> readAll() {
        String sql = "SELECT * FROM orders";
        return jdbc.query(sql, orderRowMapper());
    }

    private RowMapper<Order> orderRowMapper() {
        return (rs, rowNum) -> new Order(
                rs.getInt("id"),
                rs.getObject("client_id", Integer.class),
                rs.getTimestamp("order_date").toLocalDateTime()
        );
    }
}
