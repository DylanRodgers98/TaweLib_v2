package com.crowvalley.tawelib.model.user;

import javax.persistence.*;

@Entity
@Table(name = "user")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class User {

    @Id
    @Column(name = "username")
    private String username;

    private String firstName;

    private String surname;

    private String phoneNum;

    private Address address;

    private String profileImagePath;

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
     * @param profileImagePath The profile path of the user.
     */
    public User(String username, String firstName, String surname, String phoneNum, String houseNum,
                String street, String town, String county, String postcode, String profileImagePath){
        this.username = username;
        this.firstName = firstName;
        this.surname = surname;
        this.phoneNum = phoneNum;
        this.address = new Address(houseNum, street, town, county, postcode);
        this.profileImagePath = profileImagePath;
    }

    public User(String username, String firstName, String surname, String phoneNum,
                Address address, String profileImagePath){
        this.username = username;
        this.firstName = firstName;
        this.surname = surname;
        this.phoneNum = phoneNum;
        this.address = address;
        this.profileImagePath = profileImagePath;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getProfileImagePath() {
        return profileImagePath;
    }

    public void setProfileImagePath(String profileImagePath) {
        this.profileImagePath = profileImagePath;
    }
}


