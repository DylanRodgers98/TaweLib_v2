package com.crowvalley.tawelib.model.resource;

public enum ResourceType {
    BOOK(Book.class),
    DVD(Dvd.class),
    LAPTOP(Laptop.class);

    private Class<? extends Resource> clazz;

    ResourceType(Class<? extends Resource> clazz) {
        this.clazz = clazz;
    }

    public Class<? extends Resource> getClazz() {
        return clazz;
    }

}
