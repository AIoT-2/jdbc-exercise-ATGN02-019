package com.nhnacademy.jdbc.user.repository.impl;

import com.nhnacademy.jdbc.user.domain.User;
import com.nhnacademy.jdbc.user.repository.UserRepository;
import com.nhnacademy.jdbc.util.DbUtils;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

@Slf4j
public class StatementUserRepository implements UserRepository {

    @Override
    public int save(User user) {
        String query = String.format("INSERT INTO jdbc_users(user_id, user_name, user_password) VALUES('%s', '%s', '%s')",
                user.getUserId(), user.getUserName(), user.getUserPassword());

        try (Connection connection = DbUtils.getConnection();
             Statement statement = connection.createStatement()
        ) {
            return statement.executeUpdate(query);
        } catch (SQLException e) {
            log.error("{}", e.getMessage(), e);
        }
        return 0;
    }

    @Override
    public Optional<User> findById(String userId) {
        String query = String.format("SELECT * FROM jdbc_users WHERE user_id='%s'", userId);

        try (Connection connection = DbUtils.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query);
        ) {
            User user = null;
            while (resultSet.next()) {
                String userName = resultSet.getString("user_name");
                String userPassword = resultSet.getString("user_password");

                user = new User(userId, userName, userPassword);
            }
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            log.error("{}", e.getMessage(), e);
        } catch (Exception e) {
            log.error("{}", e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByUserIdAndUserPassword(String userId, String userPassword) {
        String query = String.format("SELECT * FROM jdbc_users WHERE user_id='%s' AND user_password='%s'",
                userId, userPassword);

        try (Connection connection = DbUtils.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query);
        ) {
            User user = null;
            while (resultSet.next()) {
                String userName = resultSet.getString("user_name");

                user = new User(userId, userName, userPassword);
            }
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            log.error("{}", e.getMessage(), e);
        } catch (Exception e) {
            log.error("{}", e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public int updateUserPasswordByUserId(String userId, String userPassword) {
        String query = String.format("UPDATE jdbc_users SET user_password='%s' WHERE user_id='%s'",
                userPassword, userId);

        try (Connection connection = DbUtils.getConnection();
             Statement statement = connection.createStatement();
        ) {
            return statement.executeUpdate(query);
        } catch (SQLException e) {
            log.error("{}", e.getMessage(), e);
        }
        return 0;
    }

    @Override
    public int deleteByUserId(String userId) {
        String query = String.format("DELETE FROM jdbc_users WHERE user_id='%s'", userId);

        try (Connection connection = DbUtils.getConnection();
             Statement statement = connection.createStatement();
        ) {
            return statement.executeUpdate(query);
        } catch (SQLException e) {
            log.error("{}", e.getMessage(), e);
        }
        return 0;
    }
}
