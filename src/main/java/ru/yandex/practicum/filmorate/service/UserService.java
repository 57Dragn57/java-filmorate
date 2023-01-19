package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    public void addFriend(User firstUser, User secondUser) {
        if (firstUser.getSubscribers().contains(secondUser)) {
            firstUser.getSubscribers().remove(secondUser.getId());
            firstUser.getSubscribers().add(secondUser.getId());
        } else {
            firstUser.getFriendList().add(secondUser.getId());
            secondUser.getSubscribers().add(firstUser.getId());
        }
    }

    public void removeFriend(User firstUser, User secondUser) {
        firstUser.getFriendList().remove(secondUser.getId());
        secondUser.getFriendList().remove(firstUser.getId());
    }

    public List<Integer> commonFriends(User firstUser, User secondUser) {
        List<Integer> common = new ArrayList<>();
        for (Integer fId : firstUser.getFriendList()) {
            for (Integer sId : secondUser.getFriendList()) {
                if (fId == sId) {
                    common.add(sId);
                }
            }
        }
        return common;
    }
}
