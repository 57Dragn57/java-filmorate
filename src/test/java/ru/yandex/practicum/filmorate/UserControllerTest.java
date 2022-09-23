package ru.yandex.practicum.filmorate;

import org.junit.Assert;
import org.junit.Test;
import ru.yandex.practicum.filmorate.controller.FilmAndUserValidator;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

public class UserControllerTest {

    @Test
    public void userEmail() {

        Assert.assertFalse(FilmAndUserValidator.validateUser(createUser(1, "practicum.yandex.ru",
                "practic13", "Georg",
                LocalDate.of(2000, 01, 01))));

        Assert.assertFalse(FilmAndUserValidator.validateUser(createUser(2, " ",
                "practic13", "Georg",
                LocalDate.of(2000, 01, 01))));

        Assert.assertTrue(FilmAndUserValidator.validateUser(createUser(3, "practicum@yandex.ru",
                "practic13", "Georg",
                LocalDate.of(2000, 01, 01))));
    }

    @Test
    public void userLogin() {

        Assert.assertFalse(FilmAndUserValidator.validateUser(createUser(4, "practicum1@yandex.ru",
                " ", "Georg",
                LocalDate.of(2000, 01, 01))));

        Assert.assertFalse(FilmAndUserValidator.validateUser(createUser(5, "practicum1@yandex.ru",
                "practic 13", "Georg",
                LocalDate.of(2000, 01, 01))));

        Assert.assertTrue(FilmAndUserValidator.validateUser(createUser(6, "practicum1@yandex.ru",
                "practic11", "Georg",
                LocalDate.of(2000, 01, 01))));
    }

    @Test
    public void userBirthday() {

        Assert.assertFalse(FilmAndUserValidator.validateUser(createUser(7, "practicum2@yandex.ru",
                "practic", "Georg",
                LocalDate.of(2023, 01, 01))));

        Assert.assertTrue(FilmAndUserValidator.validateUser(createUser(8, "practicum2@yandex.ru",
                "practic", "Georg",
                LocalDate.of(2022, 01, 01))));

    }

    private User createUser(int id, String email, String login, String name, LocalDate birthday) {
        User user = new User();
        user.setId(id);
        user.setEmail(email);
        user.setLogin(login);
        user.setName(name);
        user.setBirthday(birthday);
        return user;
    }
}
