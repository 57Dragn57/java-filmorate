package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class FilmService {

    public void addLike(User user, Film film) {
        film.getLikesList().add(user.getId());
    }

    public void deleteLike(User user, Film film) {
        film.getLikesList().remove(user.getId());
    }

    public List<Film> topFilms(HashMap<Integer, Film> filmList, int count) {
        List<Film> top = new ArrayList<>();
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
        return top;
    }
}
