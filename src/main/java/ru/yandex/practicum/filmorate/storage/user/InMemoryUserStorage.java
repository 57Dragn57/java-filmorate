package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.controller.FilmAndUserValidator;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validation.ValidationException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class InMemoryUserStorage implements UserStorage {
    private int id;
    private static final HashMap<Integer, User> userList = new HashMap<>();

    @Override
    public User addUser(User user) {
        if (FilmAndUserValidator.validateUser(user)) {
            if (user.getName() == null || user.getName().isBlank()) {
                user.setName(user.getLogin());
            }
            if (user.getId() == 0) {
                user.setId(++id);
            }
            userList.put(user.getId(), user);
        } else {
            throw new ValidationException("Пользователь не прошел валидацию");
        }
        return user;
    }

    @Override
    public User updateUser(User user) {
        if (userList.containsKey(user.getId())) {
            userList.put(user.getId(), user);
            return user;
        } else {
            throw new ValidationException("Такого пользователя не существует");
        }
    }

    @Override
    public List<User> allUsers() {
        List<User> users = new ArrayList<>(userList.values());
        return users;
    }

    @Override
    public void deleteUser(int id){
        userList.remove(id);
    }
}
