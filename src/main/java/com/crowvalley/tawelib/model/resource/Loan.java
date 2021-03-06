package com.crowvalley.tawelib.model.resource;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Comparator;

/**
 * Loan class for creating objects to store information about loans
 * to be persisted in a database.
 *
 * @author Dylan Rodgers
 */
@Entity
@Table
public class Loan {

    @Id
    @GeneratedValue
    private Long id;

    private Long copyId;

    private String borrowerUsername;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private LocalDateTime returnDate;

    public Loan(Long copyId, String borrowerUsername, LocalDateTime startDate, LocalDateTime endDate) {
        this.copyId = copyId;
        this.borrowerUsername = borrowerUsername;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Loan() {
    }

    public static Comparator<Loan> getComparator() {
        return (loan1, loan2) -> ComparisonChain.start()
                .compare(loan1.getReturnDate(), loan2.getReturnDate(), Ordering.natural().reverse().nullsFirst())
                .compare(loan1.getEndDate(), loan2.getEndDate(), Ordering.natural().reverse())
                .compare(loan1.getStartDate(), loan2.getStartDate(), Ordering.natural().reverse())
                .result();
    }

    public Long getId() {
        return id;
    }

    public Long getCopyId() {
        return copyId;
    }

    public String getBorrowerUsername() {
        return borrowerUsername;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }

    public enum Status {
        AVAILABLE("Available"),
        ON_LOAN("On loan"),
        ON_LOAN_TO_YOU("On loan to you");

        private final String toString;

        Status(String toString) {
            this.toString = toString;
        }

        @Override
        public String toString() {
            return toString;
        }
    }
}
