package brainacad.service;

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

class ClientServiceTest {
    private JdbcTemplate jdbc;
    private ClientService service;

    @BeforeEach
    void setUp() {
        jdbc = Mockito.mock(JdbcTemplate.class);
        service = new ClientService(jdbc);
    }

    @Test
    void testAddClient() {
        Client client = new Client(0, "Test User", LocalDate.of(1990, 1, 1), "123", "mail@test.com", 5.0);
        service.addClient(client);
        verify(jdbc).update(anyString(), any(), any(), any(), any(), any());
    }

    @Test
    void testDeleteClient() {
        service.deleteClient(1);
        verify(jdbc).update("DELETE FROM client WHERE id = ?", 1);
    }
}