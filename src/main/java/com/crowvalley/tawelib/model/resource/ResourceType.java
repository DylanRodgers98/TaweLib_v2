package com.crowvalley.tawelib.model.resource;

public enum ResourceType {
    BOOK(Book.class),
    DVD(Dvd.class),
    LAPTOP(Laptop.class);

    private final Class<? extends Resource> modelClass;

    ResourceType(Class<? extends Resource> modelClass) {
        this.modelClass = modelClass;
    }

    public Class<? extends Resource> getModelClass() {
        return modelClass;
    }

}
