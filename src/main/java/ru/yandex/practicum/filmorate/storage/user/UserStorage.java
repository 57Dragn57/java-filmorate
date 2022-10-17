package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {
    public User addUser(User user);

    public User updateUser(User user);

    public List<User> allUsers();

    public void deleteUser(int id);

    public void addFriend(int firstUser, int secondUser);

    public void removeFriend(int firstUser, int secondUser);

    public List<User> commonFriends(int firstUser, int secondUser);

    public List<User> getFriends(int id);

    public User getUser(int id);
}
