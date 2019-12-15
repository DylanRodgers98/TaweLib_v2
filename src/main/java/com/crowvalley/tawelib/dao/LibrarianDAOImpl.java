package com.crowvalley.tawelib.dao;

import com.crowvalley.tawelib.model.user.Librarian;
import com.crowvalley.tawelib.util.DatabaseUtils;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Data Access Object for retrieving data about {@link Librarian} objects
 * persisted in a database. This class provides methods to perform CRUD
 * operations on a database table that stores information about {@link Librarian}
 * objects, using a Hibernate {@link SessionFactory} object to access
 * the database.
 *
 * @author Dylan Rodgers
 */
@Transactional
public class LibrarianDAOImpl implements LibrarianDAO {

    @Autowired
    private SessionFactory sessionFactory;

    /**
     * Retrieves a {@link Librarian} from the database using the
     * {@link Librarian}'s {@code username} and returns it wrapped in an
     * {@link Optional}. If a {@link Librarian} with the passed {@code username}
     * doesn't exist within the database, an empty {@link Optional} is returned.
     *
     * @param username The username of the {@link Librarian} to be retrieved
     * @return The requested {@link Librarian} wrapped in an {@link Optional}
     * if it is found in the database, or an empty {@link Optional} if not.
     */
    @Override
    public Optional<Librarian> getWithUsername(String username) {
        Librarian librarian = sessionFactory.getCurrentSession().get(Librarian.class, username);
        return Optional.ofNullable(librarian);
    }

    /**
     * Retrieves a {@link Librarian} from the database using the
     * {@link Librarian}'s {@code staffNum} and returns it wrapped in an
     * {@link Optional}. If a {@link Librarian} with the passed {@code staffNum}
     * doesn't exist within the database, an empty {@link Optional} is returned.
     *
     * @param staffNum The username of the {@link Librarian} to be retrieved
     * @return The requested {@link Librarian} wrapped in an {@link Optional}
     * if it is found in the database, or an empty {@link Optional} if not.
     */
    @Override
    public Optional<Librarian> getWithStaffNumber(Long staffNum) {
        List<Librarian> librarians = DatabaseUtils.getAll(Librarian.class, "staffNum", staffNum, sessionFactory);

        if (librarians.size() > 1) {
            throw new HibernateException("More than one Librarian retrieved from database with same staff number");
        }

        if (!librarians.isEmpty()) {
            return Optional.of(librarians.get(0));
        } else {
            return Optional.empty();
        }
    }

    /**
     * @return A {@link List} of all {@link Librarian}s stored in the database.
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<Librarian> getAll() {
        return DatabaseUtils.getAll(Librarian.class, sessionFactory);
    }

    /**
     * Persists a {@link Librarian} object to the database.
     *
     * @param librarian The {@link Librarian} object to be saved to the database.
     */
    @Override
    public void save(Librarian librarian) {
        sessionFactory.getCurrentSession().save(librarian);
    }

    /**
     * Updates a {@link Librarian} object already persisted in the database
     * with new data after being changed by the application.
     *
     * @param librarian The {@link Librarian} object to be updated in the database.
     */
    @Override
    public void update(Librarian librarian) {
        sessionFactory.getCurrentSession().update(librarian);
    }

    /**
     * Deletes a {@link Librarian} object from the database.
     *
     * @param librarian The {@link Librarian} object to be deleted from the database.
     */
    @Override
    public void delete(Librarian librarian) {
        sessionFactory.getCurrentSession().delete(librarian);
    }
}
