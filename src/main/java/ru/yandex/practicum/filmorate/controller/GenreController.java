package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.service.GenreService;
import ru.yandex.practicum.filmorate.storage.film.Genre;

import java.util.LinkedHashSet;
import java.util.List;

@RestController
@Slf4j
public class GenreController {
    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/genres")
    public List<Genre> getGenres() {
        log.info("Запрос на получение жанров");
        return genreService.getGenres();
    }

    @GetMapping("/genres/{id}")
    public Genre getGenre(@PathVariable int id) {
        log.info("Запрос на получение жанра {}", id);
        return genreService.getGenre(id);
    }
}
