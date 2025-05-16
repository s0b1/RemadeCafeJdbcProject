package brainacad.service;

import brainacad.model.Order;
import brainacad.model.OrderItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;

class OrderItemServiceTest {

    private JdbcTemplate jdbcTemplate;
    private OrderItemService service;

    @BeforeEach
    void setUp() {
        jdbcTemplate = mock(JdbcTemplate.class);
        service = new OrderItemService(jdbcTemplate);
    }

    @Test
    void testAddOrderItem() {
        OrderItem item = new OrderItem(0, 1, "dessert", 2, 3);

        service.addOrderItem(item);

        verify(jdbcTemplate).update(
                anyString(),
                eq(1), eq("dessert"), eq(2), eq(3)
        );
    }

    @Test
    void testCountDessertOrdersByDate() {
        LocalDate date = LocalDate.now();
        when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class), eq(date)))
                .thenReturn(5);

        int result = service.countDessertOrdersByDate(date);

        assert result == 5;
    }

    @Test
    void testCountDrinkOrdersByDate() {
        LocalDate date = LocalDate.now();
        when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class), eq(date)))
                .thenReturn(7);

        int result = service.countDrinkOrdersByDate(date);

        assert result == 7;
    }
}
