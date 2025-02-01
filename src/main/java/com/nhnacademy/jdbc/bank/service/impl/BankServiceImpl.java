package com.nhnacademy.jdbc.bank.service.impl;

import com.nhnacademy.jdbc.bank.domain.Account;
import com.nhnacademy.jdbc.bank.exception.AccountAlreadyExistException;
import com.nhnacademy.jdbc.bank.exception.AccountNotFoundException;
import com.nhnacademy.jdbc.bank.exception.BalanceNotEnoughException;
import com.nhnacademy.jdbc.bank.repository.AccountRepository;
import com.nhnacademy.jdbc.bank.service.BankService;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.util.Optional;

@Slf4j
public class BankServiceImpl implements BankService {

    private final AccountRepository accountRepository;

    public BankServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void createAccount(Connection connection, Account account) {
        if (isExistAccount(connection, account.getAccountNumber())) {
            throw new AccountAlreadyExistException(account.getAccountNumber());
        }
        try {
            int result = accountRepository.save(connection, account);
            if (result < 1) {
                throw new RuntimeException(
                        String.format("failed to save Account: %d", account.getAccountNumber()));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Account getAccount(Connection connection, long accountNumber) {
        Optional<Account> optional = accountRepository.findByAccountNumber(connection, accountNumber);
        return optional.orElseThrow(() -> new AccountNotFoundException(accountNumber));
    }

    @Override
    public boolean isExistAccount(Connection connection, long accountNumber) {
        int count = accountRepository.countByAccountNumber(connection, accountNumber);
        return count > 0;
    }

    @Override
    public boolean depositAccount(Connection connection, long accountNumber, long amount) {
        if (!isExistAccount(connection, accountNumber)) {
            throw new AccountNotFoundException(accountNumber);
        }
        int result = accountRepository.deposit(connection, accountNumber, amount);
        return result > 0;
    }

    @Override
    public boolean withdrawAccount(Connection connection, long accountNumber, long amount) {
        if (!isExistAccount(connection, accountNumber)) {
            throw new AccountNotFoundException(accountNumber);
        }
        Account account = getAccount(connection, accountNumber);
        if (!account.isWithdraw(amount)) {
            throw new BalanceNotEnoughException(accountNumber);
        }
        int result = accountRepository.withdraw(connection, accountNumber, amount);
        return result > 0;
    }

    @Override
    public void transferAmount(Connection connection, long accountNumberFrom, long accountNumberTo, long amount) {
        // 존재 유무 체크
        if (!isExistAccount(connection, accountNumberFrom)) {
            throw new AccountNotFoundException(accountNumberFrom);
        }
        if (!isExistAccount(connection, accountNumberTo)) {
            throw new AccountNotFoundException(accountNumberTo);
        }

        // 실제 계좌 체크
        Account accountFrom = null;
        Account accountTo = null;
        try {
            accountFrom = getAccount(connection, accountNumberFrom);
            accountTo = getAccount(connection, accountNumberTo);
        } catch (Exception e) {
            throw new AccountNotFoundException(Long.parseLong(e.getMessage()));
        }

        // 잔액 체크
        if (!accountFrom.isWithdraw(amount)) {
            throw new BalanceNotEnoughException(accountNumberFrom);
        }

        // 실행
        /*withdrawAccount(connection, accountNumberFrom, amount);
        depositAccount(connection, accountNumberTo, amount);*/
        int result1 = accountRepository.withdraw(connection, accountNumberFrom, amount);
        if (result1 < 1) {
            throw new RuntimeException("fail - withdraw: " + accountNumberFrom);
        }

        int result2 = accountRepository.deposit(connection, accountNumberTo, amount);
        if (result2 < 1) {
            throw new RuntimeException("fail - deposit: " + accountNumberTo);
        }
    }

    @Override
    public void dropAccount(Connection connection, long accountNumber) {
        if (!isExistAccount(connection, accountNumber)) {
            throw new AccountNotFoundException(accountNumber);
        }
        int result = accountRepository.deleteByAccountNumber(connection, accountNumber);
        if (result < 1) {
            throw new RuntimeException(
                    String.format("failed to delete Account: %d", accountNumber));
        }
    }
}
