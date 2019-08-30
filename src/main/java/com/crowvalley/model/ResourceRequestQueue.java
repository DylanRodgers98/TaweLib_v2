package com.crowvalley.model;

/**
 * Implements a circular queue for users who have requested a copy and are
 * waiting for one to become available for them to borrow.
 * A RequestQueueElement object is enqueued when they request a copy.
 * When a copy becomes available to borrow, this user whose ID is stored in the
 * RequestQueueElement object at the front of the queue (i.e. the head) gets the
 * available copy, and the RequestQueueElement object is dequeued.
 * @author Dylan
 */
public class ResourceRequestQueue {
    private RequestQueueElement head;
    private RequestQueueElement tail;

    /**
     * Constructs an empty (i.e. head and tail equal null) ResourceRequestQueue
     * object.
     */
    public ResourceRequestQueue() {
        head = null;
        tail = null;
    }

    /**
     * Checks if the queue is empty or not.
     *
     * @return True if empty, False otherwise.
     */
    public boolean isEmpty() {
        return ((head == null) && (tail == null));
    }

    /**
     * Adds a RequestQueueElement object to the back of the queue.
     *
     * @param newElement The RequestQueueElement object to be added to the
     *                   queue.
     */
    public void enqueue(RequestQueueElement newElement) {
        if (isEmpty()) {
            //If the queue is empty, set both head and tail as newElement.
            this.head = newElement;
            this.tail = newElement;
        } else if (this.head.equals(this.tail)) {
            //If queue only has one element in it (i.e. head and tail are the
            //same, set just the tail to be newElement and set the nextElement
            //pointer in the head to point to the tail.
            this.tail = newElement;
            this.head.setNextElement(this.tail);
        } else {
            //Else set the nextElement pointer in the tail to point to
            //newElement then set the tail to be newElement.
            this.tail.setNextElement(newElement);
            this.tail = newElement;
        }
    }

    /**
     * Removes the element at the front of the queue (i.e. the head) and sets
     * the next element in the queue to be the new head.
     */
    public void dequeue() {
        this.head = this.head.getNextElement();
    }

    /**
     * Gets the value at the front of the queue.
     *
     * @return The userID stored in the RequestQueueElement object at the front
     * of the queue.
     */
    public String peek() {
        return this.head.getUsername();
    }
}
