package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.storage.film.Mpa;
import ru.yandex.practicum.filmorate.validation.NotFoundException;

import java.util.List;

public interface MpaStorage {

    List<Mpa> getRatings();

    Mpa getRating(int id);
}
