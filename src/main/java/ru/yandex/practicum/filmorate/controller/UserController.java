package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
public class UserController {
    private final InMemoryUserStorage userStorage;
    private final UserService userService;

    @Autowired
    public UserController(InMemoryUserStorage userStorage, UserService userService) {
        this.userStorage = userStorage;
        this.userService = userService;
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

    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable int id){
        log.info("Идет процесс удаления пользователя {}", id);
        userStorage.deleteUser(id);
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    public void addFriend(int id, int friendId){
        
    }

}
