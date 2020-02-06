package com.crowvalley.tawelib.model.resource;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

/**
 * Loan class for creating objects to store information about loans
 * to be persisted in a database.
 *
 * @author Dylan Rodgers
 */
@Entity
@Table(name = "loan")
public class Loan {

    @Id
    @GeneratedValue
    private Long id;

    private Long copyId;

    private String borrowerUsername;

    private Date startDate;

    private Date endDate;

    private Date returnDate;

    public Loan(Long copyId, String borrowerUsername, Date startDate, Date endDate) {
        this.copyId = copyId;
        this.borrowerUsername = borrowerUsername;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Loan() {
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

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setCopyId(Long copyId) {
        this.copyId = copyId;
    }

    public void setBorrowerUsername(String borrowerUsername) {
        this.borrowerUsername = borrowerUsername;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
}
