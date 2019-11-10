package com.crowvalley.tawelib.dao;

import com.crowvalley.tawelib.model.resource.Book;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Data Access Object for retrieving data about {@link Book} objects
 * persisted in a database. This class provides methods to perform CRUD
 * operations on a database table that stores information about {@link Book}
 * objects, using a Hibernate {@link SessionFactory} object to access
 * the database.
 *
 * @author Dylan Rodgers
 */
@Transactional
public class BookDAOImpl implements ResourceDAO<Book> {

    @Autowired
    private SessionFactory sessionFactory;

    /**
     * Retrieves a {@link Book} from the database using the {@link Book}'s
     * {@code id} and returns it wrapped in an {@link Optional}. If a
     * {@link Book} with the passed {@code id} doesn't
     * exist within the database, an empty {@link Optional} is returned.
     *
     * @param id The ID of the {@link Book} to be retrieved
     * @return The requested {@link Book} wrapped in an {@link Optional}
     * if it is found in the database, or an empty {@link Optional} if not.
     */
    @Override
    public Optional<Book> get(Long id) {
        Book book = sessionFactory.getCurrentSession().get(Book.class, id);
        return Optional.ofNullable(book);
    }

    /**
     * @return A {@link List} of all {@link Book}s stored in the database.
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<Book> getAll() {
        return sessionFactory.getCurrentSession().createQuery("from Book").list();
    }

    /**
     * Persists a {@link Book} object to the database.
     *
     * @param book The {@link Book} object to be saved to the database.
     */
    @Override
    public void save(Book book) {
        sessionFactory.getCurrentSession().save(book);
    }

    /**
     * Updates a {@link Book} object already persisted in the database
     * with new data after being changed by the application.
     *
     * @param book The {@link Book} object to be updated in the database.
     */
    @Override
    public void update(Book book) {
        sessionFactory.getCurrentSession().update(book);
    }

    /**
     * Deletes a {@link Book} object from the database.
     *
     * @param book The {@link Book} object to be deleted from the database.
     */
    @Override
    public void delete(Book book) {
        sessionFactory.getCurrentSession().delete(book);
    }
}
