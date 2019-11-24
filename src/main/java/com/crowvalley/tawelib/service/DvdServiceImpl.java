package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.dao.ResourceDAO;
import com.crowvalley.tawelib.model.resource.Dvd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;
import java.util.Optional;

/**
 * Service class for retrieving data about {@link Dvd} objects
 * persisted in a database, using a Data Access Object (DAO) to perform
 * CRUD operations.
 *
 * @author Dylan Rodgers
 */
public class DvdServiceImpl implements ResourceService<Dvd> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DvdServiceImpl.class);

    @Qualifier("dvdDAO")
    @Autowired
    private ResourceDAO<Dvd> DAO;

    /**
     * Retrieves a {@link Dvd} from the DAO using the {@link Dvd}'s
     * {@code id} and returns it wrapped in an {@link Optional}. If a
     * {@link Dvd} with the passed {@code id} isn't retrieved from the
     * DAO, an empty {@link Optional} is returned.
     *
     * @param id The ID of the {@link Dvd} to be retrieved
     * @return The requested {@link Dvd} wrapped in an {@link Optional}
     * if it isn't retrieved by the DAO, or an empty {@link Optional} if not.
     */
    @Override
    public Optional<Dvd> get(Long id) {
        Optional<Dvd> dvd = DAO.get(id);
        if (dvd.isPresent()) {
            LOGGER.info("DVD with ID {} retrieved successfully", id);
            return dvd;
        } else {
            LOGGER.warn("Could not find DVD with ID {}", id);
            return Optional.empty();
        }
    }

    /**
     * @return A {@link List} of all {@link Dvd}s retrieved by the DAO.
     */
    @Override
    public List<Dvd> getAll() {
        List<Dvd> dvds = DAO.getAll();
        if (!dvds.isEmpty()) {
            LOGGER.info("All DVDs retrieved successfully");
            return dvds;
        } else {
            LOGGER.warn("No DVDs found");
            return dvds;
        }
    }

    /**
     * Persists a {@link Dvd} object to the database through the DAO.
     *
     * @param dvd The {@link Dvd} object to be saved to the database.
     */
    @Override
    public void save(Dvd dvd) {
        DAO.save(dvd);
        LOGGER.info("DVD with ID {} saved successfully", dvd.getId());
    }

    /**
     * Updates a {@link Dvd} object already persisted in the database
     * with new data after being changed by the application.
     *
     * @param dvd The {@link Dvd} object to be updated in the database.
     */
    @Override
    public void update(Dvd dvd) {
        DAO.update(dvd);
        LOGGER.info("DVD with ID {} updated successfully", dvd.getId());
    }

    /**
     * Deletes a {@link Dvd} object from the database through the DAO.
     *
     * @param dvd The {@link Dvd} object to be deleted from the database.
     */
    @Override
    public void delete(Dvd dvd) {
        DAO.delete(dvd);
        LOGGER.info("DVD with ID {} saved successfully", dvd.getId());
    }

    @Override
    public void setDAO(ResourceDAO<Dvd> DAO) {
        this.DAO = DAO;
        LOGGER.info("DvdServiceImpl DAO set to {}", DAO.getClass());
    }
}
