package com.crowvalley.model;

import java.nio.file.Path;
import java.time.LocalDate;

/**
 * Models a librarian. Librarian extends User. Each Librarian has a employment date, and a staff number.
 * @author
 */
public class Librarian extends User{
    private LocalDate employmentDate;
    private int staffNum;

    /**
     * Creates a new librarian.
     * @param userName The username of the librarian.
     * @param firstName The first name of the librarian.
     * @param surName The surname of the librarian.
     * @param phoneNum The phone number of the librarian.
     * @param houseNum The house number of the librarian.
     * @param street The street of the librarian.
     * @param town The town of the librarian.
     * @param county The county of the librarian.
     * @param postcode The postcode of the librarian.
     * @param balance The balance of the librarian.
     * @param profilePath The profile path of the librarian.
     * @param employmentDate The employment date of the librarian.
     * @param staffNum The staff number of the librarian.
     */
    public Librarian(String userName, String firstName, String surName, String phoneNum,
                     String houseNum, String street, String town, String county, String postcode,
                     Float balance, Path profilePath, LocalDate employmentDate, int staffNum){
        super(userName, firstName, surName, phoneNum, houseNum, street, town, county, postcode, profilePath, balance);
        this.employmentDate = employmentDate;
        this.staffNum = staffNum;
    }

    public LocalDate getEmploymentDate() {
        return employmentDate;
    }

    public void setEmploymentDate(LocalDate employmentDate) {
        this.employmentDate = employmentDate;
    }

    public int getStaffNum() {
        return staffNum;
    }

    public void setStaffNum(int staffNum) {
        this.staffNum = staffNum;
    }
}
