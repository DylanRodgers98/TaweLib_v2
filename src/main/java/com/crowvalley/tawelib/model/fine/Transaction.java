package com.crowvalley.tawelib.model.fine;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@MappedSuperclass
public class Transaction {

    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private BigDecimal amount;

    private LocalDateTime transactionDate;

    public Transaction(String username, BigDecimal amount, LocalDateTime transactionDate) {
        this.username = username;
        this.amount = amount;
        this.transactionDate = transactionDate;
    }

    public Transaction(){
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }
}
