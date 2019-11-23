package com.crowvalley.tawelib.model.fine;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Transaction {

    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private Double amount;

    public Transaction(String username, Double amount) {
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

    public Double getAmount() {
        return amount;
    }
}
