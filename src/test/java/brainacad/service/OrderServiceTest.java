package brainacad.service;

import brainacad.model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {
    private JdbcTemplate jdbc;
    private OrderService service;

    @BeforeEach
    void setUp() {
        jdbc = Mockito.mock(JdbcTemplate.class);
        service = new OrderService(jdbc);
    }

    @Test
    void testAddOrder() {
        service.addOrder(new Order(0, 2, LocalDateTime.now()));
        verify(jdbc).update(anyString(), eq(2), any(LocalDateTime.class));
    }

    @Test
    void testDeleteOrder() {
        service.deleteOrder(10);
        verify(jdbc).update("DELETE FROM orders WHERE id = ?", 10);
    }
}