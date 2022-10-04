package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validation.ValidationException;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@Slf4j
public class UserController {

    private int id;

    private static final HashMap<Integer, User> userList = new HashMap<>();

    @PostMapping("/users")
    public User addUser(@Valid @RequestBody User user) {
        if (FilmAndUserValidator.validateUser(user)) {
            if (user.getName() == null || user.getName().isBlank()) {
                user.setName(user.getLogin());
            }
            if (user.getId() == 0) {
                user.setId(++id);
            }
            log.info("Добавление нового пользователя: {}", user.getName());
            userList.put(user.getId(), user);
        }else{
            throw new ValidationException("Пользователь не прошел валидацию");
        }
        return user;
    }

    @PutMapping("/users")
    public User updateUser(@Valid @RequestBody User user) {
        if (userList.containsKey(user.getId())) {
            log.info("Обновление пользователя: {}", user.getLogin());
            userList.put(user.getId(), user);
            return user;
        } else {
            throw new ValidationException("Такого пользователя не существует");
        }
    }

    @GetMapping("/users")
    public List<User> allUsers() {
        log.info("Запрос на получение списка пользователей");
        List<User> users = new ArrayList<>(userList.values());
        return users;
    }
    public HashMap<Integer, User> getUserList(){
        return userList;
    }
}
