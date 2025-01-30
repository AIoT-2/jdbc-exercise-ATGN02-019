package com.nhnacademy.jdbc.bank.service.impl;

import com.nhnacademy.jdbc.bank.domain.Account;
import com.nhnacademy.jdbc.bank.exception.AccountAlreadyExistException;
import com.nhnacademy.jdbc.bank.exception.AccountNotFoundException;
import com.nhnacademy.jdbc.bank.repository.AccountRepository;
import com.nhnacademy.jdbc.bank.service.BankService;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

@Slf4j
public class BankServiceImpl implements BankService {

    private final AccountRepository accountRepository;

    public BankServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void createAccount(Connection connection, Account account) {
        try {
            if (isExistAccount(connection, account.getAccountNumber())) {
                throw new AccountAlreadyExistException(account.getAccountNumber());
            }
            accountRepository.save(connection, account);
        } catch (Exception e) {
            log.warn("{}", e.getMessage(), e);
        }
    }

    @Override
    public Account getAccount(Connection connection, long accountNumber) {
        Optional<Account> optional = accountRepository.findByAccountNumber(connection, accountNumber);
        return optional.orElse(null);
    }

    @Override
    public boolean isExistAccount(Connection connection, long accountNumber) {
        int result = accountRepository.countByAccountNumber(connection, accountNumber);
        return result != 0;
    }

    @Override
    public boolean depositAccount(Connection connection, long accountNumber, long amount) {
        //todo#13 예금, 계좌가 존재하는지 체크 -> 예금실행 -> 성공 true, 실패 false;
        try {
            if (!isExistAccount(connection, accountNumber)) {
                throw new AccountNotFoundException(accountNumber);
            }
            connection.setAutoCommit(false);
        } catch (Exception e) {
            log.warn("{}", e.getMessage(), e);
        }
        return false;
    }

    @Override
    public boolean withdrawAccount(Connection connection, long accountNumber, long amount) {
        //todo#14 출금, 계좌가 존재하는지 체크 ->  출금가능여부 체크 -> 출금실행, 성공 true, 실패 false 반환
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {

        }
        return false;
    }

    @Override
    public void transferAmount(Connection connection, long accountNumberFrom, long accountNumberTo, long amount) {
        //todo#15 계좌 이체 accountNumberFrom -> accountNumberTo 으로 amount 만큼 이체

    }

    @Override
    public void dropAccount(Connection connection, long accountNumber) {
        try {
            if (!isExistAccount(connection, accountNumber)) {
                throw new AccountNotFoundException(accountNumber);
            }
        } catch (Exception e) {
            log.warn("{}", e.getMessage(), e);
            return;
        }
        accountRepository.deleteByAccountNumber(connection, accountNumber);
    }
}
