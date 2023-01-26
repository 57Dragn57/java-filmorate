package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.LikeDbStorage;
import ru.yandex.practicum.filmorate.dao.LikeStorage;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeStorage likeStorage;
    private final UserService userService;
    private final FilmService filmService;

    public void addLike(int id, int userId) {
        userService.getUser(userId);
        filmService.getFilm(id);
        likeStorage.addLike(id, userId);
    }

    public void deleteLike(int id, int userId) {
        userService.getUser(userId);
        filmService.getFilm(id);
        likeStorage.deleteLike(id, userId);
    }
}
