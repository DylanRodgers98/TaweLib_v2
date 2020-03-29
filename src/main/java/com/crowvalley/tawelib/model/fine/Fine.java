package com.crowvalley.tawelib.model.fine;

import com.crowvalley.tawelib.model.user.User;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
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

    public static final BigDecimal BOOK_FINE_AMOUNT_PER_DAY = new BigDecimal("2").setScale(2, RoundingMode.HALF_EVEN);

    public static final BigDecimal DVD_FINE_AMOUNT_PER_DAY = new BigDecimal("2").setScale(2, RoundingMode.HALF_EVEN);

    public static final BigDecimal LAPTOP_FINE_AMOUNT_PER_DAY = new BigDecimal("10").setScale(2, RoundingMode.HALF_EVEN);

    private Long loanId;

    public Fine(String username, Long loanId, BigDecimal amount, LocalDateTime fineDate) {
        super(username, amount, fineDate);
        this.loanId = loanId;
    }

    public Fine() {
    }

    public Long getLoanId() {
        return loanId;
    }

}
