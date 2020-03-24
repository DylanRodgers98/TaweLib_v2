package com.crowvalley.tawelib.model.resource;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Laptop class for creating objects to store information about laptops
 * to be persisted in a database.
 *
 * @author Dylan Rodgers
 */
@Entity
@Table
public class Laptop extends Resource {

    private String manufacturer;

    private String model;

    private String os;

    public Laptop(String title, String year, String imageUrl, String manufacturer, String model, String os) {
        super(ResourceType.LAPTOP, title, year, imageUrl);
        this.manufacturer = manufacturer;
        this.model = model;
        this.os = os;
    }

    public Laptop() {
        super();
    }

    @Override
    public String toString() {
        return new StringBuilder(getTitle())
                .append(" (")
                .append(getYear())
                .append(" ")
                .append(manufacturer)
                .append(" ")
                .append(model)
                .append(")")
                .toString();
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }
}
