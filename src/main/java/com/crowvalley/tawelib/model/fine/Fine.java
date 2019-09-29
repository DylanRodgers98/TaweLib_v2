package com.crowvalley.tawelib.model.fine;

import com.crowvalley.tawelib.model.user.User;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Fine class for creating objects to store information about fines
 * given to {@link User}s.
 *
 * @author Dylan Rodgers
 */
@Entity
@Table(name = "fine")
public class Fine {

    public static final Double BOOK_FINE_AMOUNT_PER_DAY = 2.00;

    public static final Double DVD_FINE_AMOUNT_PER_DAY = 2.00;

    public static final Double LAPTOP_FINE_AMOUNT_PER_DAY = 10.00;

    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private Long loanId;

    private Double amount;

    public Fine(String username, Long loanId, Double amount) {
        this.username = username;
        this.loanId = loanId;
        this.amount = amount;
    }

    public Fine() {
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Long getLoanId() {
        return loanId;
    }

    public Double getAmount() {
        return amount;
    }
}
