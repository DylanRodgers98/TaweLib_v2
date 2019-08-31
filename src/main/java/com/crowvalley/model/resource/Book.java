package com.crowvalley.model.resource;

import javax.persistence.Entity;

/**
 * Model of a book.Book extends Resource. Each book has a maximum fine, a fine rate per day, an author, a genre, an isbn, and a language.
 * @author Dylan
 */
@Entity
public class Book extends Resource {

    private String author;

    private String publisher;

    private String genre;

    private String isbn;

    private String language;

    /**
     * Create a new book.
     * @param title The title of the book.
     * @param year The year of the book.
     * @param imageUrl The image location of the book.
     * @param author The author of the book.
     * @param genre The genre of the book.
     * @param isbn The ISBN of the book.
     * @param language The language of the book.
     */
    public Book(String title, String year, String imageUrl, String author,
                String publisher, String genre, String isbn, String language){
        super(title, year, imageUrl);
        this.author = author;
        this.publisher = publisher;
        this.genre = genre;
        this.isbn = isbn;
        this.language = language;
    }

    public Book() {
        super();
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

}
