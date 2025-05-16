package brainacad.dao;

import brainacad.model.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class ClientDaoTest {

    private JdbcTemplate jdbcTemplate;
    private ClientDao dao;

    @BeforeEach
    void setUp() {
        jdbcTemplate = Mockito.mock(JdbcTemplate.class);
        dao = new ClientDao(jdbcTemplate);
    }

    @Test
    void testCreateClient() {
        Client client = new Client(0, "Test Name", LocalDate.of(2000, 1, 1), "123456", "test@mail.com", 10.0);
        dao.create(client);
        verify(jdbcTemplate).update(anyString(), eq("Test Name"), eq(java.sql.Date.valueOf("2000-01-01")),
                eq("123456"), eq("test@mail.com"), eq(10.0));
    }

    @Test
    void testReadAllClients() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(List.of());
        List<Client> result = dao.readAll();
        assertNotNull(result);
    }

    @Test
    void testReadClientById() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), anyInt())).thenReturn(List.of(
                new Client(1, "Name", LocalDate.now(), "123", "a@a.com", 0.0)
        ));
        Client result = dao.readById(1);
        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    void testUpdateClient() {
        Client client = new Client(1, "Updated", LocalDate.of(2000, 1, 1), "000", "u@u.com", 15.0);
        dao.update(client);
        verify(jdbcTemplate).update(anyString(), eq("Updated"), eq(java.sql.Date.valueOf("2000-01-01")),
                eq("000"), eq("u@u.com"), eq(15.0), eq(1));
    }

    @Test
    void testDeleteClient() {
        dao.delete(1);
        verify(jdbcTemplate).update(anyString(), eq(1));
    }
}
