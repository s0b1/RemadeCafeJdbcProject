package brainacad.service;

import brainacad.model.Staff;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffService {
    private final JdbcTemplate jdbc;

    public StaffService(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public void addStaff(Staff staff) {
        String sql = "INSERT INTO staff (full_name, phone, email, position) VALUES (?, ?, ?, ?)";
        jdbc.update(sql,
                staff.getFullName(),
                staff.getPhone(),
                staff.getEmail(),
                staff.getPosition());
    }

    public List<Staff> getAllStaff() {
        return jdbc.query("SELECT * FROM staff", staffRowMapper());
    }

    public void updateStaffPhone(int id, String newPhone) {
        String sql = "UPDATE staff SET phone = ? WHERE id = ?";
        jdbc.update(sql, newPhone, id);
    }

    public void updateStaffEmail(int id, String newEmail) {
        String sql = "UPDATE staff SET email = ? WHERE id = ?";
        jdbc.update(sql, newEmail, id);
    }

    public void deleteStaff(int id) {
        jdbc.update("DELETE FROM staff WHERE id = ?", id);
    }

    private RowMapper<Staff> staffRowMapper() {
        return (rs, rowNum) -> new Staff(
                rs.getInt("id"),
                rs.getString("full_name"),
                rs.getString("phone"),
                rs.getString("email"),
                rs.getString("position")
        );
    }
}
