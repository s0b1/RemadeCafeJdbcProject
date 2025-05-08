package brainacad.service;

import brainacad.model.Schedule;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ScheduleService
{
    private final Connection connection;

    public ScheduleService(Connection connection)
    {
        this.connection = connection;
    }

    public void addSchedule(Schedule schedule) throws SQLException {
        String sql = "INSERT INTO staff_schedule (staff_id, work_day, shift) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, schedule.getStaffId());
            stmt.setDate(2, Date.valueOf(schedule.getWorkDay()));
            stmt.setString(3, schedule.getShift());
            stmt.executeUpdate();
        }
    }

    public void updateScheduleByDate(int staffId, LocalDate date, String shift) throws SQLException {
        String sql = "UPDATE staff_schedule SET shift = ? WHERE staff_id = ? AND work_day = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, shift);
            stmt.setInt(2, staffId);
            stmt.setDate(3, Date.valueOf(date));
            stmt.executeUpdate();
        }
    }

    public void deleteScheduleByDay(LocalDate day) throws SQLException {
        String sql = "DELETE FROM staff_schedule WHERE work_day = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(day));
            stmt.executeUpdate();
        }
    }

    public void deleteScheduleByRange(LocalDate from, LocalDate to) throws SQLException {
        String sql = "DELETE FROM staff_schedule WHERE work_day BETWEEN ? AND ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(from));
            stmt.setDate(2, Date.valueOf(to));
            stmt.executeUpdate();
        }
    }

    public List<Schedule> getScheduleForDay(LocalDate date) throws SQLException {
        String sql = "SELECT * FROM staff_schedule WHERE work_day = ?";
        List<Schedule> result = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(date));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.add(new Schedule(
                        rs.getInt("id"),
                        rs.getInt("staff_id"),
                        rs.getDate("work_day").toLocalDate(),
                        rs.getString("shift")
                ));
            }
        }
        return result;
    }


    public List<Schedule> getAllSchedules() throws SQLException {
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