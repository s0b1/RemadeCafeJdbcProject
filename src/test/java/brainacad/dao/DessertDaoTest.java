package brainacad.dao;

import brainacad.model.Dessert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class DessertDaoTest {

    private JdbcTemplate jdbcTemplate;
    private DessertDao dao;

    @BeforeEach
    void setUp() {
        jdbcTemplate = Mockito.mock(JdbcTemplate.class);
        dao = new DessertDao(jdbcTemplate);
    }

    @Test
    void testCreateDessert() {
        Dessert d = new Dessert(0, "Cake", "Торт", 5.5);
        dao.create(d);
        verify(jdbcTemplate).update(anyString(), eq("Cake"), eq("Торт"), eq(5.5));
    }

    @Test
    void testReadAllDesserts() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(List.of());
        List<Dessert> list = dao.readAll();
        assertNotNull(list);
    }

    @Test
    void testReadById() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(1))).thenReturn(List.of(
                new Dessert(1, "Eclair", "Эклер", 3.0)
        ));
        Dessert d = dao.readById(1);
        assertNotNull(d);
        assertEquals("Eclair", d.getNameEn());
    }

    @Test
    void testUpdateDessert() {
        Dessert d = new Dessert(1, "Updated", "Обновлённый", 6.0);
        dao.update(d);
        verify(jdbcTemplate).update(anyString(), eq("Updated"), eq("Обновлённый"), eq(6.0), eq(1));
    }

    @Test
    void testDeleteDessert() {
        dao.delete(2);
        verify(jdbcTemplate).update(anyString(), eq(2));
    }
}
