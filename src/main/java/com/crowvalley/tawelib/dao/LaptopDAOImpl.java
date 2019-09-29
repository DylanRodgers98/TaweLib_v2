package com.crowvalley.tawelib.dao;

import com.crowvalley.tawelib.model.resource.Laptop;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

/**
 * Data Access Object for retrieving data about {@link Laptop} objects
 * persisted in a database. This class provides methods to perform CRUD
 * operations on a database table that stores information about
 * {@link Laptop} objects, using a Hibernate {@link SessionFactory} object
 * to access the database.
 *
 * @author Dylan Rodgers
 */
public class LaptopDAOImpl implements ResourceDAO<Laptop> {

    @Autowired
    private SessionFactory sessionFactory;

    /**
     * Retrieves a {@link Laptop} from the database using the
     * {@link Laptop}'s {@code id} and returns it wrapped in an
     * {@link Optional}. If a {@link Laptop} with the passed {@code id}
     * doesn't exist within the database, an empty {@link Optional} is
     * returned.
     *
     * @param id The ID of the {@link Laptop} to be retrieved
     * @return The requested {@link Laptop} wrapped in an {@link Optional}
     * if it is found in the database, or an empty {@link Optional} if not.
     */
    @Override
    public Optional<Laptop> get(Long id) {
        Laptop laptop = sessionFactory.getCurrentSession().get(Laptop.class, id);
        return Optional.ofNullable(laptop);
    }

    /**
     * @return A {@link List} of all {@link Laptop}s stored in the database.
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<Laptop> getAll() {
        return sessionFactory.getCurrentSession().createQuery("from Laptop").list();
    }

    /**
     * Persists a {@link Laptop} object to the database.
     *
     * @param laptop The {@link Laptop} object to be saved to the database.
     */
    @Override
    public void save(Laptop laptop) {
        sessionFactory.getCurrentSession().save(laptop);
    }

    /**
     * Updates a {@link Laptop} object already persisted in the database
     * with new data after being changed by the application.
     *
     * @param laptop The {@link Laptop} object to be updated in the database.
     */
    @Override
    public void update(Laptop laptop) {
        sessionFactory.getCurrentSession().update(laptop);
    }

    /**
     * Deletes a {@link Laptop} object from the database.
     *
     * @param laptop The {@link Laptop} object to be deleted from the database.
     */
    @Override
    public void delete(Laptop laptop) {
        sessionFactory.getCurrentSession().delete(laptop);
    }
}
