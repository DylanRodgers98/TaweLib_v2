package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.dao.UserDAO;
import com.crowvalley.tawelib.model.user.User;
import com.crowvalley.tawelib.model.fine.OutstandingFinesDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private UserDAO DAO;

    private FineService fineService;

    private PaymentService paymentService;

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
    public Optional<User> get(String username) {
        return DAO.get(username);
    }

    /**
     * @return A {@link List} of all {@link User}s retrieved by the DAO.
     */
    @Override
    public List<User> getAll() {
        return DAO.getAll();
    }

    @Override
    public List<String> getAllUsernames() {
        return DAO.getAllUsernames();
    }

    @Override
    public List<OutstandingFinesDTO> getAllUsersWithOutstandingFines() {
        List<OutstandingFinesDTO> usersWithOutstandingFines = new ArrayList<>();
        for (String username : getAllUsernames()) {
            Double finesForUser = fineService.getTotalFineAmountForUser(username);
            Double paymentsForUser = paymentService.getTotalPaymentAmountForUser(username);
            double outstandingFines = finesForUser - paymentsForUser;
            if (outstandingFines > 0) {
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
    public void save(User user) {
        DAO.save(user);
        LOGGER.info("User with username {} saved successfully", user.getUsername());
    }

    /**
     * Updates a {@link User} object already persisted in the database
     * with new data after being changed by the application.
     *
     * @param user The {@link User} object to be updated in the database.
     */
    @Override
    public void update(User user) {
        DAO.update(user);
        LOGGER.info("User with username {} updated successfully", user.getUsername());
    }

    /**
     * Deletes a {@link User} object from the database through the DAO.
     *
     * @param user The {@link User} object to be deleted from the database.
     */
    @Override
    public void delete(User user) {
        DAO.delete(user);
        LOGGER.info("User with username {} deleted successfully", user.getUsername());
    }

    @Override
    public void setDAO(UserDAO DAO) {
        this.DAO = DAO;
        LOGGER.info("{} DAO set to {}", this.getClass().getSimpleName(), DAO.getClass().getSimpleName());
    }

    public void setFineService(FineService fineService) {
        this.fineService = fineService;
        LOGGER.info("{} FineService set to {}", this.getClass().getSimpleName(), fineService.getClass().getSimpleName());
    }

    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
        LOGGER.info("{} PaymentService set to {}", this.getClass().getSimpleName(), paymentService.getClass().getSimpleName());
    }

}
