package brainacad.service;

import brainacad.model.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class ScheduleService {
    private final JdbcTemplate jdbc;

    public ScheduleService(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public void addSchedule(Schedule schedule) {
        String sql = "INSERT INTO staff_schedule (staff_id, work_day, shift) VALUES (?, ?, ?)";
        jdbc.update(sql,
                schedule.getStaffId(),
                Date.valueOf(schedule.getWorkDay()),
                schedule.getShift());
    }

    public void updateScheduleByDate(int staffId, LocalDate date, String shift) {
        String sql = "UPDATE staff_schedule SET shift = ? WHERE staff_id = ? AND work_day = ?";
        jdbc.update(sql, shift, staffId, Date.valueOf(date));
    }

    public void deleteScheduleByDay(LocalDate day) {
        jdbc.update("DELETE FROM staff_schedule WHERE work_day = ?", Date.valueOf(day));
    }

    public void deleteScheduleByRange(LocalDate from, LocalDate to) {
        jdbc.update("DELETE FROM staff_schedule WHERE work_day BETWEEN ? AND ?",
                Date.valueOf(from), Date.valueOf(to));
    }

    public List<Schedule> getScheduleForDay(LocalDate date) {
        String sql = "SELECT * FROM staff_schedule WHERE work_day = ?";
        return jdbc.query(sql, scheduleRowMapper(), Date.valueOf(date));
    }

    public List<Schedule> getAllSchedules() {
        return jdbc.query("SELECT * FROM staff_schedule", scheduleRowMapper());
    }

    public List<Schedule> getWeekScheduleForBarista(int staffId, LocalDate from) {
        String sql = """
            SELECT ss.* FROM staff_schedule ss
            JOIN staff s ON ss.staff_id = s.id
            WHERE s.position = 'Бариста'
              AND s.id = ?
              AND ss.work_day BETWEEN ? AND ?
        """;
        LocalDate to = from.plusDays(6);
        return jdbc.query(sql, scheduleRowMapper(), staffId, Date.valueOf(from), Date.valueOf(to));
    }

    public List<Schedule> getWeekScheduleForAllBaristas(LocalDate from) {
        String sql = """
            SELECT ss.* FROM staff_schedule ss
            JOIN staff s ON ss.staff_id = s.id
            WHERE s.position = 'Бариста'
              AND ss.work_day BETWEEN ? AND ?
        """;
        LocalDate to = from.plusDays(6);
        return jdbc.query(sql, scheduleRowMapper(), Date.valueOf(from), Date.valueOf(to));
    }

    public List<Schedule> getWeekScheduleForAllStaff(LocalDate from)
    {
        String sql = """
            SELECT * FROM staff_schedule
            WHERE work_day BETWEEN ? AND ?
        """;
        LocalDate to = from.plusDays(6);
        return jdbc.query(sql, scheduleRowMapper(), Date.valueOf(from), Date.valueOf(to));
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
