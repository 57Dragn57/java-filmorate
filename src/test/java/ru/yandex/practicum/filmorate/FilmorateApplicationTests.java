package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestExecution;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
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
        User user = createUser(1, "mail@yandex.ru", "login12", "mail", LocalDate.of(2001, 12, 1));
        userService.addUser(user);
        Assertions.assertNotNull(userService.findAllUsers());
    }

    @Test
    public void testUpdateUser() {
        User user4 = createUser(5, "ira@yandex.ru", "ira23", "ira", LocalDate.of(2006, 7, 7));
        userService.addUser(user4);
        User user = createUser(1, "ivan@yandex.ru", "ivan33", "ivan", LocalDate.of(2008, 10, 4));
        userService.updateUser(user);
        Assertions.assertEquals(user, userService.getUser(1));
    }

    @Test
    public void testFindAllUsers() {
        User user = createUser(3, "misha@yandex.ru", "misha33", "misha", LocalDate.of(2008, 10, 4));
        userService.addUser(user);
        Assertions.assertNotNull(userService.findAllUsers());
    }

    @Test
    public void testDeleteUser() {
        User user = createUser(1, "dghfjkg@yandex.ru", "adqqq2", "ddfgrt", LocalDate.of(2001, 12, 1));
        User user1 = createUser(2, "sdre@yandex.ru", "adswsd", "vwedfg", LocalDate.of(2001, 12, 1));
        User user2 = createUser(3, "vtter@yandex.ru", "adsere2", "vwdfgrt", LocalDate.of(2001, 12, 1));
        User user3 = createUser(4, "ujghfj@yandex.ru", "artnyu", "vdfgert", LocalDate.of(2001, 12, 1));
        User user4 = createUser(5, "sere@yandex.ru", "asertyu2", "vxcvrt", LocalDate.of(2001, 12, 1));
        userService.addUser(user);
        userService.addUser(user1);
        userService.addUser(user2);
        userService.addUser(user3);
        userService.addUser(user4);


        int count = userService.findAllUsers().size();
        userService.deleteUser(5);
        Assertions.assertEquals(count - 1, userService.findAllUsers().size());
    }

    @Test
    public void testFriends() {
        User user = createUser(1, "qwe@yandex.ru", "adsf12", "vwert", LocalDate.of(2001, 12, 1));
        User user2 = createUser(2, "wer@yandex.ru", "sfdg12", "sdfgr", LocalDate.of(2001, 12, 1));
        User user3 = createUser(3, "ert@yandex.ru", "dgfh12", "vtware", LocalDate.of(2001, 12, 1));
        User user4 = createUser(4, "rty@yandex.ru", "fghj12", "tjhgf", LocalDate.of(2001, 12, 1));
        userService.addUser(user);
        userService.addUser(user2);
        userService.addUser(user3);
        userService.addUser(user4);
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

    private static User createUser(int id, String email, String login, String name, LocalDate birthday) {
        User user = new User();
        user.setId(id);
        user.setEmail(email);
        user.setLogin(login);
        user.setName(name);
        user.setBirthday(birthday);
        return user;
    }
}
