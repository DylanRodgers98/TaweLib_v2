package com.crowvalley.model;

/**
 * RequestQueueElement creates an object to be used within a
 * ResourceRequestQueue. It stores a userID as an int, and a pointer to the
 * next RequestQueueElement in the ResourceRequestQueue.
 */
public class RequestQueueElement {
    private RequestQueueElement nextElement;
    private String username;

    /**
     * Constructs a RequestQueueElement, with the nextElement pointer
     * initialised as null.
     *
     * @param username The username of the user to be added to the
     *                 ResourceRequestQueue.
     */
    public RequestQueueElement(String username) {
        this.nextElement = null;
        this.username = username;
    }

    public void setNextElement(RequestQueueElement nextElement) {
        this.nextElement = nextElement;
    }

    public RequestQueueElement getNextElement() {
        return nextElement;
    }

    public String getUsername() {
        return this.username;
    }
}
