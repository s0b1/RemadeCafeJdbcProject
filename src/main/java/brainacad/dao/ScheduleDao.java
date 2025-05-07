package brainacad.dao;

import brainacad.model.Schedule;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScheduleDao {
    private final Connection connection;

    public ScheduleDao(Connection connection) {
        this.connection = connection;
    }

    public void create(Schedule schedule) throws SQLException {
        String sql = "INSERT INTO staff_schedule (staff_id, work_day, shift) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, schedule.getStaffId());
            stmt.setDate(2, Date.valueOf(schedule.getWorkDay()));
            stmt.setString(3, schedule.getShift());
            stmt.executeUpdate();
        }
    }

    public List<Schedule> readAll() throws SQLException {
        List<Schedule> list = new ArrayList<>();
        String sql = "SELECT * FROM staff_schedule";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Schedule(
                        rs.getInt("id"),
                        rs.getInt("staff_id"),
                        rs.getDate("work_day").toLocalDate(),
                        rs.getString("shift")
                ));
            }
        }
        return list;
    }
}