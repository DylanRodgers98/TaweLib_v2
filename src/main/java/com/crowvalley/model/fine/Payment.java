package com.crowvalley.model.fine;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private Double amount;

    public Payment(String username, Double amount) {
        this.username = username;
        this.amount = amount;
    }

    public Payment() {
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
