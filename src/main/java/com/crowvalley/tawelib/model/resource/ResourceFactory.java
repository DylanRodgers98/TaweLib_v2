package com.crowvalley.tawelib.model.resource;

import org.springframework.util.Assert;

import java.util.Set;

/**
 * Factory class for creating {@link Resource} objects, as well as
 * {@link Copy} objects and {@link Loan} objects.
 *
 * @author Dylan Rodgers
 */
public class ResourceFactory {

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
                                Set<String> subtitleLanguages) {
        Assert.hasText(title, "DVD must have a Title");
        Assert.hasText(year, "DVD must have a Year");
        Assert.hasText(director, "DVD must have an Director");
        Assert.hasText(language, "DVD must have a Language");
        Assert.notNull(runtime, "DVD must have a Runtime");
        Assert.notNull(subtitleLanguages, "DVD must have a set of Subtitle Languages");
        return new Dvd(title, year, imageUrl, director, language, runtime, subtitleLanguages);
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
    public static Copy createCopy(Resource resource, Integer loanDurationAsDays, String location) {
        Assert.notNull(resource, "Copy must have an attached Resource of which it is a Copy of");
        Assert.notNull(loanDurationAsDays, "Copy must have a Loan Duration in Days");
        Assert.hasText(location, "Copy must have a Location");
        return new Copy(resource, loanDurationAsDays, location);
    }

}
