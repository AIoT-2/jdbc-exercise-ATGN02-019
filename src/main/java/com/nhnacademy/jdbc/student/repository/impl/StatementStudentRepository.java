package com.nhnacademy.jdbc.student.repository.impl;

import com.nhnacademy.jdbc.student.domain.Student;
import com.nhnacademy.jdbc.student.repository.StudentRepository;
import com.nhnacademy.jdbc.util.DbUtils;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

@Slf4j
public class StatementStudentRepository implements StudentRepository {

    @Override
    public int save(Student student) {
        String query = String.format("INSERT INTO students(id, name, gender, age, created_at) VALUES('%s', '%s', '%s', %d, '%s')",
                student.getId(), student.getName(), student.getGender().toString(), student.getAge(), student.getCreatedAt().toString());

        try (Connection connection = DbUtils.getConnection();
             Statement statement = connection.createStatement()) {
            return statement.executeUpdate(query);
        } catch (SQLException e) {
            log.error("{}", e.getMessage(), e);
        }
        return 0;
    }

    @Override
    public Optional<Student> findById(String id) {
        String query = String.format("SELECT FROM students WHERE id='%s'", id);

        try (Connection connection = DbUtils.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            Student student = null;
            while (resultSet.next()) {
                student = new Student(
                        resultSet.getString("id"),
                        resultSet.getString("name"),
                        Student.Gender.valueOf(resultSet.getString("gender")),
                        resultSet.getInt("age"));
            }
            return Optional.ofNullable(student);
        } catch (SQLException e) {
            log.error("{}", e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public int update(Student student) {
        String query = String.format("UPDATE students SET name=%s WHERE id=%s",
                student.getName(), student.getId());

        try (Connection connection = DbUtils.getConnection();
             Statement statement = connection.createStatement()) {
            return statement.executeUpdate(query);
        } catch (SQLException e) {
            log.error("{}", e.getMessage(), e);
        }
        return 0;
    }

    @Override
    public int deleteById(String id) {
        String query = String.format("DELETE FROM students WHERE id=%s", id);

        try (Connection connection = DbUtils.getConnection();
             Statement statement = connection.createStatement()) {
            return statement.executeUpdate(query);
        } catch (SQLException e) {
            log.error("{}", e.getMessage(), e);
        }
        return 0;
    }

}
