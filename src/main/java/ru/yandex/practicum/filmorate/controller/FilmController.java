package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validation.ValidationException;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@Slf4j
public class FilmController {

    private int id;
    private static final HashMap<Integer, Film> filmList = new HashMap<>();

    @PostMapping("/films")
    public Film addFilm(@Valid @RequestBody Film film) {
        if (FilmAndUserValidator.validateFilm(film)) {
            if (film.getId() == 0) {
                film.setId(++id);
            }
            log.info("Идет процесс добавление нового фильма: {}", film.getName());
            filmList.put(film.getId(), film);
        }else{
            throw new ValidationException("Фильм не прошел валидацию");
        }
        return film;
    }

    @PutMapping("/films")
    public Film updateFilm(@Valid @RequestBody Film film) {
        if (filmList.containsKey(film.getId())) {
            log.info("Обновление фильма: {}", film.getName());
            filmList.put(film.getId(), film);
            return film;
        } else {
            throw new ValidationException("Фильм не найдем");
        }
    }

    @GetMapping("/films")
    public List<Film> allFilms() {
        log.info("Идет процесс получения списка фильмов");
        List<Film> films = new ArrayList<>(filmList.values());
        return films;
    }
}