package com.nhnacademy.jdbc.bank.exception;

public class AccountNotFoundException extends RuntimeException {

    /**
     * 존재하지 않는 계좌
     * 
     * @param accountNumber
     */
    public AccountNotFoundException(long accountNumber) {
        super("Account Not Found accountNumber: " + accountNumber);
    }
}
