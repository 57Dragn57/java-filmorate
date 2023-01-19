package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.controller.FilmAndUserValidator;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.validation.NotFoundException;
import ru.yandex.practicum.filmorate.validation.ValidationException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class InMemoryUserStorage implements UserStorage {
    private int id;
    private final HashMap<Integer, User> userList = new HashMap<>();
    private final UserService userService;

    @Autowired
    public InMemoryUserStorage(UserService userService) {
        this.userService = userService;
    }

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
    public User getUser(int id) {
        if (userList.containsKey(id)) {
            return userList.get(id);
        } else {
            throw new NotFoundException("Такого пользователя не существует");
        }
    }

    @Override
    public User updateUser(User user) {
        if (userList.containsKey(user.getId())) {
            userList.put(user.getId(), user);
            return user;
        } else {
            throw new NotFoundException("Такого пользователя не существует");
        }
    }

    @Override
    public List<User> allUsers() {
        List<User> users = new ArrayList<>(userList.values());
        return users;
    }

    @Override
    public void deleteUser(int id) {
        userList.remove(id);
    }

    @Override
    public void addFriend(int firstUser, int secondUser) {
        if (userList.containsKey(firstUser) && userList.containsKey(secondUser)) {
            userService.addFriend(userList.get(firstUser), userList.get(secondUser));
        } else {
            throw new NotFoundException("Такого пользователя не существует");
        }
    }

    @Override
    public void removeFriend(int firstUser, int secondUser) {
        if (userList.containsKey(firstUser) && userList.containsKey(secondUser)) {
            userService.removeFriend(userList.get(firstUser), userList.get(secondUser));
        } else {
            throw new NotFoundException("Такого пользователя не существует");
        }

    }

    @Override
    public List<User> commonFriends(int firstUser, int secondUser) {
        List<User> friends = new ArrayList<>();
        for (Integer id : userService.commonFriends(userList.get(firstUser), userList.get(secondUser))) {
            friends.add(userList.get(id));
        }
        return friends;
    }

    @Override
    public List<User> getFriends(int id) {
        List<User> friends = new ArrayList<>();
        for (Integer num : userList.get(id).getFriendList()) {
            friends.add(userList.get(num));
        }
        return friends;
    }
}
