package com.nhnacademy.jdbc.bank.domain;

import java.util.Objects;

import static com.nhnacademy.jdbc.util.StringUtils.isNullOrEmpty;

public final class Account {

    // 계좌번호, 편의를 위해서 1,2,3,4.... 형식으로 사용합니다.
    private long accountNumber;

    // 이름
    private String name;

    // 잔고
    private long balance;

    public Account(long accountNumber, String name, long balance) {
        if (accountNumber < 0) {
            throw new IllegalArgumentException("accountNumber is out of Range!");
        }
        if (isNullOrEmpty(name)) {
            throw new IllegalArgumentException("name is Null or Empty!");
        }
        if (balance < 0) {
            throw new IllegalArgumentException("balance is out of Range!");
        }
        this.accountNumber = accountNumber;
        this.name = name;
        this.balance = balance;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public boolean isWithdraw(long amount) {
        return balance - amount >= 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return accountNumber == account.accountNumber
                && balance == account.balance
                && Objects.equals(name, account.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, name, balance);
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountNumber=" + accountNumber +
                ", name='" + name + '\'' +
                ", balance=" + balance +
                '}';
    }
}
