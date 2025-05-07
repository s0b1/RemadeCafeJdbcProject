package brainacad.service;

import brainacad.model.Staff;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StaffService {
    private final Connection connection;

    public StaffService(Connection connection) {
        this.connection = connection;
    }

    public void addStaff(Staff staff) throws SQLException {
        String sql = "INSERT INTO staff (full_name, phone, email, position) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, staff.getFullName());
            stmt.setString(2, staff.getPhone());
            stmt.setString(3, staff.getEmail());
            stmt.setString(4, staff.getPosition());
            stmt.executeUpdate();
        }
    }

    public List<Staff> getAllStaff() throws SQLException {
        List<Staff> list = new ArrayList<>();
        String sql = "SELECT * FROM staff";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Staff(
                        rs.getInt("id"),
                        rs.getString("full_name"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("position")
                ));
            }
        }
        return list;
    }

    public void updateStaffPhone(int id, String newPhone) throws SQLException {
        String sql = "UPDATE staff SET phone = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, newPhone);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        }
    }

    public void updateStaffEmail(int id, String newEmail) throws SQLException {
        String sql = "UPDATE staff SET email = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, newEmail);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        }
    }

    public void deleteStaff(int id) throws SQLException {
        String sql = "DELETE FROM staff WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
