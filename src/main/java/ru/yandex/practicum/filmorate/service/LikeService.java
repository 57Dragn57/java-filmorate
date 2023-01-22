package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.LikeDbStorage;

@Service
public class LikeService {

    private final LikeDbStorage likeStorage;

    @Autowired
    public LikeService(LikeDbStorage likeStorage) {
        this.likeStorage = likeStorage;
    }

    public void addLike(int id, int userId) {
        likeStorage.addLike(id, userId);
    }

    public void deleteLike(int id, int userId) {
        likeStorage.deleteLike(id, userId);
    }
}
