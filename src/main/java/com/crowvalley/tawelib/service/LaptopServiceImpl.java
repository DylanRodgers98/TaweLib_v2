package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.dao.ResourceDAO;
import com.crowvalley.tawelib.model.resource.Laptop;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;
import java.util.Optional;

/**
 * Service class for retrieving data about {@link Laptop} objects
 * persisted in a database, using a Data Access Object (DAO) to perform
 * CRUD operations.
 *
 * @author Dylan Rodgers
 */
public class LaptopServiceImpl implements ResourceService<Laptop> {

    private static final Logger LOGGER = LoggerFactory.getLogger(LaptopServiceImpl.class);

    @Qualifier("laptopDAO")
    @Autowired
    private ResourceDAO DAO;

    /**
     * Retrieves a {@link Laptop} from the DAO using the {@link Laptop}'s
     * {@code id} and returns it wrapped in an {@link Optional}. If a
     * {@link Laptop} with the passed {@code id} isn't retrieved from the
     * DAO, an empty {@link Optional} is returned.
     *
     * @param id The ID of the {@link Laptop} to be retrieved.
     * @return The requested {@link Laptop} wrapped in an {@link Optional}
     * if it isn't retrieved by the DAO, or an empty {@link Optional} if not.
     */
    @Override
    public Optional<Laptop> get(Long id) {
        Optional<Laptop> laptop = DAO.get(id);
        if (laptop.isPresent()) {
            LOGGER.info("Laptop with ID {} retrieved successfully", id);
            return laptop;
        } else {
            LOGGER.warn("Could not find laptop with ID {}", id);
            return Optional.empty();
        }
    }

    /**
     * @return A {@link List} of all {@link Laptop}s retrieved by the DAO.
     */
    @Override
    public List<Laptop> getAll() {
        List<Laptop> laptops = DAO.getAll();
        if (!laptops.isEmpty()) {
            LOGGER.info("All laptops retrieved successfully");
            return laptops;
        } else {
            LOGGER.warn("No laptops found");
            return laptops;
        }
    }

    /**
     * Persists a {@link Laptop} object to the database through the DAO.
     *
     * @param laptop The {@link Laptop} object to be saved to the database.
     */
    @Override
    public void save(Laptop laptop) {
        DAO.save(laptop);
        LOGGER.info("Laptop with ID {} saved successfully", laptop.getId());
    }

    /**
     * Updates a {@link Laptop} object already persisted in the database
     * with new data after being changed by the application.
     *
     * @param laptop The {@link Laptop} object to be updated in the database.
     */
    @Override
    public void update(Laptop laptop) {
        DAO.update(laptop);
        LOGGER.info("Laptop with ID {} updated successfully", laptop.getId());
    }

    /**
     * Deletes a {@link Laptop} object from the database through the DAO.
     *
     * @param laptop The {@link Laptop} object to be deleted from the database.
     */
    @Override
    public void delete(Laptop laptop) {
        DAO.delete(laptop);
        LOGGER.info("Laptop with ID {} deleted successfully", laptop.getId());
    }

    @Override
    public void setDAO(ResourceDAO DAO) {
        this.DAO = DAO;
        LOGGER.info("LaptopServiceImpl DAO set to {}", DAO.getClass());
    }
}
