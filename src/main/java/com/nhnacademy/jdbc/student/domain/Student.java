package com.nhnacademy.jdbc.student.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Student {

    public enum Gender {
        M, F
    }

    private final String id;

    private final String name;

    private final Gender gender;

    private final Integer age;

    private final LocalDateTime createdAt;

    public Student(String id, String name, Gender gender, int age) {
        this(id, name, gender, age, LocalDateTime.now());
    }

    public Student(String id, String name, Gender gender, int age, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.createdAt = createdAt;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Student that = (Student) o;
        return Objects.equals(id, that.id)
                && Objects.equals(name, that.name)
                && gender == that.gender
                && Objects.equals(age, that.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, gender, age);
    }
}
