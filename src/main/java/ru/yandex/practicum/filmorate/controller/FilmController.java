package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
public class FilmController {
    private final InMemoryFilmStorage filmStorage;
    private final FilmService filmService;

    @Autowired
    public FilmController(InMemoryFilmStorage filmStorage, FilmService filmService) {
        this.filmStorage = filmStorage;
        this.filmService = filmService;
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
    public void deleteFilm(@PathVariable int id){
        log.info("Идет процесс даления фильма {}", id);
        filmStorage.deleteFilm(id);
    }
}