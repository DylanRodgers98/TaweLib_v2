package com.crowvalley.tawelib.dao;

import com.crowvalley.tawelib.model.resource.Copy;
import com.crowvalley.tawelib.util.DatabaseUtils;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Data Access Object for retrieving data about {@link Copy} objects
 * persisted in a database. This class provides methods to perform CRUD
 * operations on a database table that stores information about {@link Copy}
 * objects, using a Hibernate {@link SessionFactory} object to access
 * the database.
 *
 * @author Dylan Rodgers
 */
@Transactional
public class CopyDAOImpl implements CopyDAO {

    @Autowired
    private SessionFactory sessionFactory;

    /**
     * Retrieves a {@link Copy} from the database using the {@link Copy}'s
     * {@code id} and returns it wrapped in an {@link Optional}. If a
     * {@link Copy} with the passed {@code id} doesn't
     * exist within the database, an empty {@link Optional} is returned.
     *
     * @param id The ID of the {@link Copy} to be retrieved
     * @return The requested {@link Copy} wrapped in an {@link Optional}
     * if it is found in the database, or an empty {@link Optional} if not.
     */
    @Override
    public Optional<Copy> get(Long id) {
        Copy copy = sessionFactory.getCurrentSession().get(Copy.class, id);
        return Optional.ofNullable(copy);
    }

    /**
     * @return A {@link List} of all {@link Copy}s stored in the database.
     */
    @Override
    public List<Copy> getAll() {
        return DatabaseUtils.getAll(Copy.class, sessionFactory);
    }

    @Override
    public List<Copy> getAllCopiesForResource(Long resourceId) {
        return DatabaseUtils.getAll(Copy.class, sessionFactory, "resourceId", resourceId);
    }

    /**
     * Persists a {@link Copy} object to the database.
     *
     * @param copy The {@link Copy} object to be saved to the database.
     */
    @Override
    public void save(Copy copy) {
        sessionFactory.getCurrentSession().save(copy);
    }

    /**
     * Updates a {@link Copy} object already persisted in the database
     * with new data after being changed by the application.
     *
     * @param copy The {@link Copy} object to be updated in the database.
     */
    @Override
    public void update(Copy copy) {
        sessionFactory.getCurrentSession().update(copy);
    }

    /**
     * Deletes a {@link Copy} object from the database.
     *
     * @param copy The {@link Copy} object to be deleted from the database.
     */
    @Override
    public void delete(Copy copy) {
        sessionFactory.getCurrentSession().delete(copy);
    }
}
