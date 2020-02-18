package com.crowvalley.tawelib.model.fine;

import java.text.DecimalFormat;

public class OutstandingFinesDTO {

    private final String username;

    private final double outstandingFines;

    public OutstandingFinesDTO(String username, Double outstandingFines) {
        this.username = username;
        this.outstandingFines = outstandingFines;
    }

    public String getUsername() {
        return username;
    }

    public double getOutstandingFines() {
        return outstandingFines;
    }

    @Override
    public String toString() {
        return username + " (Outstanding: £" + String.format("£%.2f", outstandingFines) + ")";
    }

}
