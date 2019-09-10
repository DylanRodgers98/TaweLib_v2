package com.crowvalley.tawelib.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

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

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
}
