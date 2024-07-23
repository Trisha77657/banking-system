package com.bank.model;
public class Account {
    private int account_no;
    private int customer_id;
    private double balance;

    public Account(int accountNo, int customerId, double balance) {
        this.account_no = accountNo;
        this.customer_id = customerId;
        this.balance = balance;
    }

    public int getAccountNo() {
        return account_no;
    }

    public int getCustomerId() {
        return customer_id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}