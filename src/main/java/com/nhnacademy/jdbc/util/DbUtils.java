package com.nhnacademy.jdbc.util;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.time.Duration;

public final class DbUtils {

    public DbUtils() {
        throw new IllegalStateException("Utility class");
    }

    private static final DataSource DATASOURCE;

    static {
        BasicDataSource basicDataSource = new BasicDataSource();

        basicDataSource.setUrl("jdbc:mysql://220.67.216.14:13306/nhn_academy_219");
        basicDataSource.setUsername("nhn_academy_219");
        basicDataSource.setPassword("cP8zjqvd!");

        basicDataSource.setInitialSize(5);
        basicDataSource.setMaxTotal(5);
        basicDataSource.setMaxIdle(5);
        basicDataSource.setMinIdle(5);

        basicDataSource.setMaxWait(Duration.ofSeconds(2));
        basicDataSource.setValidationQuery("select 1");
        basicDataSource.setTestOnBorrow(true);
        DATASOURCE = basicDataSource;
    }

    public static DataSource getDataSource() {
        return DATASOURCE;
    }
}