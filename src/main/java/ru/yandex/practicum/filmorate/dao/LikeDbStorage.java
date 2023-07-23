package ru.yandex.practicum.filmorate.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class LikeDbStorage implements LikeStorage {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LikeDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addLike(int userId, int filmId) {
        jdbcTemplate.update("insert into LIKES (user_id, film_id) VALUES (?, ?)", userId, filmId);
    }

    @Override
    public void deleteLike(int userId, int filmId) {
        jdbcTemplate.update("delete from LIKES where USER_ID = ? and FILM_ID = ?", userId, filmId);
    }
}
