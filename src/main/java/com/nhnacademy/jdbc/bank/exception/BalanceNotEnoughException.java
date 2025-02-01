package com.nhnacademy.jdbc.bank.exception;

public class BalanceNotEnoughException extends RuntimeException {

    /**
     * 출금하기 위한 잔액 부족
     *
     * @param account_number
     */
    public BalanceNotEnoughException(long account_number) {
        super("balance not enough accountNumber: " + account_number);
    }
}
