package com.crowvalley.model;

import java.nio.file.Path;

/**
 * Models a user. Each user has a username, a first name, a surname, a phone number, a house number, a street number, a street, a town, a county, a postcode, a balance, and a profile path.
 * @author Carlos
 */
public class User {
    private String userName;
    private String firstName;
    private String surName;
    private String phoneNum;
    private String houseNum;
    private String street;
    private String town;
    private String county;
    private String postcode;
    private Float balance;
    private Path profilePath;

    /**
     * Creates a new user.
     * @param userName The username of the user.
     * @param firstName The first name of the user.
     * @param surName The surname of the user.
     * @param phoneNum The phone number of the user.
     * @param houseNum The house number of the user.
     * @param street The street of the user.
     * @param town The town of the user.
     * @param county The county of the user.
     * @param postcode The postcode of the user.
     * @param balance The balance of the user.
     * @param profilePath The profile path of the user.
     */
    public User(String userName, String firstName, String surName, String phoneNum, String houseNum,
                String street, String town, String county, String postcode, Path profilePath, Float balance){
        this.userName = userName;
        this.firstName = firstName;
        this.surName= surName;
        this.phoneNum = phoneNum;
        this.houseNum = houseNum;
        this.street = street;
        this.town = town;
        this.county = county;
        this.postcode = postcode;
        this.profilePath = profilePath;
        this.balance = balance;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
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

    public Path getProfilePath() {
        return profilePath.toAbsolutePath();
    }

    public void setProfilePath(Path profilePath) {
        this.profilePath = profilePath;
    }
}


