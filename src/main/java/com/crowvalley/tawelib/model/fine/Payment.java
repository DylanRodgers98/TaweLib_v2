package com.crowvalley.tawelib.model.fine;

import com.crowvalley.tawelib.model.user.User;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Payment class for creating objects to store information about payments
 * made by {@link User}s when paying off any {@link Fine}s
 *
 * @author Dylan Rodgers
 */
@Entity
@Table(name = "payment")
public class Payment extends Transaction {

    public Payment(String username, Double amount) {
        super(username, amount);
    }

    public Payment() {
    }

}
