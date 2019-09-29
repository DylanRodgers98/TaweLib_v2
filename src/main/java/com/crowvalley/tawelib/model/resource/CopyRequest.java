package com.crowvalley.tawelib.model.resource;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * CopyRequest class for creating objects to store information about
 * copy requests to be persisted in a database.
 *
 * @author Dylan Rodgers
 */
@Entity
@Table(name = "copy_request")
public class CopyRequest {

    @Id
    @GeneratedValue
    @Column(name = "copy_request_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "copy_id")
    private Copy copy;

    private String username;

    private Timestamp requestDate;

    public CopyRequest(Copy copy, String username, Timestamp requestDate) {
        this.copy = copy;
        this.username = username;
        this.requestDate = requestDate;
    }

    public CopyRequest() {
    }

    public Long getId() {
        return id;
    }

    public Copy getCopy() {
        return copy;
    }

    public String getUsername() {
        return username;
    }

    public Timestamp getRequestDate() {
        return requestDate;
    }

}
