package com.nhnacademy.jdbc.util;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.time.Duration;

public final class DbUtils {

    private static final DataSource DATA_SOURCE;

    private DbUtils() {
        throw new IllegalStateException("Utility class");
    }

    static {
        String ip = "220.67.216.14";
        String port = "13306";
        String database = "nhn_academy_219";
        String username = "nhn_academy_219";
        String password = "cP8zjqvd!";

        BasicDataSource basicDataSource = new BasicDataSource();

        basicDataSource.setUrl(String.format("jdbc:mysql://%s:%s/%s", ip, port, database));
        basicDataSource.setUsername(username);
        basicDataSource.setPassword(password);

        basicDataSource.setInitialSize(5);
        basicDataSource.setMaxTotal(5);
        basicDataSource.setMaxIdle(5);
        basicDataSource.setMinIdle(5);

        basicDataSource.setMaxWait(Duration.ofSeconds(2));
        basicDataSource.setValidationQuery("select 1");
        basicDataSource.setTestOnBorrow(true);
        DATA_SOURCE = basicDataSource;
    }

    public static DataSource getDataSource() {
        return DATA_SOURCE;
    }
}
