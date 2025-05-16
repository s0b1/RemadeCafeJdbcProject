package brainacad.service;

import brainacad.model.Client;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

// zadaniya 1-2

@Service
public class ClientService {
    private final JdbcTemplate jdbc;

    public ClientService(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }


    public void addClient(Client client) {
        String sql = "INSERT INTO client (full_name, birth_date, phone, email, discount) VALUES (?, ?, ?, ?, ?)";
        jdbc.update(sql,
                client.getFullName(),
                Date.valueOf(client.getBirthDate()),
                client.getPhone(),
                client.getEmail(),
                client.getDiscount());
    }

    public List<Client> getAllClients() {
        return jdbc.query("SELECT * FROM client", clientRowMapper());
    }

    public void updateClientDiscount(int id, double discount) {
        String sql = "UPDATE client SET discount = ? WHERE id = ?";
        jdbc.update(sql, discount, id);
    }

    public void deleteClient(int id) {
        jdbc.update("DELETE FROM client WHERE id = ?", id);
    }


    public Double getMinDiscount() {
        return jdbc.queryForObject("SELECT MIN(discount) FROM client", Double.class);
    }

    public Double getMaxDiscount() {
        return jdbc.queryForObject("SELECT MAX(discount) FROM client", Double.class);
    }

    public Double getAvgDiscount() {
        return jdbc.queryForObject("SELECT AVG(discount) FROM client", Double.class);
    }

    public List<Client> getClientsWithMinDiscount() {
        String sql = "SELECT * FROM client WHERE discount = (SELECT MIN(discount) FROM client)";
        return jdbc.query(sql, clientRowMapper());
    }

    public List<Client> getClientsWithMaxDiscount() {
        String sql = "SELECT * FROM client WHERE discount = (SELECT MAX(discount) FROM client)";
        return jdbc.query(sql, clientRowMapper());
    }



    public Client getYoungestClient() {
        String sql = "SELECT * FROM client WHERE birth_date = (SELECT MAX(birth_date) FROM client)";
        return jdbc.queryForObject(sql, clientRowMapper());
    }

    public Client getOldestClient() {
        String sql = "SELECT * FROM client WHERE birth_date = (SELECT MIN(birth_date) FROM client)";
        return jdbc.queryForObject(sql, clientRowMapper());
    }

    public List<Client> getClientsWithBirthdayToday() {
        String sql = """
            SELECT * FROM client
            WHERE EXTRACT(DAY FROM birth_date) = EXTRACT(DAY FROM CURRENT_DATE)
              AND EXTRACT(MONTH FROM birth_date) = EXTRACT(MONTH FROM CURRENT_DATE)
        """;
        return jdbc.query(sql, clientRowMapper());
    }

    public List<Client> getClientsWithoutEmail() {
        String sql = "SELECT * FROM client WHERE email IS NULL OR TRIM(email) = ''";
        return jdbc.query(sql, clientRowMapper());
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
