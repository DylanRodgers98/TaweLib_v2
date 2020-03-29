package com.crowvalley.tawelib.model.fine;

import com.crowvalley.tawelib.model.user.User;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Payment class for creating objects to store information about payments
 * made by {@link User}s when paying off any {@link Fine}s
 *
 * @author Dylan Rodgers
 */
@Entity
@Table
public class Payment extends Transaction {

    public Payment(String username, BigDecimal amount, LocalDateTime paymentDate) {
        super(username, amount, paymentDate);
    }

    public Payment() {
    }

}
