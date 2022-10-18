package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
public class UserController {
    private final InMemoryUserStorage userStorage;

    @Autowired
    public UserController(InMemoryUserStorage userStorage) {
        this.userStorage = userStorage;
    }

    @PostMapping("/users")
    public User addUser(@Valid @RequestBody User user) {
        log.info("Добавление нового пользователя: {}", user.getName());
        return userStorage.addUser(user);
    }

    @PutMapping("/users")
    public User updateUser(@Valid @RequestBody User user) {
        log.info("Обновление пользователя: {}", user.getLogin());
        return userStorage.updateUser(user);
    }

    @GetMapping("/users")
    public List<User> allUsers() {
        log.info("Запрос на получение списка пользователей");
        return userStorage.allUsers();
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable int id) {
        log.info("Запрос на получение пользователя {}", id);
        return userStorage.getUser(id);
    }

    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable int id) {
        log.info("Идет процесс удаления пользователя {}", id);
        userStorage.deleteUser(id);
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    public void addFriend(@PathVariable int id, @PathVariable int friendId) {
        log.info("Пользователь {} добавил в друзья пользователя {}", id, friendId);
        userStorage.addFriend(id, friendId);
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    public void removeFriend(@PathVariable int id, @PathVariable int friendId) {
        log.info("Пользователь {} удалил {} из друзей", id, friendId);
        userStorage.removeFriend(id, friendId);
    }

    @GetMapping("/users/{id}/friends")
    public List<User> getFriends(@PathVariable int id) {
        log.info("Процесс получения списка друзей пользователем {}", id);
        return userStorage.getFriends(id);
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public List<User> commonFriends(@PathVariable int id, @PathVariable int otherId) {
        log.info("Процесс получения общих друзей {} и {}", id, otherId);
        return userStorage.commonFriends(id, otherId);
    }
}
