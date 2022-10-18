package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {
    User addUser(User user);

    User updateUser(User user);

    List<User> allUsers();

    void deleteUser(int id);

    void addFriend(int firstUser, int secondUser);

    void removeFriend(int firstUser, int secondUser);

    List<User> commonFriends(int firstUser, int secondUser);

    List<User> getFriends(int id);

    User getUser(int id);
}
