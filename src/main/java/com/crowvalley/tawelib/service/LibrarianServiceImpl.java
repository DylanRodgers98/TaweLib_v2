package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.dao.LibrarianDAO;
import com.crowvalley.tawelib.model.user.Librarian;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

/**
 * Service class for retrieving data about {@link Librarian} objects
 * persisted in a database, using a Data Access Object (DAO) to perform
 * CRUD operations.
 *
 * @author Dylan Rodgers
 */
public class LibrarianServiceImpl implements LibrarianService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LibrarianServiceImpl.class);

    @Autowired
    private LibrarianDAO DAO;

    /**
     * Retrieves a {@link Librarian} from the DAO using the {@link Librarian}'s
     * {@code username} and returns it wrapped in an {@link Optional}. If a
     * {@link Librarian} with the passed {@code username} isn't retrieved from
     * the DAO, an empty {@link Optional} is returned.
     *
     * @param username The username of the {@link Librarian} to be retrieved.
     * @return The requested {@link Librarian} wrapped in an {@link Optional}
     * if it isn't retrieved by the DAO, or an empty {@link Optional} if not.
     */
    @Override
    public Optional<Librarian> getWithUsername(String username) {
        Optional<Librarian> librarian = DAO.getWithUsername(username);
        if (librarian.isPresent()) {
            LOGGER.info("Librarian with username {} retrieved successfully", username);
            return librarian;
        } else {
            LOGGER.warn("Could not find librarian with username {}", username);
            return Optional.empty();
        }
    }

    /**
     * Retrieves a {@link Librarian} from the DAO using the {@link Librarian}'s
     * {@code staffNum} and returns it wrapped in an {@link Optional}. If a
     * {@link Librarian} with the passed {@code staffNum} isn't retrieved from
     * the DAO, an empty {@link Optional} is returned.
     *
     * @param staffNum The {@code staffNum} of the {@link Librarian} to be
     *                 retrieved.
     * @return The requested {@link Librarian} wrapped in an {@link Optional}
     * if it isn't retrieved by the DAO, or an empty {@link Optional} if not.
     */
    @Override
    public Optional<Librarian> getWithStaffNumber(Long staffNum) {
        Optional<Librarian> librarian = DAO.getWithStaffNumber(staffNum);
        if (librarian.isPresent()) {
            LOGGER.info("Librarian with staff number {} retrieved successfully", staffNum);
            return librarian;
        } else {
            LOGGER.warn("Could not find librarian with staff number {}", staffNum);
            return Optional.empty();
        }
    }

    /**
     * @return A {@link List} of all {@link Librarian}s retrieved by the DAO.
     */
    @Override
    public List<Librarian> getAll() {
        List<Librarian> users = DAO.getAll();
        if (!users.isEmpty()) {
            LOGGER.info("All users retrieved successfully");
            return users;
        } else {
            LOGGER.warn("No users found");
            return users;
        }
    }

    /**
     * Persists a {@link Librarian} object to the database through the DAO,
     * firstly checking is the passed in {@link Librarian}'s {@code staffNum}
     * isn't a duplicate of an existing {@link Librarian}'s {@code staffNum}.
     *
     * @param librarian The {@link Librarian} object to be saved to the database.
     */
    @Override
    public void save(Librarian librarian) {
        Optional<Librarian> librarianForCheckingDuplicateStaffNumber = DAO.getWithStaffNumber(librarian.getStaffNum());
        if (librarianForCheckingDuplicateStaffNumber.isPresent()) {
            LOGGER.warn("A librarian already has staff number {}", librarian.getStaffNum());
            throw new IllegalArgumentException("Trying to create Librarian with duplicate staff number");
        } else {
            DAO.save(librarian);
            LOGGER.info("Librarian with username {} and staff number {} saved successfully",
                    librarian.getUsername(), librarian.getStaffNum());
        }
    }

    /**
     * Updates a {@link Librarian} object already persisted in the database
     * with new data after being changed by the application.
     *
     * @param librarian The {@link Librarian} object to be updated in the database.
     */
    @Override
    public void update(Librarian librarian) {
        DAO.update(librarian);
        LOGGER.info("Librarian with username {} and staff number {} updated successfully",
                librarian.getUsername(), librarian.getStaffNum());
    }

    /**
     * Deletes a {@link Librarian} object from the database through the DAO.
     *
     * @param librarian The {@link Librarian} object to be deleted from the database.
     */
    @Override
    public void delete(Librarian librarian) {
        DAO.delete(librarian);
        LOGGER.info("Librarian with username {} and staff number {} deleted successfully",
                librarian.getUsername(), librarian.getStaffNum());
    }

    @Override
    public void setDAO(LibrarianDAO DAO) {
        this.DAO = DAO;
        LOGGER.info("UserServiceImpl DAO set to {}", DAO.getClass());
    }

}
