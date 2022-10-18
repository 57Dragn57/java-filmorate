package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {
    Film addFilm(Film film);

    Film updateFilm(Film film);

    List<Film> allFilms();

    void deleteFilm(int id);

    void addLike(int userId, int filmId);

    void deleteLike(int userId, int filmId);

    List<Film> topFilms(int count);

    Film getFilm(int id);

}
