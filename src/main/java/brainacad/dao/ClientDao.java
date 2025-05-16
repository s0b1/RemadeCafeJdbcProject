package brainacad.dao;

import brainacad.model.Client;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public class ClientDao {
    private final JdbcTemplate jdbc;

    public ClientDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public void create(Client client) {
        String sql = "INSERT INTO client (full_name, birth_date, phone, email, discount) VALUES (?, ?, ?, ?, ?)";
        jdbc.update(sql,
                client.getFullName(),
                Date.valueOf(client.getBirthDate()),
                client.getPhone(),
                client.getEmail(),
                client.getDiscount());
    }

    public List<Client> readAll() {
        return jdbc.query("SELECT * FROM client", clientRowMapper());
    }

    public Client readById(int id) {
        String sql = "SELECT * FROM client WHERE id = ?";
        return jdbc.query(sql, clientRowMapper(), id)
                .stream()
                .findFirst()
                .orElse(null);
    }

    public void update(Client client) {
        String sql = "UPDATE client SET full_name = ?, birth_date = ?, phone = ?, email = ?, discount = ? WHERE id = ?";
        jdbc.update(sql,
                client.getFullName(),
                Date.valueOf(client.getBirthDate()),
                client.getPhone(),
                client.getEmail(),
                client.getDiscount(),
                client.getId());
    }

    public void delete(int id) {
        jdbc.update("DELETE FROM client WHERE id = ?", id);
    }

    private RowMapper<Client> clientRowMapper() {
        return (rs, rowNum) -> new Client(
                rs.getInt("id"),
                rs.getString("full_name"),
                rs.getDate("birth_date").toLocalDate(),
                rs.getString("phone"),
                rs.getString("email"),
                rs.getDouble("discount")
        );
    }
}