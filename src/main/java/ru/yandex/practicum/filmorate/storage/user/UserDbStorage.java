package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.controller.FilmAndUserValidator;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validation.NotFoundException;
import ru.yandex.practicum.filmorate.validation.ValidationException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@Qualifier("userDbStorage")
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User addUser(User user) {
        if (FilmAndUserValidator.validateUser(user)) {
            long userId = simpleSave(user);
            user.setId((int) userId);
            return user;
        } else {
            throw new ValidationException("Пользователь не прошел валидацию");
        }
    }

    private long simpleSave(User user) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
        return simpleJdbcInsert.executeAndReturnKey(user.toMap()).longValue();
    }

    @Override
    public User updateUser(User user) {
        SqlRowSet userRows = jdbcTemplate.queryForRowSet("select * from USERS where id = ?", user.getId());
        if (userRows.next()) {
            jdbcTemplate.update("update USERS set NAME = ?, LOGIN = ?, EMAIL = ?, BIRTHDAY = ? where id = ?", user.getName(), user.getLogin(), user.getEmail(), user.getBirthday(), user.getId());
            return user;
        } else {
            throw new NotFoundException("Такого пользователя не существует");
        }
    }

    @Override
    public List<User> findAllUsers() {
        String sql = "select * from USERS";

        return jdbcTemplate.query(sql, (rs, rowNum) -> makeUser(rs));
    }

    private User makeUser(ResultSet rs) throws SQLException {
        return new User(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("login"),
                rs.getDate("birthday").toLocalDate()
        );
    }

    private User makeUser(SqlRowSet srs) {
        return new User(
                srs.getInt("id"),
                srs.getString("name"),
                srs.getString("email"),
                srs.getString("login"),
                srs.getDate("birthday").toLocalDate()
        );
    }

    @Override
    public void deleteUser(int id) {
        jdbcTemplate.update("delete from USERS where ID = ?", id);
    }

    @Override
    public User getUser(int id) {
        SqlRowSet userRows = jdbcTemplate.queryForRowSet("select * from USERS where ID = ?", id);
        if (userRows.next()) {
            return makeUser(userRows);
        } else {
            throw new NotFoundException("Такого пользователя не существует");
        }
    }


}
