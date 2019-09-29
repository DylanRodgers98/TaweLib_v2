package com.crowvalley.tawelib.dao;

import com.crowvalley.tawelib.model.user.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

/**
 * Data Access Object for retrieving data about {@link User} objects
 * persisted in a database. This class provides methods to perform CRUD
 * operations on a database table that stores information about {@link User}
 * objects, using a Hibernate {@link SessionFactory} object to access
 * the database.
 *
 * @author Dylan Rodgers
 */
public class UserDAOImpl implements UserDAO {

    @Autowired
    private SessionFactory sessionFactory;

    /**
     * Retrieves a {@link User} from the database using the
     * {@link User}'s {@code username} and returns it wrapped in an
     * {@link Optional}. If a {@link User} with the passed {@code username}
     * doesn't exist within the database, an empty {@link Optional} is returned.
     *
     * @param username The username of the {@link User} to be retrieved
     * @return The requested {@link User} wrapped in an {@link Optional}
     * if it is found in the database, or an empty {@link Optional} if not.
     */
    @Override
    public Optional<User> get(String username) {
        User user = sessionFactory.getCurrentSession().get(User.class, username);
        return Optional.ofNullable(user);
    }

    /**
     * @return A {@link List} of all {@link User}s stored in the database.
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAll() {
        return sessionFactory.getCurrentSession().createQuery("from User").list();
    }

    /**
     * Persists a {@link User} object to the database.
     *
     * @param user The {@link User} object to be saved to the database.
     */
    @Override
    public void save(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    /**
     * Updates a {@link User} object already persisted in the database
     * with new data after being changed by the application.
     *
     * @param user The {@link User} object to be updated in the database.
     */
    @Override
    public void update(User user) {
        sessionFactory.getCurrentSession().update(user);
    }

    /**
     * Deletes a {@link User} object from the database.
     *
     * @param user The {@link User} object to be deleted from the database.
     */
    @Override
    public void delete(User user) {
        sessionFactory.getCurrentSession().delete(user);
    }
}
