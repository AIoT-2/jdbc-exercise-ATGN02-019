package com.nhnacademy.jdbc.util;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.sql.Connection;
import java.sql.SQLException;

@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class BasicConnectionPoolTest {

    private static BasicConnectionPool basicConnectionPool;

    private static Connection connection1;

    private static Connection connection2;

    private static Connection connection3;

    private static Connection connection4;

    private static Connection connection5;

    @BeforeAll
    static void setUp() {
        //todo#0 - jdbcUrl, username, password를 설정하세요
        basicConnectionPool = new BasicConnectionPool(
                com.mysql.cj.jdbc.Driver.class.getName(),
                "jdbc:mysql://220.67.216.14:13306/nhn_academy_219",
                "nhn_academy_219",
                "cP8zjqvd!",
                5);
    }

    @AfterAll
    static void connectionClose() throws SQLException {
        basicConnectionPool.destroy();
    }

    @Test
    @Order(0)
    @DisplayName("Driver not found Exception")
    void init() {
        Assertions.assertThrows(RuntimeException.class,
                () -> new BasicConnectionPool("org.mariadb.jdbc.Driver", "jdbcUrl", "userName", "password", 5)
        );
    }

    @Test
    @Order(1)
    @DisplayName("get connection")
    void getConnection() throws InterruptedException, SQLException {
        connection1 = basicConnectionPool.getConnection();
        connection2 = basicConnectionPool.getConnection();
        connection3 = basicConnectionPool.getConnection();
        Assertions.assertAll(
                () -> Assertions.assertTrue(connection1.isValid(1000)),
                () -> Assertions.assertTrue(connection2.isValid(1000)),
                () -> Assertions.assertTrue(connection3.isValid(1000)),
                () -> Assertions.assertEquals(3, basicConnectionPool.getUsedConnectionSize())
        );
    }

    @Disabled
    @Test
    @Order(2)
    @DisplayName("empty connection-pool")
    void getConnection_empty() throws InterruptedException, SQLException {
        connection4 = basicConnectionPool.getConnection();
        connection5 = basicConnectionPool.getConnection();
        Connection connection6 = basicConnectionPool.getConnection();

        Assertions.assertAll(
                () -> Assertions.assertEquals(5, basicConnectionPool.getUsedConnectionSize())
        );
    }

    @Test
    @Order(3)
    @DisplayName("release connection")
    void releaseConnection() {
        basicConnectionPool.releaseConnection(connection1);
        basicConnectionPool.releaseConnection(connection2);
        basicConnectionPool.releaseConnection(connection3);

        Assertions.assertEquals(0, basicConnectionPool.getUsedConnectionSize());
    }
}