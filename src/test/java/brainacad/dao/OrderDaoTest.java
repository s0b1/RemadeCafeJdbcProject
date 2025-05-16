package brainacad.dao;

import brainacad.model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class OrderDaoTest {

    private JdbcTemplate jdbcTemplate;
    private OrderDao orderDao;

    @BeforeEach
    void setUp() {
        jdbcTemplate = mock(JdbcTemplate.class);
        orderDao = new OrderDao(jdbcTemplate);
    }

    @Test
    void testCreateOrderWithClientId() {
        Order order = new Order(0, 5, LocalDateTime.now());

        orderDao.create(order);

        verify(jdbcTemplate).update(
                eq("INSERT INTO orders (client_id, order_date) VALUES (?, ?)"),
                eq(5),
                eq(Timestamp.valueOf(order.getOrderDate()))
        );
    }

    @Test
    void testCreateOrderWithoutClientId() {
        Order order = new Order(0, null, LocalDateTime.now());

        orderDao.create(order);

        verify(jdbcTemplate).update(
                eq("INSERT INTO orders (client_id, order_date) VALUES (?, ?)"),
                isNull(),
                eq(Timestamp.valueOf(order.getOrderDate()))
        );
    }
}
