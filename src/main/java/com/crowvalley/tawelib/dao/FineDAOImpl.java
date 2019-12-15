package com.crowvalley.tawelib.dao;

import com.crowvalley.tawelib.model.fine.Fine;
import com.crowvalley.tawelib.model.user.User;
import com.crowvalley.tawelib.util.DatabaseUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Data Access Object for retrieving data about {@link Fine} objects
 * persisted in a database. This class provides methods to perform create,
 * retrieve, and update operations on a database table that stores
 * information about {@link Fine} objects, using a Hibernate
 * {@link SessionFactory} object to access the database.
 *
 * @author Dylan Rodgers
 */
@Transactional
public class FineDAOImpl implements FineDAO {

    @Autowired
    private SessionFactory sessionFactory;

    /**
     * Retrieves a {@link Fine} from the database using the {@link Fine}'s
     * {@code id} and returns it wrapped in an {@link Optional}. If a
     * {@link Fine} with the passed {@code id} doesn't
     * exist within the database, an empty {@link Optional} is returned.
     *
     * @param id The ID of the {@link Fine} to be retrieved
     * @return The requested {@link Fine} wrapped in an {@link Optional}
     * if it is found in the database, or an empty {@link Optional} if not.
     */
    @Override
    public Optional<Fine> get(Long id) {
        Fine fine = sessionFactory.getCurrentSession().get(Fine.class, id);
        return Optional.ofNullable(fine);
    }

    /**
     * Retrieves a {@link List} of all {@link Fine}s created for a given
     * {@link User} stored in the database.
     *
     * @param username The ID of the {@link User} for which to generate the
     *               list of {@link Fine}s for.
     * @return A {@link List} of all {@link Fine}s stored in the database
     * for a given {@link User}.
     */
    @Override
    public List<Fine> getAllFinesForUser(String username) {
        return DatabaseUtils.getAll(Fine.class, "username", username, sessionFactory);
    }

    /**
     * @return A {@link List} of all {@link Fine}s stored in the database.
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<Fine> getAll() {
        return DatabaseUtils.getAll(Fine.class, sessionFactory);
    }

    /**
     * Persists a {@link Fine} object to the database.
     *
     * @param fine The {@link Fine} object to be saved to the database.
     */
    @Override
    public void save(Fine fine) {
        sessionFactory.getCurrentSession().save(fine);
    }

    /**
     * Deletes a {@link Fine} object from the database.
     *
     * @param fine The {@link Fine} object to be deleted from the database.
     */
    @Override
    public void delete(Fine fine) {
        sessionFactory.getCurrentSession().delete(fine);
    }
}
