package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.validation.ServerErrorException;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmStorage filmStorage;
    private final GenreService genreService;
    private static final LocalDate localDate = LocalDate.of(1895, 12, 28);

    public Film addFilm(Film film) {
        dateValidate(film);
        return filmStorage.addFilm(film);
    }

    public Film updateFilm(Film film) {
        dateValidate(film);
        return filmStorage.updateFilm(film);
    }

    public List<Film> findAllFilms() {
        List<Film> films = filmStorage.findAllFilms();
        genreService.load(films);
        return films;
    }

    public void deleteFilm(int id) {
        filmStorage.deleteFilm(id);
    }

    public List<Film> topFilms(int count) {
        List<Film> films = filmStorage.topFilms(count);
        genreService.load(films);
        return films;
    }

    public Film getFilm(int id) {
        Film film = filmStorage.getFilm(id);
        genreService.load(List.of(film));
        return film;
    }

    private void dateValidate(Film film) {
        if (film.getReleaseDate().isBefore(localDate)) {
            throw new ServerErrorException("Передана неверная дата");
        }
    }
}
