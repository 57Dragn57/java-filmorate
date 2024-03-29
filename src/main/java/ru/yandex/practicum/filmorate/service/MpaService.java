package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.MpaDbStorage;
import ru.yandex.practicum.filmorate.dao.MpaStorage;
import ru.yandex.practicum.filmorate.storage.film.Mpa;

import java.util.List;

@Service
public class MpaService {

    private final MpaStorage mpaStorage;

    @Autowired
    public MpaService(MpaDbStorage mpaStorage) {
        this.mpaStorage = mpaStorage;
    }

    public List<Mpa> getAllMpa() {
        return mpaStorage.getRatings();
    }

    public Mpa getMpa(int id) {
        return mpaStorage.getRating(id);
    }

}
