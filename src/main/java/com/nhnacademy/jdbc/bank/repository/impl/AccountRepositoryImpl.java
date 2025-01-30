package com.nhnacademy.jdbc.bank.repository.impl;

import com.nhnacademy.jdbc.bank.domain.Account;
import com.nhnacademy.jdbc.bank.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Slf4j
public class AccountRepositoryImpl implements AccountRepository {

    @Override
    public int save(Connection connection, Account account) {
        String query = "INSERT INTO jdbc_account(account_number, name, balance) VALUES(?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, account.getAccountNumber());
            preparedStatement.setString(2, account.getName());
            preparedStatement.setLong(3, account.getBalance());

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("{}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Account> findByAccountNumber(Connection connection, long accountNumber) {
        String query = "SELECT name, balance FROM jdbc_account WHERE account_number=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, accountNumber);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();

            Account account = null;
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                long balance = resultSet.getLong("balance");

                account = new Account(accountNumber, name, balance);
            }

            resultSet.close();
            return Optional.ofNullable(account);
        } catch (Exception e) {
            log.error("{}", e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public int countByAccountNumber(Connection connection, long accountNumber) {
        int count = 0;
        String query = "SELECT COUNT(account_number) FROM jdbc_account WHERE account_number=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, accountNumber);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();

            while (resultSet.next()) {
                 count = resultSet.getInt(1);
            }
            resultSet.close();
        } catch (SQLException e) {
            log.error("{}", e.getMessage(), e);
        }
        return count;
    }

    @Override
    public int deposit(Connection connection, long accountNumber, long amount) {
        String query = "UPDATE jdbc_account SET balance=balance+? WHERE account_number=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, amount);
            preparedStatement.setLong(2, accountNumber);

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("{}", e.getMessage(), e);
        }
        return 0;
    }

    @Override
    public int withdraw(Connection connection, long accountNumber, long amount) {
        String query = "UPDATE jdbc_account SET balance=balance-? WHERE account_number=? AND balance >= ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, amount);
            preparedStatement.setLong(2, accountNumber);
            preparedStatement.setLong(3, amount);

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("{}", e.getMessage(), e);
        }
        return 0;
    }

    @Override
    public int deleteByAccountNumber(Connection connection, long accountNumber) {
        String query = "DELETE FROM jdbc_account WHERE account_number=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, accountNumber);

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("{}", e.getMessage(), e);
        }
        return 0;
    }
}
