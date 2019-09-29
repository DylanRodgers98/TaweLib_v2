package com.crowvalley.tawelib.model.resource;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Resource abstract class to be used as a superclass for creating
 * objects to store information about resources to be persisted in
 * a database.
 *
 * @author Dylan Rodgers
 */
@MappedSuperclass
public abstract class Resource {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String year;

    private String imageUrl;

    public Resource(String title, String year, String imageUrl) {
        this.title = title;
        this.year = year;
        this.imageUrl = imageUrl;
    }

    public Resource() {
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageURL) {
        this.imageUrl = imageURL;
    }

}

