package com.nhnacademy.jdbc.bank.exception;

public class AccountAlreadyExistException extends RuntimeException {

    /**
     * 이미 존재하는 계좌
     *
     * @param accountNumber
     */
    public AccountAlreadyExistException(long accountNumber) {
        super("account already exist accountNumber: " + accountNumber);
    }
}
