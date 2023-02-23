package ru.yandex.practicum.filmorate.dao;

public interface LikeStorage {

    void addLike(int userId, int filmId);

    void deleteLike(int userId, int filmId);
}
