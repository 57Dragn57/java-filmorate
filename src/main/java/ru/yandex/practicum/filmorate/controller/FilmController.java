package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.film.Genre;
import ru.yandex.practicum.filmorate.storage.film.MPA;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
public class FilmController {
    private final FilmDbStorage filmDbStorage;

    @Autowired
    public FilmController(FilmDbStorage filmDbStorage) {
        this.filmDbStorage = filmDbStorage;
    }

    @PostMapping("/films")
    public Film addFilm(@Valid @RequestBody Film film) {
        log.info("Идет процесс добавление нового фильма: {}", film.getName());
        return filmDbStorage.addFilm(film);
    }

    @PutMapping("/films")
    public Film updateFilm(@Valid @RequestBody Film film) {
        log.info("Обновление фильма: {}", film.getName());
        return filmDbStorage.updateFilm(film);
    }

    @GetMapping("/films")
    public List<Film> allFilms() {
        log.info("Идет процесс получения списка фильмов");
        return filmDbStorage.allFilms();
    }

    @DeleteMapping("/films/{id}")
    public void deleteFilm(@PathVariable int id) {
        log.info("Идет процесс даления фильма {}", id);
        filmDbStorage.deleteFilm(id);
    }

    @PutMapping("/films/{id}/like/{userId}")
    public void addLike(@PathVariable int id, @PathVariable int userId) {
        log.info("Добавление лайка к фильму {} от пользователя {}", id, userId);
        filmDbStorage.addLike(userId, id);
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public void deleteLike(@PathVariable int id, @PathVariable int userId) {
        log.info("Удаление лайка с фильма {} пользователем {}", id, userId);
        filmDbStorage.deleteLike(userId, id);
    }

    @GetMapping("/films/popular")
    public List<Film> topFilms(@RequestParam(required = false) Integer count) {
        log.info("Процесс формирования лучших фильмов");

        if (count == null)
            count = 10;

        return filmDbStorage.topFilms(count);
    }

    @GetMapping("/films/{id}")
    public Film getFilm(@PathVariable int id) {
        log.info("Запрос на получение фильма {}", id);
        return filmDbStorage.getFilm(id);
    }

    @GetMapping("/genres")
    public List<Genre> getGenres() {
        log.info("Запрос на получение жанров");
        return filmDbStorage.getGenres();
    }

    @GetMapping("/genres/{id}")
    public Genre getGenre(@PathVariable int id) {
        log.info("Запрос на получение жанра {}", id);
        return filmDbStorage.getGenre(id);
    }

    @GetMapping("/mpa")
    public List<MPA> getRatings() {
        log.info("Запрос на получение рейтингов");
        return filmDbStorage.getRatings();
    }

    @GetMapping("/mpa/{id}")
    public MPA getRating(@PathVariable int id) {
        log.info("Запрос на рейтинг");
        return filmDbStorage.getRating(id);
    }
}