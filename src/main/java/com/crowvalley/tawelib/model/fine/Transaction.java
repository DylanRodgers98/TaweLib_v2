package com.crowvalley.tawelib.model.fine;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.math.BigDecimal;

@MappedSuperclass
public class Transaction {

    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private BigDecimal amount;

    public Transaction(String username, BigDecimal amount) {
        this.username = username;
        this.amount = amount;
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
}
