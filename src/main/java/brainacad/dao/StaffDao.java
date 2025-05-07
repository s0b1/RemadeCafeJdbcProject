package brainacad.dao;

import brainacad.model.Staff;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StaffDao {
    private final Connection connection;

    public StaffDao(Connection connection)
    {
        this.connection = connection;
    }

    public void create(Staff staff) throws SQLException
    {
        String sql = "INSERT INTO staff (full_name, phone, email, position) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, staff.getFullName());
            stmt.setString(2, staff.getPhone());
            stmt.setString(3, staff.getEmail());
            stmt.setString(4, staff.getPosition());
            stmt.executeUpdate();
        }
    }

    public List<Staff> readAll() throws SQLException
    {
        List<Staff> list = new ArrayList<>();
        String sql = "SELECT * FROM staff";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql))
        {
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

    public Staff readById(int id) throws SQLException
    {
        String sql = "SELECT * FROM staff WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql))
        {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Staff(
                        rs.getInt("id"),
                        rs.getString("full_name"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("position")
                );
            }
        }
        return null;
    }

    public void update(Staff staff) throws SQLException
    {
        String sql = "UPDATE staff SET full_name = ?, phone = ?, email = ?, position = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql))
        {
            stmt.setString(1, staff.getFullName());
            stmt.setString(2, staff.getPhone());
            stmt.setString(3, staff.getEmail());
            stmt.setString(4, staff.getPosition());
            stmt.setInt(5, staff.getId());
            stmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException
    {
        String sql = "DELETE FROM staff WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql))
        {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}