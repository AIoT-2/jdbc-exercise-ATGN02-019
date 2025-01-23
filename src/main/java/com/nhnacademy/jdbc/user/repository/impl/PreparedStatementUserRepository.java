package com.nhnacademy.jdbc.user.repository.impl;

import com.nhnacademy.jdbc.user.domain.User;
import com.nhnacademy.jdbc.user.repository.UserRepository;
import com.nhnacademy.jdbc.util.DbUtils;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Slf4j
public class PreparedStatementUserRepository implements UserRepository {

    @Override
    public int save(User user) {
        String query = "INSERT INTO jdbc_users(user_id, user_name, user_password) VALUES(?, ?, ?)";

        try (Connection connection = DbUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setString(1, user.getUserId());
            preparedStatement.setString(2, user.getUserName());
            preparedStatement.setString(3, user.getUserPassword());

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("{}", e.getMessage(), e);
        }
        return 0;
    }

    @Override
    public Optional<User> findById(String userId) {
        String query = "SELECT * FROM jdbc_users WHERE user_id=?";

        try (Connection connection = DbUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setString(1, userId);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();

            User user = null;
            while (resultSet.next()) {
                String userName = resultSet.getString("user_name");
                String userPassword = resultSet.getString("user_password");

                user = new User(userId, userName, userPassword);
            }

            resultSet.close();
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
        String query = "SELECT * FROM jdbc_users WHERE user_id=? AND user_password=?";

        try (Connection connection = DbUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setString(1, userId);
            preparedStatement.setString(2, userPassword);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();

            User user = null;
            while (resultSet.next()) {
                String userName = resultSet.getString("user_name");

                user = new User(userId, userName, userPassword);
            }

            resultSet.close();
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
        String query = "UPDATE jdbc_users SET user_password=? WHERE user_id=?";

        try (Connection connection = DbUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setString(1, userPassword);
            preparedStatement.setString(2, userId);

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("{}", e.getMessage(), e);
        }
        return 0;
    }

    @Override
    public int deleteByUserId(String userId) {
        String query = "DELETE FROM jdbc_users WHERE user_id=?";

        try (Connection connection = DbUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setString(1, userId);

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("{}", e.getMessage(), e);
        }
        return 0;
    }
}
