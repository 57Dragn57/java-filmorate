package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {
    Film addFilm(Film film);

    Film updateFilm(Film film);

    List<Film> findAllFilms();

    void deleteFilm(int id);

    List<Film> topFilms(int count);

    Film getFilm(int id);

}
