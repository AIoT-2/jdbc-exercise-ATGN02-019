package com.nhnacademy.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

import static com.nhnacademy.jdbc.util.StringUtils.isNullOrEmpty;

public class BasicConnectionPool {

    private final String driverClassName;

    private final String jdbcUrl;

    private final String username;

    private final String password;

    private final int maximumPoolSize;

    private final Queue<Connection> connections;

    public BasicConnectionPool(String driverClassName, String jdbcUrl, String username, String password, int maximumPoolSize) {
        if (isNullOrEmpty(driverClassName)) {
            throw new IllegalArgumentException("driverClassName is Null!");
        }
        if (isNullOrEmpty(jdbcUrl)) {
            throw new IllegalArgumentException("jdbcUrl is Null!");
        }
        if (isNullOrEmpty(username)) {
            throw new IllegalArgumentException("username is Null!");
        }
        if (isNullOrEmpty(password)) {
            throw new IllegalArgumentException("password is Null!");
        }
        if (maximumPoolSize < 1) {
            throw new IllegalArgumentException("maximumPoolSize is out of Range!");
        }
        this.driverClassName = driverClassName;
        this.jdbcUrl = jdbcUrl;
        this.username = username;
        this.password = password;
        this.maximumPoolSize = maximumPoolSize;
        this.connections = new LinkedList<>();
        checkDriver();
        initialize();
    }

    private void checkDriver() {
        try {
            // com.mysql.cj.jdbc.Driver
            Class.forName(driverClassName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void initialize() {
        for (int n = 0; n < maximumPoolSize; n++) {
            try {
                connections.add(DriverManager.getConnection(jdbcUrl, username, password));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Connection getConnection() throws InterruptedException {
        synchronized (this) {
            while (connections.isEmpty()) {
                wait();
            }
            // if (connections.peek().isClosed()) {}
            return connections.poll();
        }
    }

    public void releaseConnection(Connection connection) {
        // Multi-Thread 환경에서 동일한 객체를 참조하다가 한꺼번에 반납한다면?
        if (Objects.isNull(connection)) {
            throw new IllegalArgumentException("connection is Null!");
        }
        synchronized (this) {
            connections.add(connection);
            notifyAll();
        }
    }

    public int getUsedConnectionSize() {
        synchronized (this) {
            return maximumPoolSize - connections.size();
        }
    }

    public void destroy() throws SQLException {
        for (Connection connection : connections) {
            if (connection.isClosed()) continue;
            connection.close();
        }
    }
}
