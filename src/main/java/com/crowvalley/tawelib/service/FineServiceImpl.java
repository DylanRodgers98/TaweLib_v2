package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.dao.FineDAO;
import com.crowvalley.tawelib.model.fine.Fine;
import com.crowvalley.tawelib.model.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

/**
 * Service class for retrieving data about {@link Fine} objects
 * persisted in a database, using a Data Access Object (DAO) to perform
 * create, retrieve, and delete operations.
 *
 * @author Dylan Rodgers
 */
public class FineServiceImpl implements FineService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FineServiceImpl.class);

    @Autowired
    private FineDAO DAO;

    /**
     * Retrieves a {@link Fine} from the DAO using the {@link Fine}'s
     * {@code id} and returns it wrapped in an {@link Optional}. If a
     * {@link Fine} with the passed {@code id} isn't retrieved from the
     * DAO, an empty {@link Optional} is returned.
     *
     * @param id The ID of the {@link Fine} to be retrieved
     * @return The requested {@link Fine} wrapped in an {@link Optional}
     * if it isn't retrieved by the DAO, or an empty {@link Optional} if not.
     */
    @Override
    public Optional<Fine> get(Long id) {
        Optional<Fine> fine = DAO.get(id);
        if (fine.isPresent()) {
            LOGGER.info("Fine with ID {} retrieved successfully", id);
            return fine;
        } else {
            LOGGER.warn("Could not find fine with ID {}", id);
            return Optional.empty();
        }
    }

    /**
     * Retrieves a {@link List} of all {@link Fine}s created, past and present,
     * for a given {@link User} stored in the database.
     *
     * @param username The ID of the {@link User} for which to generate the
     *               list of {@link Fine}s for.
     * @return A {@link List} of all {@link Fine}s stored in the database
     * for a given {@link User}.
     */
    @Override
    public List<Fine> getAllFinesForUser(String username) {
        List<Fine> fines = DAO.getAllFinesForUser(username);
        if (!fines.isEmpty()) {
            LOGGER.info("All fines for user {} retrieved successfully", username);
        } else {
            LOGGER.warn("No fines found for user {}", username);
        }
        return fines;
    }

    /**
     * @return A {@link List} of all {@link Fine}s retrieved by the DAO.
     */
    @Override
    public List<Fine> getAll() {
        List<Fine> fines = DAO.getAll();
        if (!fines.isEmpty()) {
            LOGGER.info("All fines retrieved successfully");
        } else {
            LOGGER.warn("No fines found");
        }
        return fines;
    }

    /**
     * Persists a {@link Fine} object to the database through the DAO.
     *
     * @param fine The {@link Fine} object to be saved to the database.
     */
    @Override
    public void save(Fine fine) {
        DAO.save(fine);
        LOGGER.info("Fine with ID {} saved successfully", fine.getId());
    }

    /**
     * Deletes a {@link Fine} object from the database through the DAO.
     *
     * @param fine The {@link Fine} object to be deleted from the database.
     */
    @Override
    public void delete(Fine fine) {
        DAO.delete(fine);
        LOGGER.info("Fine with ID {} deleted successfully", fine.getId());
    }

    @Override
    public void setDAO(FineDAO DAO) {
        this.DAO = DAO;
        LOGGER.info("FineServiceImpl DAO set to {}", DAO.getClass());
    }

}
