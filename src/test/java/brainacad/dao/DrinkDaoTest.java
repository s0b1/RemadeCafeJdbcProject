package brainacad.dao;

import brainacad.model.Drink;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class DrinkDaoTest {

    private JdbcTemplate jdbcTemplate;
    private DrinkDao dao;

    @BeforeEach
    void setUp() {
        jdbcTemplate = Mockito.mock(JdbcTemplate.class);
        dao = new DrinkDao(jdbcTemplate);
    }

    @Test
    void testCreateDrink() {
        Drink d = new Drink(0, "Latte", "Латте", 4.5);
        dao.create(d);
        verify(jdbcTemplate).update(anyString(), eq("Latte"), eq("Латте"), eq(4.5));
    }

    @Test
    void testReadAllDrinks() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(List.of());
        List<Drink> list = dao.readAll();
        assertNotNull(list);
    }

    @Test
    void testReadById() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(1))).thenReturn(List.of(
                new Drink(1, "Cappuccino", "Капучино", 4.0)
        ));
        Drink d = dao.readById(1);
        assertNotNull(d);
        assertEquals("Cappuccino", d.getNameEn());
    }

    @Test
    void testUpdateDrink() {
        Drink d = new Drink(1, "Updated", "Обновлённый", 5.0);
        dao.update(d);
        verify(jdbcTemplate).update(anyString(), eq("Updated"), eq("Обновлённый"), eq(5.0), eq(1));
    }

    @Test
    void testDeleteDrink() {
        dao.delete(2);
        verify(jdbcTemplate).update(anyString(), eq(2));
    }
}
