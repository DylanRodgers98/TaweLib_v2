package com.crowvalley.tawelib.model.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.util.concurrent.TimeUnit;

public class ResourceFactoryImpl implements ResourceFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceFactoryImpl.class);

    private static final String RESOURCE_HAS_NO_ID_ERROR_MESSAGE = "Cannot create copy. The resource has no ID, so a " +
            "copy instance cannot reference it. Likely cause is that the resource hasn't been persisted to the database yet.";

    private static final String CANNOT_GET_RESOURCE_TYPE_ERROR_MESSAGE =
            "Resource type is empty. Copy needs to know what type of resource it is a copy of.";

    private static final String COPY_HAS_NO_ID_ERROR_MESSAGE = "Cannot create loan. The copy has no ID, so a " +
            "loan instance cannot reference it. Likely cause is that the copy hasn't been persisted to the database yet.";

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
            resourceType = Copy.BOOK_TYPE;
        } else if (resource instanceof Dvd) {
            resourceType = Copy.DVD_TYPE;
        } else if (resource instanceof Laptop) {
            resourceType = Copy.LAPTOP_TYPE;
        }

        if (resourceType.isEmpty()){
            LOGGER.error(CANNOT_GET_RESOURCE_TYPE_ERROR_MESSAGE);
            throw new IllegalArgumentException(CANNOT_GET_RESOURCE_TYPE_ERROR_MESSAGE);
        }

        return new Copy(resource.getId(), resourceType, loanDurationAsDays);
    }

    public Loan createLoanForCopy(Copy copy, String borrowerUsername) {
        if (copy.getId() == null) {
            LOGGER.error(COPY_HAS_NO_ID_ERROR_MESSAGE);
            throw new IllegalArgumentException(COPY_HAS_NO_ID_ERROR_MESSAGE);
        }

        Date startDate = new Date(System.currentTimeMillis());

        Date endDate = new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(copy.getLoanDurationAsDays()));

        return new Loan(copy.getId(), borrowerUsername, startDate, endDate);
    }

}
