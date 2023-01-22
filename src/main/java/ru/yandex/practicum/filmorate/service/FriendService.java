package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.FriendDbStorage;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

@Service
public class FriendService {

    private final FriendDbStorage friendStorage;

    @Autowired
    public FriendService(FriendDbStorage friendStorage) {
        this.friendStorage = friendStorage;
    }

    public void addFriend(int id, int friendId) {
        friendStorage.addFriend(id, friendId);
    }

    public void removeFriend(int id, int friendId) {
        friendStorage.removeFriend(id, friendId);
    }

    public List<User> getFriends(int id) {
        return friendStorage.getFriends(id);
    }

    public List<User> commonFriends(int id, int otherId) {
        return friendStorage.commonFriends(id, otherId);
    }
}
