package brainacad.service;

import brainacad.model.Drink;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DrinkService {
    private final JdbcTemplate jdbc;

    public DrinkService(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public void addDrink(Drink drink) {
        String sql = "INSERT INTO drink (name_en, name_local, price) VALUES (?, ?, ?)";
        jdbc.update(sql, drink.getNameEn(), drink.getNameLocal(), drink.getPrice());
    }

    public List<Drink> getAllDrinks() {
        return jdbc.query("SELECT * FROM drink", drinkRowMapper());
    }

    public Drink getDrinkById(int id) {
        String sql = "SELECT * FROM drink WHERE id = ?";
        return jdbc.query(sql, drinkRowMapper(), id)
                .stream()
                .findFirst()
                .orElse(null);
    }

    public void renameDrink(int id, String newNameEn, String newNameLocal) {
        String sql = "UPDATE drink SET name_en = ?, name_local = ? WHERE id = ?";
        jdbc.update(sql, newNameEn, newNameLocal, id);
    }

    public void updateDrink(Drink drink) {
        String sql = "UPDATE drink SET name_en = ?, name_local = ?, price = ? WHERE id = ?";
        jdbc.update(sql,
                drink.getNameEn(),
                drink.getNameLocal(),
                drink.getPrice(),
                drink.getId());
    }

    public void deleteDrink(int id) {
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
