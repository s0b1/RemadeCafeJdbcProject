package brainacad.dao;

import brainacad.model.Staff;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class StaffDaoTest {

    private JdbcTemplate jdbcTemplate;
    private StaffDao dao;

    @BeforeEach
    void setUp() {
        jdbcTemplate = Mockito.mock(JdbcTemplate.class);
        dao = new StaffDao(jdbcTemplate);
    }

    @Test
    void testCreateStaff() {
        Staff s = new Staff(0, "John Doe", "123", "john@a.com", "Бариста");
        dao.create(s);
        verify(jdbcTemplate).update(anyString(), eq("John Doe"), eq("123"), eq("john@a.com"), eq("Бариста"));
    }

    @Test
    void testReadAllStaff() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(List.of());
        List<Staff> result = dao.readAll();
        assertNotNull(result);
    }

    @Test
    void testReadById() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(1))).thenReturn(List.of(
                new Staff(1, "John", "000", "a@a.com", "Официант")
        ));
        Staff result = dao.readById(1);
        assertNotNull(result);
        assertEquals("Официант", result.getPosition());
    }

    @Test
    void testUpdateStaff() {
        Staff s = new Staff(1, "Jane", "456", "j@j.com", "Кондитер");
        dao.update(s);
        verify(jdbcTemplate).update(anyString(), eq("Jane"), eq("456"), eq("j@j.com"), eq("Кондитер"), eq(1));
    }

    @Test
    void testDeleteStaff() {
        dao.delete(2);
        verify(jdbcTemplate).update(anyString(), eq(2));
    }
}
