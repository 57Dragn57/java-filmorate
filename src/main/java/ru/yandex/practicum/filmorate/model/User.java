package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Data
public class User {

    private int id;
    @Email
    @NotEmpty
    private String email;
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]{3,12}$",
            message = "Логин должен быть длиной от 3 до 12 символов без специальных знаков!")
    private String login;
    private String name;
    @NotNull
    @PastOrPresent
    private LocalDate birthday;

    public User(int id, String name, String email, String login, LocalDate birthday) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.login = login;
        this.birthday = birthday;
    }

    public User() {
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