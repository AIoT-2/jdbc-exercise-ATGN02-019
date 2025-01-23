package com.nhnacademy.jdbc.student.repository.impl;

import com.nhnacademy.jdbc.student.domain.Student;
import com.nhnacademy.jdbc.student.repository.StudentRepository;
import com.nhnacademy.jdbc.util.DbUtils;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
public class PreparedStatementStudentRepository implements StudentRepository {

    @Override
    public int save(Student student) {
        String query = "INSERT INTO jdbc_students(id, name, gender, age, created_at) VALUES(?, ?, ?, ?, ?)";

        try (Connection connection = DbUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setString(1, student.getId());
            preparedStatement.setString(2, student.getName());
            preparedStatement.setString(3, student.getGender().toString());
            preparedStatement.setInt(4, student.getAge());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(student.getCreatedAt()));

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("{}", e.getMessage(), e);
        }
        return 0;
    }

    @Override
    public Optional<Student> findById(String id) {
        String query = "SELECT * FROM jdbc_students WHERE id=?";

        try (Connection connection = DbUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setString(1, id);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();

            Student student = null;
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                Student.Gender gender = Student.Gender.valueOf(resultSet.getString("gender"));
                int age = resultSet.getInt("age");
                LocalDateTime localDateTime = resultSet.getTimestamp("created_at").toLocalDateTime();

                student = new Student(id, name, gender, age, localDateTime);
            }

            resultSet.close();
            return Optional.ofNullable(student);
        } catch (SQLException e) {
            log.error("{}", e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public int update(Student student) {
        String query = "UPDATE jdbc_students SET name=?, gender=?, age=? WHERE id=?";

        try (Connection connection = DbUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setString(1, student.getName());
            preparedStatement.setString(2, student.getGender().toString());
            preparedStatement.setInt(3, student.getAge());
            preparedStatement.setString(4, student.getId());

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("{}", e.getMessage(), e);
        }
        return 0;
    }

    @Override
    public int deleteById(String id) {
        String query = "DELETE FROM jdbc_students WHERE id=?";

        try (Connection connection = DbUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setString(1, id);

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("{}", e.getMessage(), e);
        }
        return 0;
    }
}
