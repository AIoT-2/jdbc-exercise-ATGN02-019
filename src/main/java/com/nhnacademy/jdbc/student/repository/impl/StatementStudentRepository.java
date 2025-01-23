package com.nhnacademy.jdbc.student.repository.impl;

import com.nhnacademy.jdbc.student.domain.Student;
import com.nhnacademy.jdbc.student.repository.StudentRepository;
import com.nhnacademy.jdbc.util.DbUtils;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
public class StatementStudentRepository implements StudentRepository {

    @Override
    public int save(Student student) {
        String query = String.format("INSERT INTO jdbc_students(id, name, gender, age, created_at) VALUES('%s', '%s', '%s', %d, '%s')",
                student.getId(), student.getName(), student.getGender().toString(), student.getAge(), student.getCreatedAtToString());

        try (Connection connection = DbUtils.getConnection();
             Statement statement = connection.createStatement()
        ) {
            return statement.executeUpdate(query);
        } catch (SQLException e) {
            log.error("{}", e.getMessage(), e);
        }
        return 0;
    }

    @Override
    public Optional<Student> findById(String id) {
        String query = String.format("SELECT * FROM jdbc_students WHERE id='%s'", id);

        try (Connection connection = DbUtils.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)
        ) {
            Student student = null;
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                Student.Gender gender = Student.Gender.valueOf(resultSet.getString("gender"));
                int age = resultSet.getInt("age");
                LocalDateTime localDateTime = resultSet.getTimestamp("created_at").toLocalDateTime();

                student = new Student(id, name, gender, age, localDateTime);
            }
            return Optional.ofNullable(student);
        } catch (SQLException e) {
            log.error("{}", e.getMessage(), e);
        } catch (Exception e) {
            log.error("{}", e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public int update(Student student) {
        String query = String.format("UPDATE jdbc_students SET name='%s', gender='%s', age=%d WHERE id='%s'",
                student.getName(), student.getGender().toString(), student.getAge(), student.getId());

        try (Connection connection = DbUtils.getConnection();
             Statement statement = connection.createStatement()
        ) {
            return statement.executeUpdate(query);
        } catch (SQLException e) {
            log.error("{}", e.getMessage(), e);
        }
        return 0;
    }

    @Override
    public int deleteById(String id) {
        String query = String.format("DELETE FROM jdbc_students WHERE id='%s'", id);

        try (Connection connection = DbUtils.getConnection();
             Statement statement = connection.createStatement()
        ) {
            return statement.executeUpdate(query);
        } catch (SQLException e) {
            log.error("{}", e.getMessage(), e);
        }
        return 0;
    }
}
