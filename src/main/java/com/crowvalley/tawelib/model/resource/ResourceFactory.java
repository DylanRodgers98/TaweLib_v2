package com.crowvalley.tawelib.model.resource;

public interface ResourceFactory {

    Book createBook(String title, String year, String imageUrl, String author,
                    String publisher, String genre, String isbn, String language);

    Dvd createDvd(String title, String year, String imageUrl,
                  String director, String language, Integer runtime, String subtitleLang);

    Laptop createLaptop(String title, String year, String imageUrl,
                        String manufacturer, String model, String os);

    Copy createCopy(Resource resource, Integer loanDurationAsDays);

    Loan createLoanForCopy(Copy copy, String borrowerUsername);

}
