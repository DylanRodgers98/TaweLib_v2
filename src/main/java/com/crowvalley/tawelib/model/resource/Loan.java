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
                .compare(loan1.getReturnDate(), loan2.getReturnDate(), Ordering.natural().nullsFirst())
                .compare(loan1.getEndDate(), loan2.getEndDate())
                .compare(loan1.getStartDate(), loan2.getStartDate())
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

    public void setCopyId(Long copyId) {
        this.copyId = copyId;
    }

    public void setBorrowerUsername(String borrowerUsername) {
        this.borrowerUsername = borrowerUsername;
    }

    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }

}
