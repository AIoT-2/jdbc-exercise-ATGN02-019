package com.nhnacademy.jdbc.util;

import java.util.Objects;

public final class StringUtils {

    private StringUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean isNullOrEmpty(String s) {
        return Objects.isNull(s)
                || s.replace(" ", "").isBlank();
    }
}
