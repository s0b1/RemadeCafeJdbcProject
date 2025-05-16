package brainacad.service;

import brainacad.model.Dessert;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DessertService {
    private final JdbcTemplate jdbc;

    public DessertService(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public void addDessert(Dessert dessert) {
        String sql = "INSERT INTO dessert (name_en, name_local, price) VALUES (?, ?, ?)";
        jdbc.update(sql, dessert.getNameEn(), dessert.getNameLocal(), dessert.getPrice());
    }

    public List<Dessert> getAllDesserts() {
        String sql = "SELECT * FROM dessert";
        return jdbc.query(sql, dessertRowMapper());
    }

    public Dessert getDessertById(int id) {
        String sql = "SELECT * FROM dessert WHERE id = ?";
        return jdbc.query(sql, dessertRowMapper(), id)
                .stream()
                .findFirst()
                .orElse(null);
    }

    public void renameDessert(int id, String newNameEn, String newNameLocal) {
        String sql = "UPDATE dessert SET name_en = ?, name_local = ? WHERE id = ?";
        jdbc.update(sql, newNameEn, newNameLocal, id);
    }

    public void updateDessert(Dessert dessert) {
        String sql = "UPDATE dessert SET name_en = ?, name_local = ?, price = ? WHERE id = ?";
        jdbc.update(sql,
                dessert.getNameEn(),
                dessert.getNameLocal(),
                dessert.getPrice(),
                dessert.getId());
    }

    public void deleteDessert(int id) {
        String sql = "DELETE FROM dessert WHERE id = ?";
        jdbc.update(sql, id);
    }

    private RowMapper<Dessert> dessertRowMapper() {
        return (rs, rowNum) -> new Dessert(
                rs.getInt("id"),
                rs.getString("name_en"),
                rs.getString("name_local"),
                rs.getDouble("price")
        );
    }
}
