package brainacad.service;

import brainacad.model.Drink;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class DrinkServiceTest {
    private JdbcTemplate jdbc;
    private DrinkService service;

    @BeforeEach
    void setUp() {
        jdbc = Mockito.mock(JdbcTemplate.class);
        service = new DrinkService(jdbc);
    }

    @Test
    void testAddDrink() {
        service.addDrink(new Drink(0, "Cola", "Кола", 15.0));
        verify(jdbc).update(anyString(), eq("Cola"), eq("Кола"), eq(15.0));
    }

    @Test
    void testRenameDrink() {
        service.renameDrink(1, "Pepsi", "Пепси");
        verify(jdbc).update(anyString(), eq("Pepsi"), eq("Пепси"), eq(1));
    }

    @Test
    void testDeleteDrink() {
        service.deleteDrink(1);
        verify(jdbc).update("DELETE FROM drink WHERE id = ?", 1);
    }
}