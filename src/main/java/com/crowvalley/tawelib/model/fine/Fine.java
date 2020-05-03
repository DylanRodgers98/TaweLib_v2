package com.crowvalley.tawelib.model.fine;

import com.crowvalley.tawelib.model.resource.Loan;
import com.crowvalley.tawelib.model.user.User;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Fine class for creating objects to store information about fines
 * given to {@link User}s.
 *
 * @author Dylan Rodgers
 */
@Entity
@Table
public class Fine extends Transaction {

    @OneToOne
    private Loan loan;

    public Fine(String username, Loan loan, BigDecimal amount, LocalDateTime fineDate) {
        super(username, amount, fineDate);
        this.loan = loan;
    }

    public Fine() {
    }

    public Loan getLoan() {
        return loan;
    }

}
