package ru.yandex.practicum.filmorate.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.storage.film.Mpa;
import ru.yandex.practicum.filmorate.validation.NotFoundException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class MpaDbStorage implements MpaStorage {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MpaDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Mpa> getRatings() {
        String sql = "select * from RATINGS";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeRating(rs));
    }

    @Override
    public Mpa getRating(int id) {
        String sql = "select * from RATINGS where RATING_ID = ?";
        List<Mpa> mpa = jdbcTemplate.query(sql, (rs, rowNum) -> makeRating(rs), id);

        if (mpa.size() > 0) {
            return mpa.get(0);
        } else {
            throw new NotFoundException("Не подходящее id");
        }
    }

    private Mpa makeRating(ResultSet rs) throws SQLException {
        return new Mpa(
                rs.getString("name"),
                rs.getInt("rating_id")
        );
    }
}
