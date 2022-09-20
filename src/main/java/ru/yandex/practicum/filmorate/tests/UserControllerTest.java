package ru.yandex.practicum.filmorate.tests;

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

    @Test
    public void filmName() {

        Assert.assertTrue(FilmAndUserValidator.validateFilm(createFilm(1, "Форсаж 1",
                "fghgffhfghfgh", 120, LocalDate.of(2001, 04, 12))));

        Assert.assertFalse(FilmAndUserValidator.validateFilm(createFilm(2, " ",
                "fghgffhfghfgh", 120, LocalDate.of(2001, 04, 12))));
    }

    @Test
    public void filmDescription() {

        Assert.assertTrue(FilmAndUserValidator.validateFilm(createFilm(3, "Форсаж 2",
                "igoiyuyuioyuioyuiouioyuiyouiyouiyouiyoiyuo" +
                        "uiyouiyoyuioyuiuiyouioyuiyoiouyuiyouiyouioyui" +
                        "oyyuiouiyoyuiouiyoyuioyuioyuiouiyoyuioyuioyuioui" +
                        "youiyouiyouiyoyuiouioyyuiouyioyuiuyiouyioyuiouioyuiyoyuioyuioyuio", // 200 символов
                120, LocalDate.of(2001, 04, 12))));

        Assert.assertFalse(FilmAndUserValidator.validateFilm(createFilm(4, "Форсаж 2",
                "igoiyuyuioyuioyuiouioyuiyouiyouiyouiyoiyuo" +
                        "uiyouiyoyuioyuiuiyouioyuiyoiouyuiyouiyouioyui" +
                        "oyyuiouiyoyuiouiyoyuioyuioyuiouiyoyuioyuioyuioui" +
                        "youiyouiyouiyoyuiouioyyuiouyioyuiuyiouyioyuiouioyuiyoyuioyвапвапвапвuioyuio", // >200 символов
                120, LocalDate.of(2001, 04, 12))));
    }

    @Test
    public void filmRelease() {

        Assert.assertFalse(FilmAndUserValidator.validateFilm(createFilm(5, "Форсаж 5",
                "fghgffhfghfgh", 120, LocalDate.of(1895, 12, 27))));

        Assert.assertTrue(FilmAndUserValidator.validateFilm(createFilm(6, "Форсаж 5",
                "fghgffhfghfgh", 120, LocalDate.of(1895, 12, 28))));
    }

    @Test
    public void filmDuration() {
        Assert.assertTrue(FilmAndUserValidator.validateFilm(createFilm(7, "Форсаж 7",
                "fghgffhfghfgh", 1, LocalDate.of(1995, 12, 28))));

        Assert.assertFalse(FilmAndUserValidator.validateFilm(createFilm(8, "Форсаж 8",
                "fghgffhfghfgh", -1, LocalDate.of(1995, 12, 28))));
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

    private Film createFilm(int id, String name, String description, int duration, LocalDate releaseDate) {
        Film film = new Film();
        film.setId(id);
        film.setName(name);
        film.setDescription(description);
        film.setDuration(duration);
        film.setReleaseDate(releaseDate);
        return film;
    }
}
