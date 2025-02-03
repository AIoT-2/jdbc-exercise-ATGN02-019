package com.nhnacademy.jdbc.student.repository.impl;

import com.nhnacademy.jdbc.common.Page;
import com.nhnacademy.jdbc.student.domain.Student;
import com.nhnacademy.jdbc.student.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class StudentRepositoryImpl implements StudentRepository {

    @Override
    public int save(Connection connection, Student student) {
        String sql = "insert into jdbc_students(id,name,gender,age) values(?,?,?,?)";

        try (
                PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setString(1, student.getId());
            statement.setString(2, student.getName());
            statement.setString(3, student.getGender().toString());
            statement.setInt(4, student.getAge());

            int result = statement.executeUpdate();
            log.debug("save:{}", result);
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Optional<Student> findById(Connection connection, String id) {
        String sql = "select * from jdbc_students where id=?";
        log.debug("findById:{}", sql);

        ResultSet rs = null;
        try (
                PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setString(1, id);
            rs = statement.executeQuery();
            if (rs.next()) {
                Student student = new Student(rs.getString("id"),
                        rs.getString("name"),
                        Student.Gender.valueOf(rs.getString("gender")),
                        rs.getInt("age"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                );
                return Optional.of(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return Optional.empty();
    }

    @Override
    public int update(Connection connection, Student student) {
        String sql = "update jdbc_students set name=?, gender=?, age=? where id=?";
        log.debug("update:{}", sql);

        try (
                PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            int index = 0;
            statement.setString(++index, student.getName());
            statement.setString(++index, student.getGender().toString());
            statement.setInt(++index, student.getAge());
            statement.setString(++index, student.getId());

            int result = statement.executeUpdate();
            log.debug("result:{}", result);
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteById(Connection connection, String id) {
        String sql = "delete from jdbc_students where id=?";

        try (
                PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setString(1, id);
            int result = statement.executeUpdate();
            log.debug("result:{}", result);
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteAll(Connection connection) {
        String sql = "delete from jdbc_students";

        try (
                PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            int result = statement.executeUpdate();
            log.debug("result:{}", result);
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long totalCount(Connection connection) {
        long totalCount = 0L;
        String query = "SELECT COUNT(id) FROM jdbc_students";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            if (resultSet.next()) {
                totalCount = resultSet.getLong(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return totalCount;
    }

    @Override
    public Page<Student> findAll(Connection connection, int page, int pageSize) {
        String query = "SELECT * FROM jdbc_students LIMIT ?, ?";
        int offset = (page - 1) * pageSize;
        int limit = pageSize;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setInt(1, offset);
            preparedStatement.setInt(2, limit);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Student> studentList = new ArrayList<>(pageSize);
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                Student.Gender gender = Student.Gender.valueOf(resultSet.getString("gender"));
                int age = resultSet.getInt("age");
                LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();

                Student student = new Student(id, name, gender, age, createdAt);
                log.info("{}", student);
                studentList.add(student);
            }
            resultSet.close();

            long total = 0L;
            if (!studentList.isEmpty()) {
                total = totalCount(connection);
            }
            return new Page<>(studentList, total);
        } catch (SQLException e) {
            log.error("{}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}