package com.crowvalley.tawelib.model.resource;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Book class for creating objects to store information about books
 * to be persisted in a database.
 *
 * @author Dylan Rodgers
 */
@Entity
@Table(name = "book")
public class Book extends Resource {

    private String author;

    private String publisher;

    private String genre;

    private String isbn;

    private String language;

    public Book(String title, String year, String imageUrl, String author,
                String publisher, String genre, String isbn, String language) {
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

    @Override
    public String toString() {
        return new StringBuilder(getTitle())
                .append(" by ")
                .append(author)
                .append(" (")
                .append(getYear())
                .append(")")
                .toString();
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
