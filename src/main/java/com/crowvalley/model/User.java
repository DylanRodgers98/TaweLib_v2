package com.crowvalley.model;

import com.crowvalley.model.resource.Copy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy =  InheritanceType.TABLE_PER_CLASS)
public class User {

    @Id
    private String username;

    private String firstName;

    private String surname;

    private String phoneNum;

    private String houseNum;

    private String street;

    private String town;

    private String county;

    private String postcode;

    private Float balance;

    private String profileImagePath;

    @OneToMany
    private List<Copy> copiesBorrowing = new ArrayList<>();

    /**
     * Creates a new user.
     * @param username The username of the user.
     * @param firstName The first name of the user.
     * @param surname The surname of the user.
     * @param phoneNum The phone number of the user.
     * @param houseNum The house number of the user.
     * @param street The street of the user.
     * @param town The town of the user.
     * @param county The county of the user.
     * @param postcode The postcode of the user.
     * @param balance The balance of the user.
     * @param profileImagePath The profile path of the user.
     */
    public User(String username, String firstName, String surname, String phoneNum, String houseNum,
                String street, String town, String county, String postcode, String profileImagePath, Float balance){
        this.username = username;
        this.firstName = firstName;
        this.surname = surname;
        this.phoneNum = phoneNum;
        this.houseNum = houseNum;
        this.street = street;
        this.town = town;
        this.county = county;
        this.postcode = postcode;
        this.profileImagePath = profileImagePath;
        this.balance = balance;
    }

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getHouseNum() {
        return houseNum;
    }

    public void setHouseNum(String houseNum) {
        this.houseNum = houseNum;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public Float getBalance() {
        return balance;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }

    public String getProfileImagePath() {
        return profileImagePath;
    }

    public void setProfileImagePath(String profileImagePath) {
        this.profileImagePath = profileImagePath;
    }

    public List<Copy> getCopiesBorrowing() {
        return copiesBorrowing;
    }

    public void setCopiesBorrowing(List<Copy> copiesBorrowing) {
        this.copiesBorrowing = copiesBorrowing;
    }
}


