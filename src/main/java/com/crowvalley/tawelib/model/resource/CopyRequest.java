package com.crowvalley.tawelib.model.resource;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Comparator;

/**
 * CopyRequest class for creating objects to store information about
 * copy requests to be persisted in a database.
 *
 * @author Dylan Rodgers
 */
@Entity
@Table
public class CopyRequest {

    @Id
    @GeneratedValue
    @Column
    private Long id;

    @ManyToOne
    private Copy copy;

    private String username;

    private LocalDateTime requestDate;

    private Status requestStatus;

    public CopyRequest(Copy copy, String username) {
        this(copy, username, LocalDateTime.now());
    }

    public CopyRequest(Copy copy, String username, LocalDateTime requestDate) {
        this(copy, username, requestDate, Status.REQUESTED);
    }

    public CopyRequest(Copy copy, String username, LocalDateTime requestDate, Status requestStatus) {
        this.copy = copy;
        this.username = username;
        this.requestDate = requestDate;
        this.requestStatus = requestStatus;
    }

    public CopyRequest() {
    }

    public static Comparator<CopyRequest> getComparator() {
        return (copyRequest1, copyRequest2) -> ComparisonChain.start()
                .compare(copyRequest1.getRequestStatus(), copyRequest2.getRequestStatus(), Ordering.natural().reverse())
                .compare(copyRequest1.getRequestDate(), copyRequest2.getRequestDate())
                .result();
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

    public LocalDateTime getRequestDate() {
        return requestDate;
    }

    public Status getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(Status requestStatus) {
        this.requestStatus = requestStatus;
    }

    public enum Status {
        REQUESTED("Request made"),
        READY_FOR_COLLECTION("Ready for collection"),
        COLLECTED("Collected");

        private final String toString;

        Status(String toString) {
            this.toString = toString;
        }

        @Override
        public String toString() {
            return toString;
        }
    }

}
