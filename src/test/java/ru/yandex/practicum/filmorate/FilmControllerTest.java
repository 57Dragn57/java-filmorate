package ru.yandex.practicum.filmorate;

import org.junit.Assert;
import org.junit.Test;
import ru.yandex.practicum.filmorate.controller.FilmAndUserValidator;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

public class FilmControllerTest {

    @Test
    public void filmName() {

        Assert.assertTrue(FilmAndUserValidator.validateFilm(createFilm(1, "Ôîðñàæ 1",
                "fghgffhfghfgh", 120, LocalDate.of(2001, 04, 12))));

        Assert.assertFalse(FilmAndUserValidator.validateFilm(createFilm(2, " ",
                "fghgffhfghfgh", 120, LocalDate.of(2001, 04, 12))));
    }

    @Test
    public void filmDescription() {

        Assert.assertTrue(FilmAndUserValidator.validateFilm(createFilm(3, "Ôîðñàæ 2",
                "igoiyuyuioyuioyuiouioyuiyouiyouiyouiyoiyuo" +
                        "uiyouiyoyuioyuiuiyouioyuiyoiouyuiyouiyouioyui" +
                        "oyyuiouiyoyuiouiyoyuioyuioyuiouiyoyuioyuioyuioui" +
                        "youiyouiyouiyoyuiouioyyuiouyioyuiuyiouyioyuiouioyuiyoyuioyuioyuio", // 200 ñèìâîëîâ
                120, LocalDate.of(2001, 04, 12))));

        Assert.assertFalse(FilmAndUserValidator.validateFilm(createFilm(4, "Ôîðñàæ 2",
                "igoiyuyuioyuioyuiouioyuiyouiyouiyouiyoiyuo" +
                        "uiyouiyoyuioyuiuiyouioyuiyoiouyuiyouiyouioyui" +
                        "oyyuiouiyoyuiouiyoyuioyuioyuiouiyoyuioyuioyuioui" +
                        "youiyouiyouiyoyuiouioyyuiouyioyuiuyiouyioyuiouioyuiyoyuioyâàïâàïâàïâuioyuio", // >200 ñèìâîëîâ
                120, LocalDate.of(2001, 04, 12))));
    }

    @Test
    public void filmRelease() {

        Assert.assertFalse(FilmAndUserValidator.validateFilm(createFilm(5, "Ôîðñàæ 5",
                "fghgffhfghfgh", 120, LocalDate.of(1895, 12, 27))));

        Assert.assertTrue(FilmAndUserValidator.validateFilm(createFilm(6, "Ôîðñàæ 5",
                "fghgffhfghfgh", 120, LocalDate.of(1895, 12, 28))));
    }

    @Test
    public void filmDuration() {
        Assert.assertTrue(FilmAndUserValidator.validateFilm(createFilm(7, "Ôîðñàæ 7",
                "fghgffhfghfgh", 1, LocalDate.of(1995, 12, 28))));

        Assert.assertFalse(FilmAndUserValidator.validateFilm(createFilm(8, "Ôîðñàæ 8",
                "fghgffhfghfgh", -1, LocalDate.of(1995, 12, 28))));
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