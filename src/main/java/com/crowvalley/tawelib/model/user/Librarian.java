package com.crowvalley.tawelib.model.user;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Librarian class for creating objects to store information about librarian
 * users to be persisted in a database.
 *
 * @author Dylan Rodgers
 */
@Entity
@Table
public class Librarian extends User {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "staffnum", referencedColumnName = "staffnum")
    private StaffNumber staffNum;

    private LocalDate employmentDate;

    /**
     * Creates a Librarian using individually passed in {@link Address} fields
     */
    public Librarian(String username, String firstName, String surname, String phoneNum,
                     String houseNum, String street, String town, String county, String postcode,
                     String profileImagePath, LocalDate employmentDate){
        super(username, firstName, surname, phoneNum, houseNum, street, town, county, postcode, profileImagePath);
        this.employmentDate = employmentDate;
        staffNum = new StaffNumber();
    }

    /**
     * Creates a Librarian using a passed in {@link Address} object
     */
    public Librarian(String username, String firstName, String surname, String phoneNum,
                     Address address, String profileImagePath, LocalDate employmentDate){
        super(username, firstName, surname, phoneNum, address, profileImagePath);
        this.employmentDate = employmentDate;
        staffNum = new StaffNumber();
    }

    public Librarian() {
        super();
    }

    public LocalDate getEmploymentDate() {
        return employmentDate;
    }

    public void setEmploymentDate(LocalDate employmentDate) {
        this.employmentDate = employmentDate;
    }

    public Long getStaffNum() {
        return staffNum.getStaffNum();
    }

    @Override
    public String toString() {
        return "Username: " + getUsername() + " (Staff Number: " + staffNum + ")";
    }
}
