package ru.yandex.practicum.filmorate.controller;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validation.ValidationException;

import java.time.LocalDate;

public class FilmAndUserValidator {

    public static boolean validateFilm(Film film) {
        if (!film.getName().isBlank()) {
            if (film.getDescription().length() < 201) {
                if (film.getReleaseDate().isAfter(LocalDate.of(1895, 12, 27))) {
                    if (film.getDuration() > 0) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean validateUser(User user) {
        if (user.getEmail().contains("@") && !user.getEmail().isBlank()) {
            if (!user.getLogin().isBlank() && !user.getLogin().contains(" ")) {
                if (!user.getBirthday().isAfter(LocalDate.now())) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
