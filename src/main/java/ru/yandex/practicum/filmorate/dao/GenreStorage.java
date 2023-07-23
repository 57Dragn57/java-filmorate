package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.Genre;

import java.util.List;

public interface GenreStorage {

    Genre getGenre(int id);

    List<Genre> getGenres();

    void load(List<Film> films);
}
