package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;

@Service
public class UserService {

    private final UserStorage userStorage;

    @Autowired
    public UserService(UserDbStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User addUser(User user) {
        nameValidate(user);
        return userStorage.addUser(user);
    }

    public User updateUser(User user) {
        nameValidate(user);
        return userStorage.updateUser(user);
    }

    private void nameValidate(User user){
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }

    public List<User> findAllUsers() {
        return userStorage.findAllUsers();
    }

    public User getUser(int id) {
        return userStorage.getUser(id);
    }

    public void deleteUser(int id) {
        userStorage.deleteUser(id);
    }
}
