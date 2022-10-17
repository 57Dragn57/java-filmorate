package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
public class FilmController {
    private final InMemoryFilmStorage filmStorage;

    @Autowired
    public FilmController(InMemoryFilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    @PostMapping("/films")
    public Film addFilm(@Valid @RequestBody Film film) {
        log.info("Идет процесс добавление нового фильма: {}", film.getName());
        return filmStorage.addFilm(film);
    }

    @PutMapping("/films")
    public Film updateFilm(@Valid @RequestBody Film film) {
        log.info("Обновление фильма: {}", film.getName());
        return filmStorage.updateFilm(film);
    }

    @GetMapping("/films")
    public List<Film> allFilms() {
        log.info("Идет процесс получения списка фильмов");
        return filmStorage.allFilms();
    }

    @DeleteMapping("/films/{id}")
    public void deleteFilm(@PathVariable int id) {
        log.info("Идет процесс даления фильма {}", id);
        filmStorage.deleteFilm(id);
    }

    @PutMapping("/films/{id}/like/{userId}")
    public void addLike(@PathVariable int id, @PathVariable int userId) {
        log.info("Добавление лайка к фильму {} от пользователя {}", id, userId);
        filmStorage.addLike(userId, id);
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public void deleteLike(@PathVariable int id, @PathVariable int userId) {
        log.info("Удаление лайка с фильма {} пользователем {}", id, userId);
        filmStorage.deleteLike(userId, id);
    }

    @GetMapping("/films/popular")
    public List<Film> topFilms(@RequestParam(required = false) Integer count) {
        log.info("Процесс формирования лучших фильмов");

        if (count == null)
            count = 10;

        return filmStorage.topFilms(count);
    }

    @GetMapping("/films/{id}")
    public Film getFilm(@PathVariable int id){
        log.info("Запрос на получение фильма {}", id);
        return filmStorage.getFilm(id);
    }
}