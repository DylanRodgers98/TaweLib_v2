package com.crowvalley.tawelib.model.user;

import javax.persistence.Embeddable;

/**
 * Address object for storing information about a {@link User}'s address,
 * extracted to avoid having to store it in the {@link User} class.
 *
 * @author Dylan Rodgers
 */
@Embeddable
public class Address {

    private String houseNum;

    private String street;

    private String town;

    private String county;

    private String postcode;

    public Address(String houseNum, String street, String town, String county, String postcode) {
        this.houseNum = houseNum;
        this.street = street;
        this.town = town;
        this.county = county;
        this.postcode = postcode;
    }

    public Address() {
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

    @Override
    public String toString() {
        return new StringBuilder(getHouseNum())
                .append(" ")
                .append(getStreet())
                .append(", ")
                .append(getTown())
                .append(", ")
                .append(getCounty())
                .append(", ")
                .append(getPostcode())
                .toString();
    }
}
