package ru.yandex.practicum.filmorate.storage.film;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validation.NotFoundException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Film addFilm(Film film) {
        long filmId = simpleSave(film);
        film.setId((int) filmId);

        if (film.getGenres() != null) {
            batchUpdater(film);
        }
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        SqlRowSet rowsFilm = jdbcTemplate.queryForRowSet("select * from FILMS where ID = ?", film.getId());
        if (rowsFilm.next()) {
            jdbcTemplate.update("update FILMS set NAME = ?, DESCRIPTION = ?, RELEACE_DATE = ?, DURATION = ?, RATING_ID = ? where ID = ?",
                    film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(), film.getMpa().getId(), film.getId());

            if (film.getGenres() != null) {
                jdbcTemplate.update("delete from FILMS_GENRES where FILM_ID = ?", film.getId());
                batchUpdater(film);
            }
            return film;
        } else {
            throw new NotFoundException("Фильм не найдем");
        }
    }

    @Override
    public List<Film> findAllFilms() {
        String sql = "select f.id as id, f.name as name, f.description as description, f.releace_date as releace_date, f.duration as duration, f.rating_id as rating_id, r.name as rat_name from FILMS as f left join RATINGS as r on f.RATING_ID = r.RATING_ID";

        return jdbcTemplate.query(sql, (rs, rowNum) -> makeFilm(rs));
    }

    @Override
    public void deleteFilm(int id) {
        SqlRowSet rowsFilm = jdbcTemplate.queryForRowSet("select * from FILMS where ID = ?", id);

        if (rowsFilm.next()) {
            jdbcTemplate.update("delete from FILMS where ID = ?", id);
        } else {
            throw new NotFoundException("Фильм не найдем");
        }
    }

    @Override
    public List<Film> topFilms(int count) {
        String sql = "select distinct id, f.name as name, description, releace_date, duration, f.rating_id as rating_id, r.NAME as rat_name, count(FILM_ID) " +
                "from FILMS as f " +
                "left join LIKES as l on f.ID = l.FILM_ID " +
                "left join RATINGS as r on f.RATING_ID = r.RATING_ID " +
                "group by FILM_ID, ID " +
                "order by count(FILM_ID) desc " +
                "limit  " + count;

        return jdbcTemplate.query(sql, (rs, rowNum) -> makeFilm(rs));
    }

    @Override
    public Film getFilm(int id) {
        String sql = "select f.id as id, f.name as name, f.description as description, f.releace_date as releace_date, f.duration as duration, f.rating_id as rating_id, r.name as rat_name from FILMS as f left join RATINGS as r on f.RATING_ID = r.RATING_ID where ID = ?";
        List<Film> film = new ArrayList<>(jdbcTemplate.query(sql, (rs, rowNum) -> makeFilm(rs), id));
        if (film.size() == 0) {
            throw new NotFoundException("Такого фильма не существует");
        } else {
            return film.get(0);
        }
    }

    private long simpleSave(Film film) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("films")
                .usingGeneratedKeyColumns("id");
        return simpleJdbcInsert.executeAndReturnKey(film.toMap()).longValue();
    }

    private Film makeFilm(ResultSet rss) throws SQLException {
        Film film = new Film(
                rss.getInt("id"),
                rss.getString("name"),
                rss.getString("description"),
                rss.getInt("duration"),
                rss.getDate("releace_date").toLocalDate()
        );

        int ratingId = rss.getInt("rating_id");
        String ratingName = rss.getString("rat_name");

        film.setMpa(new Mpa(ratingName, ratingId));

        return film;
    }

    private void batchUpdater(Film film) {
        jdbcTemplate.batchUpdate("INSERT INTO FILMS_GENRES (genre_id, film_id) VALUES(?, ?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                List<Genre> genres = new ArrayList<>(film.getGenres());
                preparedStatement.setString(1, Integer.toString(genres.get(i).getId()));
                preparedStatement.setString(2, Integer.toString(film.getId()));
            }

            @Override
            public int getBatchSize() {
                return film.getGenres().size();
            }
        });
    }
}
