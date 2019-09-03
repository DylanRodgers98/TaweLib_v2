package com.crowvalley.model.resource;

public interface ResourceFactory {

    public Book createBook();

    public Dvd createDvd();

    public Laptop createLaptop();

    public Copy createCopy();

}
