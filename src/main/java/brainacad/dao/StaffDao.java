package brainacad.dao;

import brainacad.model.Staff;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StaffDao {

    private final JdbcTemplate jdbc;

    public StaffDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public void create(Staff staff) {
        String sql = "INSERT INTO staff (full_name, phone, email, position) VALUES (?, ?, ?, ?)";
        jdbc.update(sql,
                staff.getFullName(),
                staff.getPhone(),
                staff.getEmail(),
                staff.getPosition());
    }

    public List<Staff> readAll() {
        return jdbc.query("SELECT * FROM staff", staffRowMapper());
    }

    public Staff readById(int id) {
        String sql = "SELECT * FROM staff WHERE id = ?";
        List<Staff> result = jdbc.query(sql, staffRowMapper(), id);
        return result.isEmpty() ? null : result.get(0);
    }

    public void update(Staff staff) {
        String sql = "UPDATE staff SET full_name = ?, phone = ?, email = ?, position = ? WHERE id = ?";
        jdbc.update(sql,
                staff.getFullName(),
                staff.getPhone(),
                staff.getEmail(),
                staff.getPosition(),
                staff.getId());
    }

    public void delete(int id) {
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
