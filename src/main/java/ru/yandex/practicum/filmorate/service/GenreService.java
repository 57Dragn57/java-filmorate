package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.GenreDbStorage;
import ru.yandex.practicum.filmorate.storage.film.Genre;

import java.util.LinkedHashSet;

@Service
public class GenreService {

    private final GenreDbStorage genreStorage;

    @Autowired
    public GenreService(GenreDbStorage genreStorage) {
        this.genreStorage = genreStorage;
    }

    public LinkedHashSet<Genre> getGenres() {
        return genreStorage.getGenres();
    }

    public Genre getGenre(int id) {
        return genreStorage.getGenre(id);
    }
}
