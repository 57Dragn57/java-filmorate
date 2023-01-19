package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.controller.FilmAndUserValidator;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validation.NotFoundException;
import ru.yandex.practicum.filmorate.validation.ValidationException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@Qualifier("filmDbStorage")
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Film addFilm(Film film) {
        if (FilmAndUserValidator.validateFilm(film)) {
            long filmId = simpleSave(film);

            if (film.getGenres() != null) {
                for (Genre g : film.getGenres()) {
                    jdbcTemplate.update("insert into FILMS_GENRES (genre_id, film_id) VALUES (?, ?)", g.getId(), filmId);
                }
            }
            film.setId((int) filmId);
        } else {
            throw new ValidationException("Фильм не прошел валидацию");
        }

        return film;
    }

    private long simpleSave(Film film) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("films")
                .usingGeneratedKeyColumns("id");
        return simpleJdbcInsert.executeAndReturnKey(film.toMap()).longValue();
    }

    @Override
    public Film updateFilm(Film film) {
        SqlRowSet rowsFilm = jdbcTemplate.queryForRowSet("select * from FILMS where ID = ?", film.getId());
        if (rowsFilm.next()) {
            jdbcTemplate.update("update FILMS set NAME = ?, DESCRIPTION = ?, RELEACE_DATE = ?, DURATION = ?, RATING_ID = ? where ID = ?",
                    film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(), film.getMpa().getId(), film.getId());

            if (film.getGenres() != null) {
                jdbcTemplate.update("delete from FILMS_GENRES where FILM_ID = ?", film.getId());
                for (Genre g : film.getGenres()) {
                    SqlRowSet genreRows = jdbcTemplate.queryForRowSet("select * from films_genres where genre_id = ? and film_id = ?", g.getId(), film.getId());
                    if (!genreRows.next()) {
                        jdbcTemplate.update("insert into FILMS_GENRES (genre_id, film_id) VALUES (?, ?)", g.getId(), film.getId());
                    }
                }
            }

            return getFilm(film.getId());
        } else {
            throw new NotFoundException("Фильм не найдем");
        }
    }

    @Override
    public List<Film> allFilms() {
        String sql = "select f.id as id, f.name as name, f.description as description, f.releace_date as releace_date, f.duration as duration, f.rating_id as rating_id, r.name as rating_name from FILMS as f left join RATINGS as r on f.RATING_ID = r.RATING_ID";

        return jdbcTemplate.query(sql, (rs, rowNum) -> makeFilm(rs));
    }

    private Film makeFilm(ResultSet rss) throws SQLException {
        Film film = new Film(
                rss.getInt("id"),
                rss.getString("name"),
                rss.getString("description"),
                rss.getInt("duration"),
                rss.getDate("releace_date").toLocalDate()
        );

        String sql = "select g.name, fg.genre_id from FILMS_GENRES as fg left join GENRES as g on fg.genre_id = g.genre_id where fg.film_id = ?";
        film.setGenres(jdbcTemplate.query(sql, (rs, rowNum) -> genreList(rs), film.getId()));

        int ratingId = rss.getInt("rating_id");
        String ratingName = null;
        for (MPA mpa : getRatings()) {
            if (mpa.getId() == ratingId) {
                ratingName = mpa.getName();
            }
        }
        film.setMpa(new MPA(ratingName, ratingId));

        return film;
    }

    private Film makeFilm(SqlRowSet srs) {
        Film film = new Film(
                srs.getInt("id"),
                srs.getString("name"),
                srs.getString("description"),
                srs.getInt("duration"),
                srs.getDate("releace_date").toLocalDate()
        );

        String sql = "select g.name, fg.genre_id from FILMS_GENRES as fg left join GENRES as g on fg.genre_id = g.genre_id where fg.film_id = ?";
        film.setGenres(jdbcTemplate.query(sql, (rs, rowNum) -> genreList(rs), film.getId()));

        int ratingId = srs.getInt("rating_id");
        String ratingName = null;
        for (MPA mpa : getRatings()) {
            if (mpa.getId() == ratingId) {
                ratingName = mpa.getName();
            }
        }
        film.setMpa(new MPA(ratingName, ratingId));

        return film;
    }

    private Genre genreList(ResultSet rs) throws SQLException {
        return new Genre(
                rs.getInt("genre_id"),
                rs.getString("name")
        );
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
    public void addLike(int userId, int filmId) {
        SqlRowSet rowsFilm = jdbcTemplate.queryForRowSet("select * from FILMS where ID = ?", filmId);
        SqlRowSet rowsUser = jdbcTemplate.queryForRowSet("select * from USERS where ID = ?", userId);

        if (rowsFilm.next() && rowsUser.next()) {
            jdbcTemplate.update("insert into LIKES (user_id, film_id) VALUES (?, ?)", userId, filmId);
        } else {
            throw new NotFoundException("Фильм не найдем");
        }
    }

    @Override
    public void deleteLike(int userId, int filmId) {
        SqlRowSet rowsLike = jdbcTemplate.queryForRowSet("select * from LIKES where USER_ID = ? and FILM_ID = ?", userId, filmId);

        if (rowsLike.next()) {
            jdbcTemplate.update("delete from LIKES where USER_ID = ? and FILM_ID = ?", userId, filmId);
        } else {
            throw new NotFoundException("Лайк не найден");
        }
    }

    @Override
    public List<Film> topFilms(int count) {
        String sql = "select distinct id, name, description, releace_date, duration, rating_id, count(FILM_ID) " +
                "from FILMS as f left join LIKES as l on f.ID = l.FILM_ID " +
                "group by FILM_ID, ID " +
                "order by count(FILM_ID) desc " +
                "limit " + count;

        return jdbcTemplate.query(sql, (rs, rowNum) -> makeFilm(rs));
    }

    @Override
    public Film getFilm(int id) {
        SqlRowSet rowsFilm = jdbcTemplate.queryForRowSet("select * from FILMS where ID = ?", id);
        if (rowsFilm.next()) {
            return makeFilm(rowsFilm);
        } else {
            throw new NotFoundException("Фильм не найдем");
        }
    }

    private Genre genres(ResultSet rs) throws SQLException {
        return new Genre(
                rs.getInt("genre_id"),
                rs.getString("name")
        );
    }

    public Genre getGenre(int id) {
        if (id < 6 && id > 0) {
            String sql = "select * from GENRES where GENRE_ID = ?";
            List<Genre> genres = jdbcTemplate.query(sql, (rs, rowNum) -> genres(rs), id);
            return genres.get(0);
        } else {
            throw new NotFoundException("Не верно задан id");
        }
    }

    public List<Genre> getGenres() {
        String sql = "select * from GENRES limit 6";
        return jdbcTemplate.query(sql, (rs, rowNum) -> genres(rs));
    }

    private MPA ratings(ResultSet rs) throws SQLException {
        return new MPA(
                rs.getString("name"),
                rs.getInt("rating_id")
        );
    }

    public List<MPA> getRatings() {
        String sql = "select * from RATINGS limit 5";
        return jdbcTemplate.query(sql, (rs, rowNum) -> ratings(rs));
    }

    public MPA getRating(int id) {
        if (id < 6 && id > -1) {
            String sql = "select * from RATINGS where RATING_ID = ?";
            List<MPA> mpa = jdbcTemplate.query(sql, (rs, rowNum) -> ratings(rs), id);
            return mpa.get(0);
        } else {
            throw new NotFoundException("Не подходящее id");
        }
    }
}
