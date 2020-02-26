package com.crowvalley.tawelib.model.fine;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class OutstandingFinesDTO {

    private static final NumberFormat CURRENCY_FORMAT = NumberFormat.getCurrencyInstance(Locale.UK);

    private final String username;

    private final BigDecimal outstandingFines;

    public OutstandingFinesDTO(String username, BigDecimal outstandingFines) {
        this.username = username;
        this.outstandingFines = outstandingFines;
    }

    public String getUsername() {
        return username;
    }

    public BigDecimal getOutstandingFines() {
        return outstandingFines;
    }

    @Override
    public String toString() {
        return username + " (Outstanding: " + CURRENCY_FORMAT.format(outstandingFines) + ")";
    }

}
