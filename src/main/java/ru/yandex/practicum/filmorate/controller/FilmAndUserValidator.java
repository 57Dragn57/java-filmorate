package ru.yandex.practicum.filmorate.controller;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

public class FilmAndUserValidator {

    private static final LocalDate date = LocalDate.of(1895, 12, 27);

    public static boolean validateFilm(Film film) {

        if (film.getReleaseDate().isAfter(date)) {
            if (film.getId() <= 0) {
                film.setId(1);
            }
            return true;
        } else {
            return false;
        }
    }


    public static boolean validateUser(User user) {

        if (!user.getBirthday().isAfter(LocalDate.now())) {
            if (user.getId() <= 0) {
                user.setId(1);
            }
            return true;
        } else {
            return false;
        }
    }
}
