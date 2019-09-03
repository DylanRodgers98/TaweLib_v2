package com.crowvalley.model.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourceFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceFactory.class);

    private static final String RESOURCE_HAS_NO_ID_ERROR_MESSAGE = "Cannot create copy. The resource has no ID, so a " +
            "copy instance cannot reference it. Likely cause is that the resource hasn't been persisted to the database yet.";

    private static final String CANNOT_GET_RESOURCE_TYPE_ERROR_MESSAGE =
            "Resource type is empty. Copy needs to know what type of resource it is a copy of.";

    public Book createBook(String title, String year, String imageUrl, String author,
                           String publisher, String genre, String isbn, String language) {
        return new Book(title, year, imageUrl, author, publisher, genre, isbn, language);
    }

    public Dvd createDvd(String title, String year, String imageUrl,
                         String director, String language, Integer runtime, String subtitleLang) {
        return new Dvd(title, year, imageUrl, director, language, runtime, subtitleLang);
    }

    public Laptop createLaptop(String title, String year, String imageUrl,
                               String manufacturer, String model, String os) {
        return new Laptop(title, year, imageUrl, manufacturer, model, os);
    }

    public Copy createCopy(Resource resource, Integer loanDurationAsDays) {
        if (resource.getId() == null) {
            LOGGER.error(RESOURCE_HAS_NO_ID_ERROR_MESSAGE);
            throw new IllegalArgumentException(RESOURCE_HAS_NO_ID_ERROR_MESSAGE);
        }

        String resourceType = "";
        if (resource instanceof Book) {
            resourceType = "Book";
        } else if (resource instanceof Dvd) {
            resourceType = "Dvd";
        } else if (resource instanceof Laptop) {
            resourceType = "Laptop";
        }

        if (resourceType.isEmpty()){
            LOGGER.error(CANNOT_GET_RESOURCE_TYPE_ERROR_MESSAGE);
            throw new IllegalArgumentException(CANNOT_GET_RESOURCE_TYPE_ERROR_MESSAGE);
        }

        return new Copy(resource.getId(), resourceType, loanDurationAsDays);
    }

}
