package com.nhnacademy.jdbc.club.repository.impl;

import com.nhnacademy.jdbc.club.domain.ClubStudent;
import com.nhnacademy.jdbc.club.repository.ClubRegistrationRepository;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Slf4j
public class ClubRegistrationRepositoryImpl implements ClubRegistrationRepository {

    @Override
    public int save(Connection connection, String studentId, String clubId) {
        String query = "INSERT INTO jdbc_club_registrations(student_id, club_id) VALUES(?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setString(1, studentId);
            preparedStatement.setString(2, clubId);

            int result = preparedStatement.executeUpdate();
            log.debug("result: {}", result);
            return result;
        } catch (SQLException e) {
            log.error("{}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ClubStudent> findClubStudentsByStudentId(Connection connection, String studentId) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT students.name AS studentName, club.club_id AS clubId, club.club_name AS clubName ");
        query.append("FROM jdbc_club_registrations registrations ");
        query.append("INNER JOIN jdbc_students students ON registrations.student_id = students.id ");
        query.append("INNER JOIN jdbc_club club ON registrations.club_id = club.club_id ");
        query.append("WHERE registrations.student_id=?");

        try (PreparedStatement preparedStatement = connection.prepareStatement(query.toString())
        ) {
            preparedStatement.setString(1, studentId);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<ClubStudent> clubStudentList = new LinkedList<>();
            while (resultSet.next()) {
                String studentName = resultSet.getString("studentName");
                String clubId = resultSet.getString("clubId");
                String clubName = resultSet.getString("clubName");

                ClubStudent clubStudent = new ClubStudent(studentId, studentName, clubId, clubName);
                log.debug("{}", clubStudent);
                clubStudentList.add(clubStudent);
            }
            resultSet.close();
            return clubStudentList;
        } catch (SQLException e) {
            log.error("{}", e.getMessage(), e);
             throw new RuntimeException(e);
        }
        // return Collections.emptyList();
    }

    @Override
    public List<ClubStudent> findClubStudents(Connection connection) {
        //todo#21 - join
        return Collections.emptyList();
    }

    @Override
    public List<ClubStudent> findClubStudents_left_join(Connection connection) {
        //todo#22 - left join
        return Collections.emptyList();
    }

    @Override
    public List<ClubStudent> findClubStudents_right_join(Connection connection) {
        //todo#23 - right join
        return Collections.emptyList();
    }

    @Override
    public List<ClubStudent> findClubStudents_full_join(Connection connection) {
        //todo#24 - full join = left join union right join
        return Collections.emptyList();
    }

    @Override
    public List<ClubStudent> findClubStudents_left_excluding_join(Connection connection) {
        //todo#25 - left excluding join
        return Collections.emptyList();
    }

    @Override
    public List<ClubStudent> findClubStudents_right_excluding_join(Connection connection) {
        //todo#26 - right excluding join
        return Collections.emptyList();
    }

    @Override
    public List<ClubStudent> findClubStudents_outer_excluding_join(Connection connection) {
        //todo#27 - outher_excluding_join = left excluding join union right excluding join
        return Collections.emptyList();
    }

    @Override
    public int deleteByStudentIdAndClubId(Connection connection, String studentId, String clubId) {
        //todo#12 - 핵생 -> 클럽 탈퇴, executeUpdate() 결과를 반환
        return 0;
    }
}
