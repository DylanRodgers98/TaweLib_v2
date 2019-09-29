package com.crowvalley.tawelib.model.user;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Date;

/**
 * Librarian class for creating objects to store information about librarian
 * users to be persisted in a database.
 *
 * @author Dylan Rodgers
 */
@Entity
@Table(name = "librarian")
public class Librarian extends User{

    private Long staffNum;

    private Date employmentDate;

    /**
     * Creates a Librarian using individually passed in {@link Address} fields
     */
    public Librarian(String username, String firstName, String surname, String phoneNum,
                     String houseNum, String street, String town, String county, String postcode,
                     String profileImagePath, Date employmentDate, Long staffNum){
        super(username, firstName, surname, phoneNum, houseNum, street, town, county, postcode, profileImagePath);
        this.employmentDate = employmentDate;
        this.staffNum = staffNum;
    }

    /**
     * Creates a Librarian using a passed in {@link Address} object
     */
    public Librarian(String username, String firstName, String surname, String phoneNum,
                     Address address, String profileImagePath, Date employmentDate, Long staffNum){
        super(username, firstName, surname, phoneNum, address, profileImagePath);
        this.employmentDate = employmentDate;
        this.staffNum = staffNum;
    }

    /**
     * Creates a Librarian using a passed in {@link User} object, theoretically
     * "promoting" a {@link User} to a Librarian.
     */
    public Librarian(User user, Date employmentDate, Long staffNum) {
        this(user.getUsername(), user.getFirstName(), user.getSurname(), user.getPhoneNum(),
                user.getAddress(), user.getProfileImagePath(), employmentDate, staffNum);
    }

    public Librarian() {
        super();
    }

    public Date getEmploymentDate() {
        return employmentDate;
    }

    public void setEmploymentDate(Date employmentDate) {
        this.employmentDate = employmentDate;
    }

    public Long getStaffNum() {
        return staffNum;
    }

    public void setStaffNum(Long staffNum) {
        this.staffNum = staffNum;
    }
}
