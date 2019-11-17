package com.crowvalley.tawelib.model.resource;

import com.crowvalley.tawelib.model.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.util.concurrent.TimeUnit;

/**
 * Factory class for creating {@link Resource} objects, as well as
 * {@link Copy} objects and {@link Loan} objects.
 *
 * @author Dylan Rodgers
 */
public class ResourceFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceFactory.class);

    private static final String RESOURCE_HAS_NO_ID_ERROR_MESSAGE = "Cannot create copy. The resource has no ID, so a " +
            "copy instance cannot reference it. Likely cause is that the resource hasn't been persisted to the database yet";

    private static final String CANNOT_GET_RESOURCE_TYPE_ERROR_MESSAGE = "Cannot get resource type from copy";

    private static final String COPY_HAS_NO_ID_ERROR_MESSAGE = "Cannot create loan. The copy has no ID, so a " +
            "loan instance cannot reference it. Likely cause is that the copy hasn't been persisted to the database yet";

    public static Book createBook(String title, String year, String imageUrl, String author,
                           String publisher, String genre, String isbn, String language) {
        return new Book(title, year, imageUrl, author, publisher, genre, isbn, language);
    }

    public static Dvd createDvd(String title, String year, String imageUrl,
                         String director, String language, Integer runtime, String subtitleLang) {
        return new Dvd(title, year, imageUrl, director, language, runtime, subtitleLang);
    }

    public static Laptop createLaptop(String title, String year, String imageUrl,
                               String manufacturer, String model, String os) {
        return new Laptop(title, year, imageUrl, manufacturer, model, os);
    }

    /**
     * Creates a {@link Copy} of a given {@link Resource}.
     *
     * @param resource The {@link Resource} for which to create a {@link Copy} of.
     * @param loanDurationAsDays An {@link Integer} representing the number of
     *                           days this {@link Copy} can be loaned for.
     * @return The created {@link Copy} object
     */
    public static Copy createCopy(Resource resource, Integer loanDurationAsDays) {
        if (resource.getId() == null) {
            LOGGER.error(RESOURCE_HAS_NO_ID_ERROR_MESSAGE);
            throw new IllegalArgumentException(RESOURCE_HAS_NO_ID_ERROR_MESSAGE);
        }

        //Sets the resource type to help with database queries
        ResourceType resourceType = null;
        if (resource instanceof Book) {
            resourceType = ResourceType.BOOK;
        } else if (resource instanceof Dvd) {
            resourceType = ResourceType.DVD;
        } else if (resource instanceof Laptop) {
            resourceType = ResourceType.LAPTOP;
        }

        if (resourceType == null) {
            LOGGER.error(CANNOT_GET_RESOURCE_TYPE_ERROR_MESSAGE);
            throw new IllegalStateException(CANNOT_GET_RESOURCE_TYPE_ERROR_MESSAGE);
        }

        return new Copy(resource.getId(), resourceType, loanDurationAsDays);
    }

    /**
     * Creates a {@link Loan} for a given {@link Copy}.
     *
     * @param copy The {@link Copy} for which to create a {@link Loan} for.
     * @param borrowerUsername The {@code username} of the {@link User} who
     *                         is borrowing the passed in {@link Copy}.
     * @return The created {@link Loan} object.
     */
    public static Loan createLoanForCopy(Copy copy, String borrowerUsername) {
        if (copy.getId() == null) {
            LOGGER.error(COPY_HAS_NO_ID_ERROR_MESSAGE);
            throw new IllegalArgumentException(COPY_HAS_NO_ID_ERROR_MESSAGE);
        }

        Date startDate = new Date(System.currentTimeMillis());

        Date endDate = new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(copy.getLoanDurationAsDays()));

        return new Loan(copy.getId(), borrowerUsername, startDate, endDate);
    }

}
