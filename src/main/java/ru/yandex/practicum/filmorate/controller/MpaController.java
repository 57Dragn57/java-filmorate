package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.service.MpaService;
import ru.yandex.practicum.filmorate.storage.film.Mpa;

import java.util.List;

@RestController
@Slf4j
public class MpaController {
    private final MpaService mpaService;

    @Autowired
    public MpaController(MpaService mpaService) {
        this.mpaService = mpaService;
    }

    @GetMapping("/mpa")
    public List<Mpa> getAllMpa() {
        log.info("Запрос на получение рейтингов");
        return mpaService.getAllMpa();
    }

    @GetMapping("/mpa/{id}")
    public Mpa getMpa(@PathVariable int id) {
        log.info("Запрос на рейтинг");
        return mpaService.getMpa(id);
    }

}
