package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.dao.ResourceDAO;
import com.crowvalley.tawelib.model.resource.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class BookServiceImpl implements ResourceService<Book> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookServiceImpl.class);

    @Autowired
    private ResourceDAO bookDAO;

    @Override
    public Optional<Book> get(Long id) {
        Optional<Book> book = bookDAO.get(id);
        if (book.isPresent()) {
            LOGGER.info("Book with ID {} retrieved successfully", id);
            return book;
        } else {
            LOGGER.warn("Could not find book with ID {}", id);
            return Optional.empty();
        }
    }

    @Override
    public List<Book> getAll() {
        List<Book> books = bookDAO.getAll();
        if (!books.isEmpty()) {
            LOGGER.info("All books retrieved successfully");
            return books;
        } else {
            LOGGER.warn("No books found");
            return books;
        }
    }

    @Override
    public void save(Book book){
        bookDAO.save(book);
        LOGGER.info("Book with ID {} saved successfully", book.getId());
    }

    @Override
    public void update(Book book) {
        bookDAO.update(book);
        LOGGER.info("Book with ID {} updated successfully", book.getId());
    }

    @Override
    public void delete(Book book) {
        bookDAO.delete(book);
        LOGGER.info("Book with ID {} deleted successfully", book.getId());
    }

    @Override
    public void setLaptopDAO(ResourceDAO laptopDAO) {
        this.bookDAO = laptopDAO;
        LOGGER.info("BookServiceImpl DAO set to {}", laptopDAO.getClass());
    }
}
