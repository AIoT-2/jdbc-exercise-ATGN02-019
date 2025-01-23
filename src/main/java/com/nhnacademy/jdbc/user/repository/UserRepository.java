package com.nhnacademy.jdbc.user.repository;

import com.nhnacademy.jdbc.user.domain.User;

import java.util.Optional;

public interface UserRepository {

    int save(User user);

    Optional<User> findById(String userId);

    Optional<User> findByUserIdAndUserPassword(String userId, String userPassword);

    int updateUserPasswordByUserId(String userId, String userPassword);

    int deleteByUserId(String userId);
}
