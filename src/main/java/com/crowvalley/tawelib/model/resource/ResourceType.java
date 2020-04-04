package com.crowvalley.tawelib.model.resource;

import java.math.BigDecimal;
import java.math.RoundingMode;

public enum ResourceType {

    ALL(Resource.class, null),
    BOOK(Book.class, new BigDecimal("2").setScale(2,RoundingMode.HALF_EVEN)),
    DVD(Dvd.class, new BigDecimal("2").setScale(2, RoundingMode.HALF_EVEN)),
    LAPTOP(Laptop.class, new BigDecimal("10").setScale(2, RoundingMode.HALF_EVEN));

    private final Class<? extends Resource> modelClass;

    private final BigDecimal fineRate;

    ResourceType(Class<? extends Resource> modelClass, BigDecimal fineRate) {
        this.modelClass = modelClass;
        this.fineRate = fineRate;
    }

    public Class<? extends Resource> getModelClass() {
        return modelClass;
    }

    public BigDecimal getFineRate() {
        return fineRate;
    }

}
