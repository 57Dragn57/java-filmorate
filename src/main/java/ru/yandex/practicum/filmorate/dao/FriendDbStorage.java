package ru.yandex.practicum.filmorate.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class FriendDbStorage implements FriendStorage {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FriendDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addFriend(int userId, int friendId) {
        String sqlQuery = "merge into FRIENDS (USER_ID, FRIEND_ID) values (?, ?)";
        jdbcTemplate.update(sqlQuery, userId, friendId);
    }

    @Override
    public void removeFriend(int userId, int friendId) {
        String sqlQuery = "delete from FRIENDS where USER_ID = ? and FRIEND_ID = ?";
        jdbcTemplate.update(sqlQuery, userId, friendId);
    }

    @Override
    public List<User> commonFriends(int firstUser, int secondUser) {
        String sql = "select * from USERS u, FRIENDS f, FRIENDS o where u.ID = f.FRIEND_ID AND u.ID = o.FRIEND_ID AND f.USER_ID = ? AND o.USER_ID = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> makeUser(rs), firstUser, secondUser);
    }

    @Override
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
