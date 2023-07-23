package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.GenreDbStorage;
import ru.yandex.practicum.filmorate.dao.GenreStorage;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.Genre;

import java.util.List;

@Service
public class GenreService {

    private final GenreStorage genreStorage;

    @Autowired
    public GenreService(GenreDbStorage genreStorage) {
        this.genreStorage = genreStorage;
    }

    public List<Genre> getGenres() {
        return genreStorage.getGenres();
    }

    public Genre getGenre(int id) {
        return genreStorage.getGenre(id);
    }

    public void load(List<Film> filmList) {
        genreStorage.load(filmList);
    }
}
