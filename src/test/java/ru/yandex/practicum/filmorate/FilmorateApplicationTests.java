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
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.FriendService;
import ru.yandex.practicum.filmorate.service.GenreService;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.film.Genre;
import ru.yandex.practicum.filmorate.storage.film.Mpa;

import java.time.LocalDate;
import java.util.LinkedHashSet;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmorateApplicationTests {
    private final UserService userService;
    private final FilmService filmService;
    private final FriendService friendService;
    private final GenreService genreService;

    @BeforeEach
    public void beforeEachUsers() {
        User user = createUser(1, "mail@yandex.ru", "login12", "mail", LocalDate.of(2001, 12, 1));
        User user1 = createUser(2, "igor@yandex.ru", "igor123", "igor", LocalDate.of(2010, 11, 5));
        User user2 = createUser(3, "misha@yandex.ru", "misha33", "misha", LocalDate.of(2008, 10, 4));
        User user3 = createUser(4, "masha@yandex.ru", "masha33", "masha", LocalDate.of(2007, 9, 6));
        User user4 = createUser(5, "ira@yandex.ru", "ira23", "ira", LocalDate.of(2006, 7, 7));
        userService.addUser(user);
        userService.addUser(user1);
        userService.addUser(user2);
        userService.addUser(user3);
        userService.addUser(user4);

    }

    @BeforeEach
    public void beforeEachFilms() {
        Film film = new Film(1, "qwert", "sdfsdfsdf", 120, LocalDate.of(2012, 3, 1));
        Film film1 = new Film(2, "rtyui", "sdfsgf", 145, LocalDate.of(2006, 6, 2));
        Film film2 = new Film(3, "fghjk", "jhjyhjhj", 143, LocalDate.of(2005, 5, 3));
        Film film3 = new Film(4, "asdsd", "werewrwer", 178, LocalDate.of(2008, 4, 4));
        Film film4 = new Film(5, "cvbbn", "scvbcvbcv", 145, LocalDate.of(2001, 7, 5));
        Film film5 = new Film(6, "tghcf", "nyuttnghfjn", 136, LocalDate.of(2002, 8, 6));
        Mpa mpa = new Mpa(4);
        film.setMpa(mpa);
        film1.setMpa(mpa);
        film2.setMpa(mpa);
        film3.setMpa(mpa);
        film4.setMpa(mpa);
        film5.setMpa(mpa);
        filmService.addFilm(film);
        filmService.addFilm(film1);
        filmService.addFilm(film2);
        filmService.addFilm(film3);
        filmService.addFilm(film4);
        filmService.addFilm(film5);

    }

    @Test
    public void testFindUser() {
        Assertions.assertEquals("login12", userService.getUser(1).getLogin());
    }

    @Test
    public void testUpdateUser() {
        User user = createUser(2, "ivan@yandex.ru", "ivan33", "ivan", LocalDate.of(2008, 10, 4));
        userService.updateUser(user);
        Assertions.assertEquals(user, userService.getUser(2));
    }

    @Test
    public void testFindAllUsers() {
        Assertions.assertNotNull(userService.findAllUsers());
    }

    @Test
    public void testDeleteUser() {
        int count = userService.findAllUsers().size();
        userService.deleteUser(4);
        Assertions.assertEquals(count - 1, userService.findAllUsers().size());
    }

    @Test
    public void testFriends() {
        friendService.addFriend(1, 2);
        friendService.addFriend(1, 3);
        Assertions.assertEquals(2, friendService.getFriends(1).size());
        friendService.addFriend(3, 2);
        Assertions.assertEquals(1, friendService.commonFriends(1, 3).size());
        friendService.removeFriend(1, 2);
        Assertions.assertEquals(1, friendService.getFriends(1).size());
    }

    @Test
    public void testUpdateFilm() {
        Film film = new Film(1, "Liza", "ssssssssn", 111, LocalDate.of(2002, 8, 6));
        Mpa mpa = new Mpa(3);
        LinkedHashSet<Genre> genres = new LinkedHashSet<>();
        genres.add(new Genre(2));
        genres.add(new Genre(3));
        film.setMpa(mpa);
        film.setGenres(genres);
        filmService.updateFilm(film);
        Assertions.assertEquals(film.getName(), filmService.getFilm(1).getName());
    }

    @Test
    public void testFindAllFilms() {
        int count = filmService.findAllFilms().size();
        filmService.deleteFilm(6);
        Assertions.assertEquals(count - 1, filmService.findAllFilms().size());
    }

    @Test
    public void testFindGenres() {
        Assertions.assertEquals(6, genreService.getGenres().size());
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
