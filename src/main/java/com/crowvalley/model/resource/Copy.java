package com.crowvalley.model.resource;

import com.crowvalley.model.ResourceRequestQueue;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Copy {

    @Id
    @GeneratedValue
    private int id;

    private int resourceId;

    private int loanDurationAsDays;

    private String usernameOfCurrentOwner;

    /**
     * Creates a copy.
     * @param id the unique identifier of the copy
     * @param resourceId the resourceId of the copy
     * @param loanDurationAsDays The minimum duration of a loan in days
     */
    public Copy(int id, int resourceId, int loanDurationAsDays) {
        this.id = id;
        this.resourceId = resourceId;
        this.loanDurationAsDays = loanDurationAsDays;
    }

    public Copy() {
    }

    public int getCopyId() {
        return id;
    }

    public int getResourceId() {
        return resourceId;
    }

    public int getLoanDurationAsDays() {
        return loanDurationAsDays;
    }

    public void setLoanDurationAsDays(int loanDurationAsDays) {
        this.loanDurationAsDays = loanDurationAsDays;
    }

    public String getUsernameOfCurrentOwner() {
        return usernameOfCurrentOwner;
    }

    public void setUsernameOfCurrentOwner(String usernameOfCurrentOwner) {
        this.usernameOfCurrentOwner = usernameOfCurrentOwner;
    }
}
