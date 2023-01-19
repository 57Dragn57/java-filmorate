package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.film.MPA;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;

import java.time.LocalDate;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmorateApplicationTests {
    private final UserDbStorage userStorage;
    private final FilmDbStorage filmStorage;

    @BeforeEach
    public void beforeEachUsers() {
        User user = createUser(1, "mail@yandex.ru", "login12", "mail", LocalDate.of(2001, 12, 1));
        User user1 = createUser(2, "igor@yandex.ru", "igor123", "igor", LocalDate.of(2010, 11, 5));
        User user2 = createUser(3, "misha@yandex.ru", "misha33", "misha", LocalDate.of(2008, 10, 4));
        User user3 = createUser(3, "masha@yandex.ru", "masha33", "masha", LocalDate.of(2007, 9, 6));
        User user4 = createUser(3, "ira@yandex.ru", "ira23", "ira", LocalDate.of(2006, 7, 7));
        userStorage.addUser(user);
        userStorage.addUser(user1);
        userStorage.addUser(user2);
        userStorage.addUser(user3);
        userStorage.addUser(user4);

    }

    @BeforeEach
    public void beforeEachFilms() {
        Film film = new Film(1, "qwert", "sdfsdfsdf", 120, LocalDate.of(2012, 3, 1));
        Film film1 = new Film(2, "rtyui", "sdfsgf", 145, LocalDate.of(2006, 6, 2));
        Film film2 = new Film(3, "fghjk", "jhjyhjhj", 143, LocalDate.of(2005, 5, 3));
        Film film3 = new Film(4, "asdsd", "werewrwer", 178, LocalDate.of(2008, 4, 4));
        Film film4 = new Film(5, "cvbbn", "scvbcvbcv", 145, LocalDate.of(2001, 7, 5));
        Film film5 = new Film(6, "tghcf", "nyuttnghfjn", 136, LocalDate.of(2002, 8, 6));
        MPA mpa = new MPA(4);
        film.setMpa(mpa);
        film1.setMpa(mpa);
        film2.setMpa(mpa);
        film3.setMpa(mpa);
        film4.setMpa(mpa);
        film5.setMpa(mpa);
        filmStorage.addFilm(film);
        filmStorage.addFilm(film1);
        filmStorage.addFilm(film2);
        filmStorage.addFilm(film3);
        filmStorage.addFilm(film4);
        filmStorage.addFilm(film5);

    }

    @Test
    public void testFindUser() {
        Assertions.assertEquals("login12", userStorage.getUser(1).getLogin());
    }

    @Test
    public void testUpdateUser() {
        User user = createUser(2, "ivan@yandex.ru", "ivan33", "ivan", LocalDate.of(2008, 10, 4));
        userStorage.updateUser(user);
        Assertions.assertEquals(user, userStorage.getUser(2));
    }

    @Test
    public void testFindAllUsers() {
        Assertions.assertNotNull(userStorage.allUsers());
    }

    @Test
    public void testDeleteUser() {
        int count = userStorage.allUsers().size();
        userStorage.deleteUser(4);
        Assertions.assertEquals(count - 1, userStorage.allUsers().size());
    }

    @Test
    public void testFriends() {
        userStorage.addFriend(1, 2);
        userStorage.addFriend(1, 3);
        Assertions.assertEquals(2, userStorage.getFriends(1).size());
        userStorage.addFriend(3, 2);
        Assertions.assertEquals(1, userStorage.commonFriends(1, 3).size());
        userStorage.removeFriend(1, 2);
        Assertions.assertEquals(1, userStorage.getFriends(1).size());
    }

    @Test
    public void testUpdateFilm() {
        Film film = new Film(1, "Liza", "ssssssssn", 111, LocalDate.of(2002, 8, 6));
        MPA mpa = new MPA(3);
        film.setMpa(mpa);
        film.setGenres(null);
        filmStorage.updateFilm(film);
        Assertions.assertEquals(film.getName(), filmStorage.getFilm(1).getName());
    }

    @Test
    public void testFindAllFilms() {
        int count = filmStorage.allFilms().size();
        filmStorage.deleteFilm(6);
        Assertions.assertEquals(count - 1, filmStorage.allFilms().size());
    }

    @Test
    public void testFindGenres() {
        Assertions.assertEquals(6, filmStorage.getGenres().size());
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
