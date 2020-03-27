package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.dao.UserDAO;
import com.crowvalley.tawelib.model.user.Librarian;
import com.crowvalley.tawelib.model.user.User;
import com.crowvalley.tawelib.model.fine.OutstandingFinesDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * Service class for retrieving data about {@link User} objects
 * persisted in a database, using a Data Access Object (DAO) to perform
 * CRUD operations.
 *
 * @author Dylan Rodgers
 */
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private UserDAO userDAO;

    private TransactionService transactionService;

    /**
     * Retrieves a {@link User} from the DAO using the {@link User}'s
     * {@code username} and returns it wrapped in an {@link Optional}. If a
     * {@link User} with the passed {@code username} isn't retrieved from
     * the DAO, an empty {@link Optional} is returned.
     *
     * @param username The username of the {@link User} to be retrieved.
     * @return The requested {@link User} wrapped in an {@link Optional}
     * if it isn't retrieved by the DAO, or an empty {@link Optional} if not.
     */
    @Override
    public Optional<? extends User> getWithUsername(String username) {
        return userDAO.getWithUsername(username);
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
    public Optional<Librarian> getLibrarianUserWithStaffNumber(Long staffNum) {
        return userDAO.getLibrarianUserWithStaffNumber(staffNum);
    }

    /**
     * @return A {@link List} of all {@link User}s retrieved by the DAO.
     */
    @Override
    public List<? extends User> getAll() {
        return userDAO.getAll(User.class);
    }

    @Override
    public List<String> getAllUsernames() {
        return userDAO.getAllUsernames();
    }

    @Override
    public List<OutstandingFinesDTO> getAllUsersWithOutstandingFines() {
        List<OutstandingFinesDTO> usersWithOutstandingFines = new ArrayList<>();
        for (String username : getAllUsernames()) {
            BigDecimal finesForUser = transactionService.getTotalFinesAmountForUser(username);
            BigDecimal paymentsForUser = transactionService.getTotalPaymentsAmountForUser(username);
            BigDecimal outstandingFines = finesForUser.subtract(paymentsForUser);
            if (outstandingFines.compareTo(BigDecimal.ZERO) > 0) {
                usersWithOutstandingFines.add(new OutstandingFinesDTO(username, outstandingFines));
            }
        }
        return usersWithOutstandingFines;
    }

    /**
     * Persists a {@link User} object to the database through the DAO.
     *
     * @param user The {@link User} object to be saved to the database.
     */
    @Override
    public void saveOrUpdate(User user) {
        userDAO.saveOrUpdate(user);
        LOGGER.info("User with username {} saved successfully", user.getUsername());
    }

    /**
     * Deletes a {@link User} object from the database through the DAO.
     *
     * @param user The {@link User} object to be deleted from the database.
     */
    @Override
    public void delete(User user) {
        userDAO.delete(user);
        LOGGER.info("User with username {} deleted successfully", user.getUsername());
    }

    @Override
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
        LOGGER.info("{} UserDAO set to {}", this.getClass().getSimpleName(), userDAO.getClass().getSimpleName());
    }

    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
        LOGGER.info("{} FineService set to {}", this.getClass().getSimpleName(), transactionService.getClass().getSimpleName());
    }

}
