package com.nhnacademy.jdbc.util;

import java.sql.Connection;
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

        }
        if (isNullOrEmpty(jdbcUrl)) {

        }
        if (isNullOrEmpty(username)) {

        }
        if (isNullOrEmpty(password)) {

        }

        this.driverClassName = driverClassName;
        this.jdbcUrl = jdbcUrl;
        this.username = username;
        this.password = password;
        this.maximumPoolSize = maximumPoolSize;

        connections = new LinkedList<>();

        checkDriver();
        initialize();
    }

    private void checkDriver() {
        //todo#1 driverClassName에 해당하는 class가 존재하는지 check합니다.
        //존재하지 않는다면 RuntimeException 예외처리.
        try {
            Class.forName(driverClassName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void initialize() {
        //todo#2 maximumPoolSize만큼 Connection 객체를 생성해서 Connection Pool에 등록합니다.
        for (int n = 0; n < maximumPoolSize; n++) {
            connections.add(DbUtils.getConnection());
        }
    }

    public Connection getConnection() throws InterruptedException {
        //todo#3 Connection Pool에 connection이 존재하면 반환하고 비어있다면 Connection Pool에 Connection이 존재할 때 까지 대기합니다.
        while (connections.isEmpty()) {
            wait();
        }

        notify();
        return connections.poll();
    }

    public void releaseConnection(Connection connection) {
        //todo#4 작업을 완료한 Connection 객체를 Connection Pool에 반납 합니다.

        // TODO: Multi-Thread 환경에서 동일한 객체를 참조하다가 한꺼번에 반납한다면?
        if (Objects.isNull(connection)) {
            throw new IllegalArgumentException("connection is Null!");
        }
    }

    public int getUsedConnectionSize() {
        //todo#5 현재 사용중인 Connection 객체 수를 반환합니다.
        return connections.size();
    }

    public void destroy() throws SQLException {
        //todo#6 Connection Pool에 등록된 Connection을 close 합니다.
        for (Connection connection : connections) {
            if (connection.isClosed()) continue;
            connection.close();
        }
    }
}
