package com.crowvalley.model.resource;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "copy_request")
public class CopyRequest {

    @Id
    @GeneratedValue
    @Column(name = "copy_request_id")
    private Long id;

    @ManyToOne
    @JoinTable(
            name="copy_copy_request",
            joinColumns = @JoinColumn(name="copy_request_id", insertable = false, updatable = false),
            inverseJoinColumns = @JoinColumn(name = "copy_id", insertable = false, updatable = false)
    )
    private Copy copy;

    private String username;

    private Date requestDate;

    public CopyRequest (Copy copy, String username, Date requestDate) {
        this.copy = copy;
        this.username = username;
        this.requestDate = requestDate;
    }

    public CopyRequest () {
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

    public Date getRequestDate() {
        return requestDate;
    }

}
