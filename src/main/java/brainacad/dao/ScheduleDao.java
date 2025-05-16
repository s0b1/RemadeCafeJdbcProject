package brainacad.dao;

import brainacad.model.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public class ScheduleDao {

    private final JdbcTemplate jdbc;

    public ScheduleDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public void create(Schedule schedule) {
        String sql = "INSERT INTO staff_schedule (staff_id, work_day, shift) VALUES (?, ?, ?)";
        jdbc.update(sql,
                schedule.getStaffId(),
                Date.valueOf(schedule.getWorkDay()),
                schedule.getShift());
    }

    public List<Schedule> readAll() {
        String sql = "SELECT * FROM staff_schedule";
        return jdbc.query(sql, scheduleRowMapper());
    }

    private RowMapper<Schedule> scheduleRowMapper() {
        return (rs, rowNum) -> new Schedule(
                rs.getInt("id"),
                rs.getInt("staff_id"),
                rs.getDate("work_day").toLocalDate(),
                rs.getString("shift")
        );
    }
}
