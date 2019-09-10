package com.crowvalley.tawelib.model.user;

import javax.persistence.Embeddable;

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
}
