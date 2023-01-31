package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.validation.ServerErrorException;

import java.time.LocalDate;
import java.util.List;

@Service
public class FilmService {

    private final FilmStorage filmStorage;
    private static final LocalDate localDate = LocalDate.of(1895, 12, 28);

    @Autowired
    public FilmService(FilmDbStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public Film addFilm(Film film) {
        dateValidate(film);
        return filmStorage.addFilm(film);
    }

    public Film updateFilm(Film film) {
        dateValidate(film);
        return filmStorage.updateFilm(film);
    }

    public List<Film> findAllFilms() {
        return filmStorage.findAllFilms();
    }

    public void deleteFilm(int id) {
        filmStorage.deleteFilm(id);
    }

    public List<Film> topFilms(int count) {
        return filmStorage.topFilms(count);
    }

    public Film getFilm(int id) {
        return filmStorage.getFilm(id);
    }

    private void dateValidate(Film film) {
        if (film.getReleaseDate().isBefore(localDate)) {
            throw new ServerErrorException("Передана неверная дата");
        }
    }
}
