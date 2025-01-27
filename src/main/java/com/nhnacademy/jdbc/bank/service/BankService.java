package com.nhnacademy.jdbc.bank.service;

import com.nhnacademy.jdbc.bank.domain.Account;

import java.sql.Connection;

public interface BankService {

    // 계좌생성
    void createAccount(Connection connection, Account account);

    // 계좌조회
    Account getAccount(Connection connection, long accountNumber);

    // 계좌가 존재하면 true 존재하지 않다면 false
    boolean isExistAccount(Connection connection, long accountNumber);

    // 예금
    boolean depositAccount(Connection connection, long accountNumber, long amount);

    // 출금
    boolean withdrawAccount(Connection connection, long accountNumber, long amount);

    // 송금(이체)
    void transferAmount(Connection connection, long accountNumberFrom, long accountNumberTo, long amount);

    // 계좌삭제
    void dropAccount(Connection connection, long accountNumber);
}
