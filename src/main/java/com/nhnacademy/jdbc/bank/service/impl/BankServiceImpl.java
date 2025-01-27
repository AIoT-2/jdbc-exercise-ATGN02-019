package com.nhnacademy.jdbc.bank.service.impl;

import com.nhnacademy.jdbc.bank.domain.Account;
import com.nhnacademy.jdbc.bank.repository.AccountRepository;
import com.nhnacademy.jdbc.bank.service.BankService;

import java.sql.Connection;

public class BankServiceImpl implements BankService {

    private final AccountRepository accountRepository;

    public BankServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account getAccount(Connection connection, long accountNumber) {
        //todo#11 계좌-조회

        return null;
    }

    @Override
    public void createAccount(Connection connection, Account account) {
        //todo#12 계좌-등록

    }

    @Override
    public boolean depositAccount(Connection connection, long accountNumber, long amount) {
        //todo#13 예금, 계좌가 존재하는지 체크 -> 예금실행 -> 성공 true, 실패 false;

        return false;
    }

    @Override
    public boolean withdrawAccount(Connection connection, long accountNumber, long amount) {
        //todo#14 출금, 계좌가 존재하는지 체크 ->  출금가능여부 체크 -> 출금실행, 성공 true, 실폐 false 반환

        return false;
    }

    @Override
    public void transferAmount(Connection connection, long accountNumberFrom, long accountNumberTo, long amount) {
        //todo#15 계좌 이체 accountNumberFrom -> accountNumberTo 으로 amount만큼 이체

    }

    @Override
    public boolean isExistAccount(Connection connection, long accountNumber) {
        //todo#16 Account가 존재하면 true , 존재하지 않다면 false

        return false;
    }

    @Override
    public void dropAccount(Connection connection, long accountNumber) {
        //todo#17 account 삭제

    }
}
