package brainacad.dao;

import brainacad.model.OrderItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class OrderItemDaoTest {

    private JdbcTemplate jdbcTemplate;
    private OrderItemDao dao;

    @BeforeEach
    void setUp() {
        jdbcTemplate = Mockito.mock(JdbcTemplate.class);
        dao = new OrderItemDao(jdbcTemplate);
    }

    @Test
    void testCreateOrderItem() {
        OrderItem item = new OrderItem(0, 1, "drink", 2, 1);
        dao.create(item);
        verify(jdbcTemplate).update(anyString(), eq(1), eq("drink"), eq(2), eq(1));
    }

    @Test
    void testReadByOrderId() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(1))).thenReturn(List.of());
        List<OrderItem> result = dao.readByOrderId(1);
        assertNotNull(result);
    }
}