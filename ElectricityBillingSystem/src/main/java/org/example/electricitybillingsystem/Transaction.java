package org.example.electricitybillingsystem;

public class Transaction {
    public int transactionId;
    public double amount;

    public Transaction(int transactionId, double amount) {
        this.transactionId = transactionId;
        this.amount = amount;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public double getAmount() {
        return amount;
    }
}
