package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.FriendStorage;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final FriendStorage friendStorage;
    private final UserService userService;

    public void addFriend(int id, int friendId) {
        userService.getUser(id);
        userService.getUser(friendId);
        friendStorage.addFriend(id, friendId);
    }

    public void removeFriend(int id, int friendId) {
        userService.getUser(id);
        userService.getUser(friendId);
        friendStorage.removeFriend(id, friendId);
    }

    public List<User> getFriends(int id) {
        return friendStorage.getFriends(id);
    }

    public List<User> commonFriends(int id, int otherId) {
        return friendStorage.commonFriends(id, otherId);
    }
}
