package com.crowvalley.tawelib.model.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.*;

/**
 * Copy class for creating objects to store information about copies
 * to be persisted in a database.
 *
 * @author Dylan Rodgers
 */
@Entity
@Table
public class Copy {

    private static final Logger LOGGER = LoggerFactory.getLogger(Copy.class);

    @Id
    @GeneratedValue
    @Column
    private Long id;

    @ManyToOne
    @JoinColumn
    private Resource resource;

    private Integer loanDurationAsDays;

    private String location;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @MapKey(name = "username")
    private Map<String, CopyRequest> copyRequests;

    public Copy(Resource resource, Integer loanDurationAsDays, String location) {
        this.resource = resource;
        this.loanDurationAsDays = loanDurationAsDays;
        this.location = location;
    }

    public Copy() {
    }

    public Long getId() {
        return id;
    }

    public Resource getResource() {
        return resource;
    }

    public Integer getLoanDurationAsDays() {
        return loanDurationAsDays;
    }

    public String getLocation() { return location; }

    public Map<String, CopyRequest> getCopyRequests() {
        if (copyRequests == null) {
            copyRequests = new HashMap<>();
        }
        return copyRequests;
    }

    @Override
    public String toString() {
        return loanDurationAsDays + " day loan";
    }

}
