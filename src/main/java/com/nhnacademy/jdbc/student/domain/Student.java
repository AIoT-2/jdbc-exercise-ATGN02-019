package com.nhnacademy.jdbc.student.domain;

import java.time.LocalDateTime;
import java.util.Objects;

import static com.nhnacademy.jdbc.util.StringUtils.isNullOrEmpty;

public class Student {

    public enum Gender {
        M, F
    }

    private final String id;

    private final String name;

    private final Gender gender;

    private final Integer age;

    private final LocalDateTime createdAt;

    public Student(String id, String name, Gender gender, Integer age) {
        if (isNullOrEmpty(id)) {
            throw new IllegalArgumentException("id is Null or Empty!");
        }
        if (isNullOrEmpty(name)) {
            throw new IllegalArgumentException("name is Null or Empty!");
        }
        if (Objects.isNull(gender)) {
            throw new IllegalArgumentException("gender is Null!");
        }
        if (age < 0) {
            throw new IllegalArgumentException("age is out of Range!");
        }
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.createdAt = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Gender getGender() {
        return gender;
    }

    public Integer getAge() {
        return age;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
