package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dao.FriendDbStorage;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

@RestController
@Slf4j
public class FriendController {
    private final FriendDbStorage friendStorage;

    @Autowired
    public FriendController(FriendDbStorage friendStorage) {
        this.friendStorage = friendStorage;
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    public void addFriend(@PathVariable int id, @PathVariable int friendId) {
        log.info("Пользователь {} добавил в друзья пользователя {}", id, friendId);
        friendStorage.addFriend(id, friendId);
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    public void removeFriend(@PathVariable int id, @PathVariable int friendId) {
        log.info("Пользователь {} удалил {} из друзей", id, friendId);
        friendStorage.removeFriend(id, friendId);
    }

    @GetMapping("/users/{id}/friends")
    public List<User> getFriends(@PathVariable int id) {
        log.info("Процесс получения списка друзей пользователем {}", id);
        return friendStorage.getFriends(id);
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public List<User> commonFriends(@PathVariable int id, @PathVariable int otherId) {
        log.info("Процесс получения общих друзей {} и {}", id, otherId);
        return friendStorage.commonFriends(id, otherId);
    }

}
