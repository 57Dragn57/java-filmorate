package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class FilmService {

    public void addLike(int userId, Film film) {
        film.getLikesList().add(userId);
    }

    public void deleteLike(int userId, Film film) {
        film.getLikesList().remove(userId);
    }

    public List<Film> topFilms(HashMap<Integer, Film> filmList, int count) {
        List<Film> top;
        if (count >= filmList.size()) {
            return new ArrayList<>(filmList.values());
        } else {
            top = new ArrayList<>();

            for (Film film : filmList.values()) {
                if (top.size() < count) {
                    top.add(film);
                } else {
                    for (Film f : top) {
                        if (f.getLikesList().size() < film.getLikesList().size()) {
                            top.remove(f);
                            top.add(film);
                        }
                    }
                }
            }
        }
        return top;
    }
}
