package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.dao.ResourceDAO;
import com.crowvalley.tawelib.model.resource.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;
import java.util.Optional;

/**
 * Service class for retrieving data about {@link Book} objects
 * persisted in a database, using a Data Access Object (DAO) to perform
 * CRUD operations.
 *
 * @author Dylan Rodgers
 */
public class BookServiceImpl implements ResourceService<Book> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookServiceImpl.class);

    @Qualifier("bookDAO")
    @Autowired
    private ResourceDAO<Book> DAO;

    /**
     * Retrieves a {@link Book} from the DAO using the {@link Book}'s
     * {@code id} and returns it wrapped in an {@link Optional}. If a
     * {@link Book} with the passed {@code id} isn't retrieved from the
     * DAO, an empty {@link Optional} is returned.
     *
     * @param id The ID of the {@link Book} to be retrieved
     * @return The requested {@link Book} wrapped in an {@link Optional}
     * if it isn't retrieved by the DAO, or an empty {@link Optional} if not.
     */
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

    /**
     * @return A {@link List} of all {@link Book}s retrieved by the DAO.
     */
    @Override
    public List<Book> getAll() {
        List<Book> books = DAO.getAll();
        if (!books.isEmpty()) {
            LOGGER.info("All books retrieved successfully");
        } else {
            LOGGER.warn("No books found");
        }
        return books;
    }

    /**
     * Persists a {@link Book} object to the database through the DAO.
     *
     * @param book The {@link Book} object to be saved to the database.
     */
    @Override
    public void save(Book book) {
        DAO.save(book);
        LOGGER.info("Book with ID {} saved successfully", book.getId());
    }

    /**
     * Updates a {@link Book} object already persisted in the database
     * with new data after being changed by the application.
     *
     * @param book The {@link Book} object to be updated in the database.
     */
    @Override
    public void update(Book book) {
        DAO.update(book);
        LOGGER.info("Book with ID {} updated successfully", book.getId());
    }

    /**
     * Deletes a {@link Book} object from the database through the DAO.
     *
     * @param book The {@link Book} object to be deleted from the database.
     */
    @Override
    public void delete(Book book) {
        DAO.delete(book);
        LOGGER.info("Book with ID {} deleted successfully", book.getId());
    }

    @Override
    public void setDAO(ResourceDAO<Book> DAO) {
        this.DAO = DAO;
        LOGGER.info("BookServiceImpl DAO set to {}", DAO.getClass());
    }
}
