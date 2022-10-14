package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.controller.FilmAndUserValidator;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validation.ValidationException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private int id;
    private static final HashMap<Integer, Film> filmList = new HashMap<>();

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
    public Film updateFilm(Film film) {
        if (filmList.containsKey(film.getId())) {
            filmList.put(film.getId(), film);
            return film;
        } else {
            throw new ValidationException("Фильм не найдем");
        }
    }

    @Override
    public List<Film> allFilms() {
        List<Film> films = new ArrayList<>(filmList.values());
        return films;
    }

    @Override
    public void deleteFilm(int id) {
        filmList.remove(id);
    }
}
