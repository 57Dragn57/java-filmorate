package ru.yandex.practicum.filmorate.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validation.NotFoundException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class FriendDbStorage {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FriendDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addFriend(int firstUser, int secondUser) {
        SqlRowSet subRows = jdbcTemplate.queryForRowSet("select * from SUBSCRIBERS where USER_ID = ? and SUB_ID = ?", firstUser, secondUser);
        SqlRowSet rowsFirstUser = jdbcTemplate.queryForRowSet("select * from USERS where ID = ?", firstUser);
        SqlRowSet rowsSecondUser = jdbcTemplate.queryForRowSet("select * from USERS where ID = ?", secondUser);

        if (rowsFirstUser.next() && rowsSecondUser.next()) {
            if (subRows.next()) {
                jdbcTemplate.update("insert into FRIENDS (user_id, friend_id) values (?, ?)", firstUser, secondUser);
                jdbcTemplate.update("insert into FRIENDS (user_id, friend_id) values (?, ?)", secondUser, firstUser);
                jdbcTemplate.update("delete from SUBSCRIBERS where USER_ID = ? and SUB_ID = ?", firstUser, secondUser);
                jdbcTemplate.update("delete from SUBSCRIBERS where USER_ID = ? and SUB_ID = ?", secondUser, firstUser);
            } else {
                jdbcTemplate.update("insert into SUBSCRIBERS (user_id, sub_id) values (?, ?)", secondUser, firstUser);
                jdbcTemplate.update("insert into FRIENDS (user_id, friend_id) values (?, ?)", firstUser, secondUser);
            }
        } else {
            throw new NotFoundException("Такого пользователя не существует");
        }
    }

    public void removeFriend(int firstUser, int secondUser) {
        SqlRowSet friendRows = jdbcTemplate.queryForRowSet("select * from FRIENDS where USER_ID = ? and FRIEND_ID = ?", secondUser, firstUser);
        SqlRowSet rowsFirstUser = jdbcTemplate.queryForRowSet("select * from USERS where ID = ?", firstUser);
        SqlRowSet rowsSecondUser = jdbcTemplate.queryForRowSet("select * from USERS where ID = ?", secondUser);

        if (rowsFirstUser.next() && rowsSecondUser.next()) {
            if (friendRows.next()) {
                jdbcTemplate.update("delete from FRIENDS where USER_ID = ? and FRIEND_ID = ?", firstUser, secondUser);
                jdbcTemplate.update("insert into SUBSCRIBERS (user_id, sub_id) values (?, ?)", firstUser, secondUser);
            } else {
                jdbcTemplate.update("delete from FRIENDS where USER_ID = ? and FRIEND_ID = ?", firstUser, secondUser);
                jdbcTemplate.update("delete from FRIENDS where USER_ID = ? and FRIEND_ID = ?", secondUser, firstUser);
                jdbcTemplate.update("delete from SUBSCRIBERS where USER_ID = ? and SUB_ID = ?", firstUser, secondUser);
                jdbcTemplate.update("delete from SUBSCRIBERS where USER_ID = ? and SUB_ID = ?", secondUser, firstUser);
            }
        } else {
            throw new NotFoundException("Такого пользователя не существует");
        }
    }

    public List<User> commonFriends(int firstUser, int secondUser) {
        String sql = "select ID, NAME, EMAIL, LOGIN, BIRTHDAY " +
                "from ( " +
                "select * from FRIENDS where USER_ID = " + firstUser +
                ") as f1 " +
                "join ( " +
                "select * from FRIENDS where USER_ID = " + secondUser +
                ") as f2 on f1.FRIEND_ID = f2.FRIEND_ID " +
                "join USERS as u on f1.FRIEND_ID = u.ID";

        return jdbcTemplate.query(sql, (rs, rowNum) -> makeUser(rs));
    }

    public List<User> getFriends(int id) {
        String sql = "select * from FRIENDS as f left join USERS as u on f.FRIEND_ID = u.ID where f.USER_ID = " + id;

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
}
