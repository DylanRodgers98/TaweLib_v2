package com.crowvalley.tawelib.model.user;

import javax.persistence.*;
import java.util.Optional;

/**
 * User class for creating objects to store information about users
 * to be persisted in a database.
 *
 * @author Dylan Rodgers
 */
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

    @Embedded
    private Address address;

    private String profileImagePath;

    /**
     * Creates a User using individually passed in {@link Address} fields
     */
    public User(String username, String firstName, String surname, String phoneNum, String houseNum,
                String street, String town, String county, String postcode, String profileImagePath) {
        this.username = username;
        this.firstName = firstName;
        this.surname = surname;
        this.phoneNum = phoneNum;
        this.address = new Address(houseNum, street, town, county, postcode);
        this.profileImagePath = profileImagePath;
    }

    /**
     * Creates a User using a passed in {@link Address} object
     */
    public User(String username, String firstName, String surname, String phoneNum,
                Address address, String profileImagePath) {
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

    public Optional<String> getProfileImagePath() {
        return Optional.ofNullable(profileImagePath);
    }

    public void setProfileImagePath(String profileImagePath) {
        this.profileImagePath = profileImagePath;
    }
}


