package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.dao.PaymentDAO;
import com.crowvalley.tawelib.model.fine.Payment;
import com.crowvalley.tawelib.model.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

/**
 * Service class for retrieving data about {@link Payment} objects
 * persisted in a database, using a Data Access Object (DAO) to perform
 * create, retrieve, and delete operations.
 *
 * @author Dylan Rodgers
 */
public class PaymentServiceImpl implements PaymentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentServiceImpl.class);

    private PaymentDAO DAO;

    /**
     * Retrieves a {@link Payment} from the DAO using the {@link Payment}'s
     * {@code id} and returns it wrapped in an {@link Optional}. If a
     * {@link Payment} with the passed {@code id} isn't retrieved from the
     * DAO, an empty {@link Optional} is returned.
     *
     * @param id The ID of the {@link Payment} to be retrieved
     * @return The requested {@link Payment} wrapped in an {@link Optional}
     * if it isn't retrieved by the DAO, or an empty {@link Optional} if not.
     */
    @Override
    public Optional<Payment> get(Long id) {
        return DAO.get(id);
    }

    /**
     * @return A {@link List} of all {@link Payment}s retrieved by the DAO.
     */
    @Override
    public List<Payment> getAll() {
        return DAO.getAll();
    }

    /**
     * Retrieves a {@link List} of all {@link Payment}s created, past and present,
     * for a given {@link User} stored in the database.
     *
     * @param username The ID of the {@link User} for which to generate the
     *               list of {@link Payment}s for.
     * @return A {@link List} of all {@link Payment}s stored in the database
     * for a given {@link User}.
     */
    @Override
    public List<Payment> getAllPaymentsForUser(String username) {
        return DAO.getAllPaymentsForUser(username);
    }

    @Override
    public Double getTotalPaymentAmountForUser(String username) {
        return DAO.getTotalPaymentAmountForUser(username);
    }

    /**
     * Persists a {@link Payment} object to the database through the DAO.
     *
     * @param payment The {@link Payment} object to be saved to the database.
     */
    @Override
    public void save(Payment payment) {
        DAO.save(payment);
        LOGGER.info("Payment with ID {} saved successfully", payment.getId());
    }

    /**
     * Deletes a {@link Payment} object from the database through the DAO.
     *
     * @param payment The {@link Payment} object to be deleted from the database.
     */
    @Override
    public void delete(Payment payment) {
        DAO.delete(payment);
        LOGGER.info("Payment with ID {} deleted successfully", payment.getId());
    }

    @Override
    public void setDAO(PaymentDAO DAO) {
        this.DAO = DAO;
        LOGGER.info("{} DAO set to {}", this.getClass().getSimpleName(), DAO.getClass().getSimpleName());
    }

}
