package brainacad.service;

import brainacad.model.Dessert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

import static org.mockito.Mockito.*;

public class DessertServiceTest {

    private JdbcTemplate jdbcTemplate;
    private DessertService dessertService;

    @BeforeEach
    void setUp() {
        jdbcTemplate = mock(JdbcTemplate.class);
        dessertService = new DessertService(jdbcTemplate);
    }

    @Test
    void testAddDessert() {
        Dessert d = new Dessert(0, "Cake", "Торт", 5.99);
        dessertService.addDessert(d);

        verify(jdbcTemplate).update(
                eq("INSERT INTO dessert (name_en, name_local, price) VALUES (?, ?, ?)"),
                eq("Cake"), eq("Торт"), eq(5.99)
        );
    }

    @Test
    void testRenameDessert() {
        dessertService.renameDessert(2, "Pie", "Пирог");
        verify(jdbcTemplate).update(
                eq("UPDATE dessert SET name_en = ?, name_local = ? WHERE id = ?"),
                eq("Pie"), eq("Пирог"), eq(2)
        );
    }

    @Test
    void testDeleteDessert() {
        dessertService.deleteDessert(5);
        verify(jdbcTemplate).update("DELETE FROM dessert WHERE id = ?", 5);
    }
}