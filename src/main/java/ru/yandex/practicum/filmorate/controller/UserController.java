package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
public class UserController {
    private final UserDbStorage userDbStorage;

    @Autowired
    public UserController(UserDbStorage userDbStorage) {
        this.userDbStorage = userDbStorage;
    }

    @PostMapping("/users")
    public User addUser(@Valid @RequestBody User user) {
        log.info("Добавление нового пользователя: {}", user.getName());
        return userDbStorage.addUser(user);
    }

    @PutMapping("/users")
    public User updateUser(@Valid @RequestBody User user) {
        log.info("Обновление пользователя: {}", user.getLogin());
        return userDbStorage.updateUser(user);
    }

    @GetMapping("/users")
    public List<User> allUsers() {
        log.info("Запрос на получение списка пользователей");
        return userDbStorage.allUsers();
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable int id) {
        log.info("Запрос на получение пользователя {}", id);
        return userDbStorage.getUser(id);
    }

    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable int id) {
        log.info("Идет процесс удаления пользователя {}", id);
        userDbStorage.deleteUser(id);
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    public void addFriend(@PathVariable int id, @PathVariable int friendId) {
        log.info("Пользователь {} добавил в друзья пользователя {}", id, friendId);
        userDbStorage.addFriend(id, friendId);
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    public void removeFriend(@PathVariable int id, @PathVariable int friendId) {
        log.info("Пользователь {} удалил {} из друзей", id, friendId);
        userDbStorage.removeFriend(id, friendId);
    }

    @GetMapping("/users/{id}/friends")
    public List<User> getFriends(@PathVariable int id) {
        log.info("Процесс получения списка друзей пользователем {}", id);
        return userDbStorage.getFriends(id);
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public List<User> commonFriends(@PathVariable int id, @PathVariable int otherId) {
        log.info("Процесс получения общих друзей {} и {}", id, otherId);
        return userDbStorage.commonFriends(id, otherId);
    }
}
