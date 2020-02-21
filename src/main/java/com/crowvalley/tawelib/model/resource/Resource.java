package com.crowvalley.tawelib.model.resource;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.Optional;

/**
 * Resource abstract class to be used as a superclass for creating
 * objects to store information about resources to be persisted in
 * a database.
 *
 * @author Dylan Rodgers
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Resource {

    @Id
    @GeneratedValue
    private Long id;

    private ResourceType resourceType;

    private String title;

    private String year;

    private String imageUrl;

    protected Resource(ResourceType resourceType, String title, String year, String imageUrl) {
        this.resourceType = resourceType;
        this.title = title;
        this.year = year;
        this.imageUrl = imageUrl;
    }

    public Resource() {
    }

    public Long getId() {
        return id;
    }

    public ResourceType getResourceType() { return resourceType; }

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

    public Optional<String> getImageUrl() {
        if (StringUtils.isEmpty(imageUrl)) {
            return Optional.empty();
        } else {
            return Optional.of(imageUrl);
        }
    }

    public void setImageUrl(String imageURL) {
        this.imageUrl = imageURL;
    }

}

