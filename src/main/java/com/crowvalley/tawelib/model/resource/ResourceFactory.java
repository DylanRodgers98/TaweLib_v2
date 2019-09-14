package com.crowvalley.tawelib.model.resource;

public interface ResourceFactory {

    public Book createBook(String title, String year, String imageUrl, String author,
                           String publisher, String genre, String isbn, String language);

    public Dvd createDvd(String title, String year, String imageUrl,
                         String director, String language, Integer runtime, String subtitleLang);

    public Laptop createLaptop(String title, String year, String imageUrl,
                               String manufacturer, String model, String os);

    public Copy createCopy(Resource resource, Integer loanDurationAsDays);

    public Loan createLoanForCopy(Copy copy, String borrowerUsername);

}
