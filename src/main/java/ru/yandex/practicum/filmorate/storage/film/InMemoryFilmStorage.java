package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.controller.FilmAndUserValidator;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.validation.NotFoundException;
import ru.yandex.practicum.filmorate.validation.ValidationException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private int id;
    private final HashMap<Integer, Film> filmList = new HashMap<>();
    private final FilmService filmService;

    @Autowired
    public InMemoryFilmStorage(FilmService filmService) {
        this.filmService = filmService;
    }

    @Override
    public Film addFilm(Film film) {
        if (FilmAndUserValidator.validateFilm(film)) {
            if (film.getId() == 0) {
                film.setId(++id);
            }
            filmList.put(film.getId(), film);
        } else {
            throw new ValidationException("Фильм не прошел валидацию");
        }
        return film;
    }

    @Override
    public Film getFilm(int id) {
        if (filmList.containsKey(id)) {
            return filmList.get(id);
        } else {
            throw new NotFoundException("Фильм не найдем");
        }
    }

    @Override
    public Film updateFilm(Film film) {
        if (filmList.containsKey(film.getId())) {
            filmList.put(film.getId(), film);
            return film;
        } else {
            throw new NotFoundException("Фильм не найдем");
        }
    }

    @Override
    public List<Film> allFilms() {
        List<Film> films = new ArrayList<>(filmList.values());
        return films;
    }

    @Override
    public void deleteFilm(int id) {
        if (filmList.containsKey(id)) {
            filmList.remove(id);
        } else {
            throw new NotFoundException("Фильм не найдем");
        }

    }

    @Override
    public void addLike(int userId, int filmId) {
        if (filmList.containsKey(filmId) && userId > 0) {
            filmService.addLike(userId, filmList.get(filmId));
        } else {
            throw new NotFoundException("Фильм не найдем");
        }
    }

    @Override
    public void deleteLike(int userId, int filmId) {
        if (filmList.containsKey(filmId) && userId > 0) {
            filmService.deleteLike(userId, filmList.get(filmId));
        } else {
            throw new NotFoundException("Фильм не найдем");
        }

    }

    @Override
    public List<Film> topFilms(int count) {
        return filmService.topFilms(filmList, count);
    }
}
