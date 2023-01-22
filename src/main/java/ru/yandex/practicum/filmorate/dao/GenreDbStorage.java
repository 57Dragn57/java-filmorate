package ru.yandex.practicum.filmorate.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.storage.film.Genre;
import ru.yandex.practicum.filmorate.validation.NotFoundException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.List;

@Component
public class GenreDbStorage {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GenreDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Genre makeGenre(ResultSet rs) throws SQLException {
        return new Genre(
                rs.getInt("genre_id"),
                rs.getString("name")
        );
    }

    public Genre getGenre(int id) {
        if (id < 6 && id > 0) {
            String sql = "select * from GENRES where GENRE_ID = ?";
            List<Genre> genres = jdbcTemplate.query(sql, (rs, rowNum) -> makeGenre(rs), id);
            return genres.get(0);
        } else {
            throw new NotFoundException("Не верно задан id");
        }
    }

    public LinkedHashSet<Genre> getGenres() {
        String sql = "select * from GENRES limit 6";
        return new LinkedHashSet<>(jdbcTemplate.query(sql, (rs, rowNum) -> makeGenre(rs)));
    }
}
