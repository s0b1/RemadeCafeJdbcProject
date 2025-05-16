package brainacad.dao;

import brainacad.model.Drink;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DrinkDao {

    private final JdbcTemplate jdbc;

    public DrinkDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public void create(Drink drink) {
        String sql = "INSERT INTO drink (name_en, name_local, price) VALUES (?, ?, ?)";
        jdbc.update(sql, drink.getNameEn(), drink.getNameLocal(), drink.getPrice());
    }

    public List<Drink> readAll() {
        String sql = "SELECT * FROM drink";
        return jdbc.query(sql, drinkRowMapper());
    }

    public Drink readById(int id) {
        String sql = "SELECT * FROM drink WHERE id = ?";
        return jdbc.query(sql, drinkRowMapper(), id)
                .stream()
                .findFirst()
                .orElse(null);
    }

    public void update(Drink drink) {
        String sql = "UPDATE drink SET name_en = ?, name_local = ?, price = ? WHERE id = ?";
        jdbc.update(sql,
                drink.getNameEn(),
                drink.getNameLocal(),
                drink.getPrice(),
                drink.getId());
    }

    public void delete(int id) {
        jdbc.update("DELETE FROM drink WHERE id = ?", id);
    }

    private RowMapper<Drink> drinkRowMapper() {
        return (rs, rowNum) -> new Drink(
                rs.getInt("id"),
                rs.getString("name_en"),
                rs.getString("name_local"),
                rs.getDouble("price")
        );
    }
}
