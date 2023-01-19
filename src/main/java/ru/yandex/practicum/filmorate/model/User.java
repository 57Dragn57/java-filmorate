package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@Data
public class User {
    private HashSet<Integer> friendList = new HashSet<>();
    private HashSet<Integer> subscribers = new HashSet<>();

    public User(int id, String name, String email, String login, LocalDate birthday) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.login = login;
        this.birthday = birthday;
    }

    public User() {
    }

    ;

    private int id;
    @Email
    private String email;
    @NotBlank
    @NotNull
    private String login;
    private String name;
    private LocalDate birthday;

    public String getName() {
        if (name == null || name.isBlank()) {
            name = login;
            return name;
        } else {
            return name;
        }
    }

    public Map<String, Object> toMap() {
        Map<String, Object> values = new HashMap<>();
        values.put("name", name);
        values.put("login", login);
        values.put("email", email);
        values.put("birthday", birthday);
        return values;
    }
}