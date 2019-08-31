package com.crowvalley.model.resource;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "laptop")
public class Laptop extends Resource{

    private String manufacturer;

    private String model;

    private String os;

    /**
     * This creates a new Laptop.
     * @param title The of the Laptop.
     * @param year The of the Laptop.
     * @param imageUrl The of the Laptop.
     * @param manufacturer The of the Laptop.
     * @param model The of the Laptop.
     * @param os The of the Laptop.
     */
    public Laptop(String title, String year, String imageUrl, String manufacturer, String model, String os){
        super(title, year, imageUrl);
        this.manufacturer = manufacturer;
        this.model = model;
        this.os = os;
    }

    public Laptop() {
        super();
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
