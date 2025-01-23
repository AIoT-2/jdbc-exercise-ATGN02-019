package com.nhnacademy.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DbUtils {

    private DbUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static Connection getConnection() {
        String url = "jdbc:mysql://220.67.216.14:13306/nhn_academy_219";
        String user = "nhn_academy_219";
        String password = "cP8zjqvd!";

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }
}
