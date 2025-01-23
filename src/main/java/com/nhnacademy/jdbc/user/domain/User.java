package com.nhnacademy.jdbc.user.domain;

import java.util.Objects;

import static com.nhnacademy.jdbc.util.StringUtils.isNullOrEmpty;

public class User {

    private final String userId;

    private final String userName;

    private final String userPassword;

    public User(String userId, String userName, String userPassword) {
        if (isNullOrEmpty(userId)) {
            throw new IllegalArgumentException("userId is Null!");
        }
        if (isNullOrEmpty(userName)) {
            throw new IllegalArgumentException("userName is Null!");
        }
        if (isNullOrEmpty(userPassword)) {
            throw new IllegalArgumentException("userPassword is Null!");
        }
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId)
                && Objects.equals(userName, user.userName)
                && Objects.equals(userPassword, user.userPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userName, userPassword);
    }
}
