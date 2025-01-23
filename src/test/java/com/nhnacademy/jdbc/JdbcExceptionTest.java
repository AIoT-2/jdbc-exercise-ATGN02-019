package com.nhnacademy.jdbc;

import com.nhnacademy.jdbc.util.DbUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Slf4j
class JdbcExceptionTest {

    private static Connection connection;

    @BeforeAll
    static void setUp() {
        log.info("connection open()");
        connection = DbUtils.getConnection();
    }

    @Test
    @DisplayName("sqlExceptionTest")
    void insert_throw_sqlException() {

        String sql = "insert into jdbc_students (student_id,student_name,gender,age) values(100,'마르코','M','39')";

        SQLException sqlException = Assertions.assertThrows(SQLException.class, () -> {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        });

        log.info("errorCode: {}", sqlException.getErrorCode());
        log.info("message: {}", sqlException.getMessage());
        log.info("sqlState: {}", sqlException.getSQLState());
    }

    @AfterAll
    static void release() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
