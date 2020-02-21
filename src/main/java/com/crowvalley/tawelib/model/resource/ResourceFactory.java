package com.crowvalley.tawelib.model.resource;

import org.springframework.util.Assert;

/**
 * Factory class for creating {@link Resource} objects, as well as
 * {@link Copy} objects and {@link Loan} objects.
 *
 * @author Dylan Rodgers
 */
public class ResourceFactory {

    private static final String RESOURCE_HAS_NO_ID_ERROR_MESSAGE = "Cannot create copy. The resource has no ID, so a " +
            "copy instance cannot reference it. Likely cause is that the resource hasn't been persisted to the database yet";

    private static final String CANNOT_GET_RESOURCE_TYPE_ERROR_MESSAGE = "Cannot get resource type from resource";

    public static Book createBook(String title, String year, String imageUrl,
                                  String author, String publisher, String genre,
                                  String isbn, String language) {
        Assert.hasText(title, "Book must have a Title");
        Assert.hasText(year, "Book must have a Year");
        Assert.hasText(author, "Book must have an Author");
        Assert.hasText(publisher, "Book must have a Publisher");
        Assert.hasText(genre, "Book must have a Genre");
        Assert.hasText(isbn, "Book must have an ISBN");
        Assert.hasText(language, "Book must have a Language");
        return new Book(title, year, imageUrl, author, publisher, genre, isbn, language);
    }

    public static Dvd createDvd(String title, String year, String imageUrl,
                                String director, String language, Integer runtime,
                                String subtitleLang) {
        Assert.hasText(title, "DVD must have a Title");
        Assert.hasText(year, "DVD must have a Year");
        Assert.hasText(director, "DVD must have an Director");
        Assert.hasText(language, "DVD must have a Language");
        Assert.notNull(runtime, "DVD must have a Runtime");
        Assert.hasText(subtitleLang, "DVD must have a Subtitle Language");
        return new Dvd(title, year, imageUrl, director, language, runtime, subtitleLang);
    }

    public static Laptop createLaptop(String title, String year, String imageUrl,
                                      String manufacturer, String model, String os) {
        Assert.hasText(title, "Laptop must have a Title");
        Assert.hasText(year, "Laptop must have a Year");
        Assert.hasText(manufacturer, "Laptop must have a Manufacturer");
        Assert.hasText(model, "Laptop must have a Model");
        Assert.hasText(os, "Laptop must have an Operation System");
        return new Laptop(title, year, imageUrl, manufacturer, model, os);
    }

    /**
     * Creates a {@link Copy} of a given {@link Resource}.
     *
     * @param resource           The {@link Resource} for which to create a {@link Copy} of.
     * @param loanDurationAsDays An {@link Integer} representing the number of
     *                           days this {@link Copy} can be loaned for.
     * @return The created {@link Copy} object
     */
    public static Copy createCopy(Resource resource, Integer loanDurationAsDays) {
        Long id = resource.getId();
        Assert.notNull(id, RESOURCE_HAS_NO_ID_ERROR_MESSAGE);

        ResourceType resourceType = resource.getResourceType();
        Assert.notNull(resourceType, CANNOT_GET_RESOURCE_TYPE_ERROR_MESSAGE);

        return new Copy(id, resourceType, loanDurationAsDays);
    }

}
