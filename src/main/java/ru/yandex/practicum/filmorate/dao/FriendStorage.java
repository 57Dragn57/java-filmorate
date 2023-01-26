package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface FriendStorage {
    void addFriend(int firstUser, int secondUser);

    void removeFriend(int firstUser, int secondUser);

    List<User> commonFriends(int firstUser, int secondUser);

    List<User> getFriends(int id);
}
