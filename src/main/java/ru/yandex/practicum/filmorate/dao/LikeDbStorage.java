package ru.yandex.practicum.filmorate.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.validation.NotFoundException;

@Component
public class LikeDbStorage {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LikeDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addLike(int userId, int filmId) {
        SqlRowSet rowsFilm = jdbcTemplate.queryForRowSet("select * from FILMS where ID = ?", filmId);
        SqlRowSet rowsUser = jdbcTemplate.queryForRowSet("select * from USERS where ID = ?", userId);

        if (rowsFilm.next() && rowsUser.next()) {
            jdbcTemplate.update("insert into LIKES (user_id, film_id) VALUES (?, ?)", userId, filmId);
        } else {
            throw new NotFoundException("Фильм не найдем");
        }
    }

    public void deleteLike(int userId, int filmId) {
        SqlRowSet rowsLike = jdbcTemplate.queryForRowSet("select * from LIKES where USER_ID = ? and FILM_ID = ?", userId, filmId);

        if (rowsLike.next()) {
            jdbcTemplate.update("delete from LIKES where USER_ID = ? and FILM_ID = ?", userId, filmId);
        } else {
            throw new NotFoundException("Лайк не найден");
        }
    }
}
