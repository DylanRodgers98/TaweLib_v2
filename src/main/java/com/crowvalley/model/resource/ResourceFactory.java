package com.crowvalley.model.resource;

public class ResourceFactory {

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
        String resourceType = "[WARN] TYPE NOT SET";
        if (resource instanceof Book) {
            resourceType = "Book";
        }
        if (resource instanceof Dvd) {
            resourceType = "Dvd";
        }
        if (resource instanceof Laptop) {
            resourceType = "Laptop";
        }
        return new Copy(resource.getId(), resourceType, loanDurationAsDays);
    }

}
