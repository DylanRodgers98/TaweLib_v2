package com.crowvalley.tawelib.dao;

import com.crowvalley.tawelib.model.resource.Dvd;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Data Access Object for retrieving data about {@link Dvd} objects
 * persisted in a database. This class provides methods to perform CRUD
 * operations on a database table that stores information about
 * {@link Dvd} objects, using a Hibernate {@link SessionFactory} object
 * to access the database.
 *
 * @author Dylan Rodgers
 */
@Transactional
public class DvdDAOImpl implements ResourceDAO<Dvd> {

    @Autowired
    private SessionFactory sessionFactory;

    /**
     * Retrieves a {@link Dvd} from the database using the {@link Dvd}'s
     * {@code id} and returns it wrapped in an {@link Optional}. If a
     * {@link Dvd} with the passed {@code id} doesn't
     * exist within the database, an empty {@link Optional} is returned.
     *
     * @param id The ID of the {@link Dvd} to be retrieved
     * @return The requested {@link Dvd} wrapped in an {@link Optional}
     * if it is found in the database, or an empty {@link Optional} if not.
     */
    @Override
    public Optional<Dvd> get(Long id) {
        Dvd dvd = sessionFactory.getCurrentSession().get(Dvd.class, id);
        return Optional.ofNullable(dvd);
    }

    /**
     * @return A {@link List} of all {@link Dvd}s stored in the database.
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<Dvd> getAll() {
        return sessionFactory.getCurrentSession().createQuery("from Dvd").list();
    }

    /**
     * Persists a {@link Dvd} object to the database.
     *
     * @param dvd The {@link Dvd} object to be saved to the database.
     */
    @Override
    public void save(Dvd dvd) {
        sessionFactory.getCurrentSession().save(dvd);
    }

    /**
     * Updates a {@link Dvd} object already persisted in the database
     * with new data after being changed by the application.
     *
     * @param dvd The {@link Dvd} object to be updated in the database.
     */
    @Override
    public void update(Dvd dvd) {
        sessionFactory.getCurrentSession().update(dvd);
    }

    /**
     * Deletes a {@link Dvd} object from the database.
     *
     * @param dvd The {@link Dvd} object to be deleted from the database.
     */
    @Override
    public void delete(Dvd dvd) {
        sessionFactory.getCurrentSession().delete(dvd);
    }
}
