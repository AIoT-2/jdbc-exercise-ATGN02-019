package com.nhnacademy.jdbc.club.repository.impl;

import com.nhnacademy.jdbc.club.domain.Club;
import com.nhnacademy.jdbc.club.repository.ClubRepository;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.Optional;

@Slf4j
public class ClubRepositoryImpl implements ClubRepository {

    @Override
    public int save(Connection connection, Club club) {
        String query = "INSERT INTO jdbc_club(club_id, club_name, club_created_at) VALUES(?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setString(1, club.getClubId());
            preparedStatement.setString(2, club.getClubName());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(club.getClubCreatedAt()));

            int result = preparedStatement.executeUpdate();
            log.debug("save: {}", result);
            return result;
        } catch (SQLException e) {
            log.error("{}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Club> findByClubId(Connection connection, String clubId) {
        String query = "SELECT club_name, club_created_at FROM jdbc_club WHERE club_id=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setString(1, clubId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Club club = new Club(clubId,
                        resultSet.getString("club_name"),
                        resultSet.getTimestamp("club_created_at").toLocalDateTime()
                );
                return Optional.of(club);
            }
        } catch (SQLException e) {
            log.error("{}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public int countByClubId(Connection connection, String clubId) {
        int count = 0;
        String query = "SELECT COUNT(club_id) FROM jdbc_club WHERE club_id=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setString(1, clubId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            log.error("{}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
        return count;
    }

    @Override
    public int update(Connection connection, Club club) {
        String query = "UPDATE jdbc_club SET club_name=?, club_created_at=? WHERE club_id=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setString(1, club.getClubName());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(club.getClubCreatedAt()));
            preparedStatement.setString(3, club.getClubId());

            int result = preparedStatement.executeUpdate();
            log.debug("update: {}", result);
            return result;
        } catch (SQLException e) {
            log.error("{}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteByClubId(Connection connection, String clubId) {
        String query = "DELETE FROM jdbc_club WHERE club_id=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setString(1, clubId);

            int result = preparedStatement.executeUpdate();
            log.debug("delete: {}", result);
            return result;
        } catch (SQLException e) {
            log.error("{}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
