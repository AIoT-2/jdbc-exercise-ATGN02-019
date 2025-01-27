package com.nhnacademy.jdbc.bank.exception;

public class AccountAlreadyExistException extends RuntimeException {

    public AccountAlreadyExistException(long accountNumber) {
        super("account already exist accountNumber: " + accountNumber);
    }
}
