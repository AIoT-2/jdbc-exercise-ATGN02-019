package com.nhnacademy.jdbc.bank.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AccountTest {

    private Account account;

    @BeforeEach
    void setUp() {
        account = new Account(1L, "nhn아카데미", 10_0000L);
    }

    @Test
    void getAccountNumber() {
        Assertions.assertEquals(account.getAccountNumber(), 1L);
    }

    @Test
    void getName() {
        Assertions.assertEquals(account.getName(), "nhn아카데미");
    }

    @Test
    void getBalance() {
        Assertions.assertEquals(account.getBalance(), 10_0000L);
    }

    @Test
    void setAccountNumber() {
        account.setAccountNumber(2L);
        Assertions.assertEquals(account.getAccountNumber(), 2L);
    }

    @Test
    void isWithdraw() {
        //출금가능 여부 테스트
        Assertions.assertAll(
                () -> Assertions.assertTrue(account.isWithdraw(1_000L)),
                () -> Assertions.assertFalse(account.isWithdraw(20_0000L))
        );
    }

}