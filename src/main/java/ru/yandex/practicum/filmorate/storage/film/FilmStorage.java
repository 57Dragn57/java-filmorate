package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {
    public Film addFilm(Film film);

    public Film updateFilm(Film film);

    public List<Film> allFilms();

    public void deleteFilm(int id);

    public void addLike(int userId, int filmId);

    public void deleteLike(int userId, int filmId);

    public List<Film> topFilms(int count);

    public Film getFilm(int id);

}
