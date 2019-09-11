package com.crowvalley.tawelib.model.user;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table(name = "librarian")
public class Librarian extends User{

    private Long staffNum;

    private Date employmentDate;

    /**
     * Creates a new librarian.
     * @param username The username of the librarian.
     * @param firstName The first name of the librarian.
     * @param surname The surname of the librarian.
     * @param phoneNum The phone number of the librarian.
     * @param houseNum The house number of the librarian.
     * @param street The street of the librarian.
     * @param town The town of the librarian.
     * @param county The county of the librarian.
     * @param postcode The postcode of the librarian.
     * @param profileImagePath The profile path of the librarian.
     * @param employmentDate The employment date of the librarian.
     * @param staffNum The staff number of the librarian.
     */
    public Librarian(String username, String firstName, String surname, String phoneNum,
                     String houseNum, String street, String town, String county, String postcode,
                     String profileImagePath, Date employmentDate, Long staffNum){
        super(username, firstName, surname, phoneNum, houseNum, street, town, county, postcode, profileImagePath);
        this.employmentDate = employmentDate;
        this.staffNum = staffNum;
    }

    public Librarian(String username, String firstName, String surname, String phoneNum,
                     Address address, String profileImagePath, Date employmentDate, Long staffNum){
        super(username, firstName, surname, phoneNum, address, profileImagePath);
        this.employmentDate = employmentDate;
        this.staffNum = staffNum;
    }

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
