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

    @Override
    public Optional<Book> get(Long id) {
        Optional<Book> book = DAO.get(id);
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
        List<Book> books = DAO.getAll();
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
        DAO.save(book);
        LOGGER.info("Book with ID {} saved successfully", book.getId());
    }

    @Override
    public void update(Book book) {
        DAO.update(book);
        LOGGER.info("Book with ID {} updated successfully", book.getId());
    }

    @Override
    public void delete(Book book) {
        DAO.delete(book);
        LOGGER.info("Book with ID {} deleted successfully", book.getId());
    }

    @Override
    public void setDAO(ResourceDAO DAO) {
        this.DAO = DAO;
        LOGGER.info("BookServiceImpl DAO set to {}", DAO.getClass());
    }
}
