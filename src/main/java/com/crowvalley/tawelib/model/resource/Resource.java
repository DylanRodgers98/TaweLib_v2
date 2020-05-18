package com.crowvalley.tawelib.model.resource;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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
    @Column
    private Long id;

    private ResourceType resourceType;

    protected String title;

    protected String year;

    private String imageUrl;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "resource")
    private Set<Copy> copies;

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

    public Set<Copy> getCopies() {
        if (copies == null) {
            copies = new HashSet<>();
        }
        return copies;
    }

}

