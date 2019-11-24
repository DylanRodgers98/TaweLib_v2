package com.crowvalley.tawelib.dao;

import com.crowvalley.tawelib.model.fine.Payment;
import com.crowvalley.tawelib.model.user.User;
import com.crowvalley.tawelib.util.DatabaseUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Data Access Object for retrieving data about {@link Payment} objects
 * persisted in a database. This class provides methods to perform create,
 * retrieve, and update operations on a database table that stores
 * information about {@link Payment} objects, using a Hibernate
 * {@link SessionFactory} object to access the database.
 *
 * @author Dylan Rodgers
 */
@Transactional
public class PaymentDAOImpl implements PaymentDAO {

    @Autowired
    private SessionFactory sessionFactory;

    /**
     * Retrieves a {@link Payment} from the database using the
     * {@link Payment}'s {@code id} and returns it wrapped in an
     * {@link Optional}. If a {@link Payment} with the passed {@code id}
     * doesn't exist within the database, an empty {@link Optional} is
     * returned.
     *
     * @param id The ID of the {@link Payment} to be retrieved
     * @return The requested {@link Payment} wrapped in an {@link Optional}
     * if it is found in the database, or an empty {@link Optional} if not.
     */
    @Override
    public Optional<Payment> get(Long id) {
        Payment payment = sessionFactory.getCurrentSession().get(Payment.class, id);
        return Optional.ofNullable(payment);
    }

    /**
     * @return A {@link List} of all {@link Payment}s stored in the database.
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<Payment> getAll() {
        return sessionFactory.getCurrentSession().createQuery("from Payment").list();
    }

    /**
     * Retrieves a {@link List} of all {@link Payment}s created for a given
     * {@link User} stored in the database.
     *
     * @param username The ID of the {@link User} for which to generate the
     *               list of {@link Payment}s for.
     * @return A {@link List} of all {@link Payment}s stored in the database
     * for a given {@link User}.
     */
    @Override
    public List<Payment> getAllPaymentsForUser(String username) {
        return DatabaseUtils.getAll(Payment.class, "username", username, sessionFactory);
    }

    /**
     * Persists a {@link Payment} object to the database.
     *
     * @param payment The {@link Payment} object to be saved to the database.
     */
    @Override
    public void save(Payment payment) {
        sessionFactory.getCurrentSession().save(payment);
    }

    /**
     * Deletes a {@link Payment} object from the database.
     *
     * @param payment The {@link Payment} object to be deleted from the database.
     */
    @Override
    public void delete(Payment payment) {
        sessionFactory.getCurrentSession().delete(payment);
    }
}
