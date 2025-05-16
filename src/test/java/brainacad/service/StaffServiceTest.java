package brainacad.service;

import brainacad.model.Staff;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.mockito.Mockito.*;

public class StaffServiceTest
{

    private JdbcTemplate jdbcTemplate;
    private StaffService staffService;

    @BeforeEach
    void setUp()
    {
        jdbcTemplate = mock(JdbcTemplate.class);
        staffService = new StaffService(jdbcTemplate);
    }

    @Test
    void testAddStaff() {
        Staff s = new Staff(0, "Ivan Grimes", "1234567890", "grimes@example.com", "Бариста");
        staffService.addStaff(s);
        verify(jdbcTemplate).update(
                eq("INSERT INTO staff (full_name, phone, email, position) VALUES (?, ?, ?, ?)"),
                eq("Ivan Grimes"), eq("1234567890"), eq("grimes@example.com"), eq("Бариста")
        );
    }

    @Test
    void testUpdatePhone()
    {
        staffService.updateStaffPhone(3, "0987654321");
        verify(jdbcTemplate).update("UPDATE staff SET phone = ? WHERE id = ?", "0987654321", 3);
    }

    @Test
    void testUpdateEmail()
    {
        staffService.updateStaffEmail(3, "new@example.com");
        verify(jdbcTemplate).update("UPDATE staff SET email = ? WHERE id = ?", "new@example.com", 3);
    }

    @Test
    void testDeleteStaff()
    {
        staffService.deleteStaff(4);
        verify(jdbcTemplate).update("DELETE FROM staff WHERE id = ?", 4);
    }
}
