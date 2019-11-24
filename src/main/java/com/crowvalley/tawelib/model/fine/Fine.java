package com.crowvalley.tawelib.model.fine;

import com.crowvalley.tawelib.model.user.User;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Fine class for creating objects to store information about fines
 * given to {@link User}s.
 *
 * @author Dylan Rodgers
 */
@Entity
@Table(name = "fine")
public class Fine extends Transaction {

    public static final Double BOOK_FINE_AMOUNT_PER_DAY = 2.00;

    public static final Double DVD_FINE_AMOUNT_PER_DAY = 2.00;

    public static final Double LAPTOP_FINE_AMOUNT_PER_DAY = 10.00;

    private Long loanId;

    public Fine(String username, Long loanId, Double amount) {
        super(username, amount);
        this.loanId = loanId;
    }

    public Fine() {
    }

    public Long getLoanId() {
        return loanId;
    }

}
