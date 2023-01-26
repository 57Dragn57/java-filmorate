package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@Slf4j
@Validated
public class FilmController {
    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @PostMapping("/films")
    public Film addFilm(@Valid @RequestBody Film film) {
        log.info("Идет процесс добавление нового фильма: {}", film.getName());
        return filmService.addFilm(film);
    }

    @PutMapping("/films")
    public Film updateFilm(@Valid @RequestBody Film film) {
        log.info("Обновление фильма: {}", film.getName());
        return filmService.updateFilm(film);
    }

    @GetMapping("/films")
    public List<Film> findAllFilms() {
        log.info("Идет процесс получения списка фильмов");
        return filmService.findAllFilms();
    }

    @DeleteMapping("/films/{id}")
    public void deleteFilm(@PathVariable int id) {
        log.info("Идет процесс даления фильма {}", id);
        filmService.deleteFilm(id);
    }


    @GetMapping("/films/popular")
    public List<Film> findTopFilms(@Positive @RequestParam(defaultValue = "10") Integer count) {
        log.info("Процесс формирования лучших фильмов");
        return filmService.topFilms(count);
    }

    @GetMapping("/films/{id}")
    public Film getFilm(@PathVariable int id) {
        log.info("Запрос на получение фильма {}", id);
        return filmService.getFilm(id);
    }
}