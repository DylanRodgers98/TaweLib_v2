package com.crowvalley.service;

import com.crowvalley.dao.ResourceDAO;
import com.crowvalley.model.resource.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

public class BookServiceImpl implements ResourceService<Book> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookServiceImpl.class);

    @Resource
    private ResourceDAO DAO;

    public Optional<Book> get(Long id) {
        Optional<Book> book = DAO.get(id);
        if (book.isPresent()) {
            LOGGER.info("Book with id {} retrieved successfully", id);
            return book;
        } else {
            LOGGER.warn("Could not find book with id {}", id);
            return Optional.empty();
        }
    }

    public List<Book> getAll() {
        List<Book> books = DAO.getAll();
        if (!books.isEmpty()) {
            LOGGER.info("All books retrieved successfully");
            return books;
        } else {
            LOGGER.warn("No books found");
            return books;
        }
    }

    public void save(Book book){
        DAO.save(book);
        LOGGER.info("Book with id {} saved successfully", book.getId());
    }

    public void update(Book book) {
        DAO.update(book);
        LOGGER.info("Book with id {} updated successfully", book.getId());
    }

    public void delete(Book book) {
        DAO.delete(book);
        LOGGER.info("Book with id {} deleted successfully", book.getId());
    }

    public void setDAO(ResourceDAO DAO) {
        this.DAO = DAO;
        LOGGER.info("BookServiceImpl DAO set to {}", DAO.getClass());
    }
}
