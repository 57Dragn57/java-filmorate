package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public User addUser(@Valid @RequestBody User user) {
        log.info("Добавление нового пользователя: {}", user.getName());
        return userService.addUser(user);
    }

    @PutMapping("/users")
    public User updateUser(@Valid @RequestBody User user) {
        log.info("Обновление пользователя: {}", user.getLogin());
        return userService.updateUser(user);
    }

    @GetMapping("/users")
    public List<User> findAllUsers() {
        log.info("Запрос на получение списка пользователей");
        return userService.findAllUsers();
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable int id) {
        log.info("Запрос на получение пользователя {}", id);
        return userService.getUser(id);
    }

    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable int id) {
        log.info("Идет процесс удаления пользователя {}", id);
        userService.deleteUser(id);
    }

}
