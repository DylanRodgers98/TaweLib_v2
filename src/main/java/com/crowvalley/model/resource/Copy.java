package com.crowvalley.model.resource;

import com.crowvalley.model.ResourceRequestQueue;

/**
 * Models a copy of a resource. Each Copy has a copy identfier
 * @author Carlos Salamanca Fernandez
 */
public class Copy {
    private int copyId;
    private int resourceId;
    private int loanDurationAsDays;
    private String currentOwner;
    private ResourceRequestQueue requestQueue;

    /**
     * Creates a copy.
     * @param copyId the unique identifier of the copy
     * @param resourceId the resourceId of the copy
     * @param currentOwner the current owner of the copy
     * @param loanDurationAsDays The minimum duration of a loan in days
     */
    public Copy(int copyId, int resourceId, int loanDurationAsDays, String currentOwner) {
        this.copyId = copyId;
        this.resourceId = resourceId;
        this.loanDurationAsDays = loanDurationAsDays;
        this.currentOwner = currentOwner;
        requestQueue = new ResourceRequestQueue();
    }

    public int getCopyId() {
        return copyId;
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

    public String getCurrentOwner() {
        return currentOwner;
    }

    public void setCurrentOwner(String currentOwner) {
        this.currentOwner = currentOwner;
    }

    public ResourceRequestQueue getRequestQueue() {
        return requestQueue;
    }
}
