package com.crowvalley.tawelib.model.resource;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Resource {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String year;

    private String imageUrl;

    /**
     * Create a new resource.
     *
     * @param title    The title of the resource.
     * @param year     The year of the resource.
     * @param imageUrl The image url of the resource.
     */
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

