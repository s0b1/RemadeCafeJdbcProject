package brainacad.dao;

import brainacad.model.Dessert;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DessertDao {

    private final JdbcTemplate jdbc;

    public DessertDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public void create(Dessert dessert) {
        String sql = "INSERT INTO dessert (name_en, name_local, price) VALUES (?, ?, ?)";
        jdbc.update(sql, dessert.getNameEn(), dessert.getNameLocal(), dessert.getPrice());
    }

    public List<Dessert> readAll() {
        return jdbc.query("SELECT * FROM dessert", dessertRowMapper());
    }

    public Dessert readById(int id) {
        String sql = "SELECT * FROM dessert WHERE id = ?";
        return jdbc.query(sql, dessertRowMapper(), id)
                .stream()
                .findFirst()
                .orElse(null);
    }

    public void update(Dessert dessert) {
        String sql = "UPDATE dessert SET name_en = ?, name_local = ?, price = ? WHERE id = ?";
        jdbc.update(sql,
                dessert.getNameEn(),
                dessert.getNameLocal(),
                dessert.getPrice(),
                dessert.getId());
    }

    public void delete(int id) {
        jdbc.update("DELETE FROM dessert WHERE id = ?", id);
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
