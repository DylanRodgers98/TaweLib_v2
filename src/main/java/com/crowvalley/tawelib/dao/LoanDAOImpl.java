package com.crowvalley.tawelib.dao;

import com.crowvalley.tawelib.model.resource.Copy;
import com.crowvalley.tawelib.model.resource.Loan;
import com.crowvalley.tawelib.model.user.User;
import com.crowvalley.tawelib.util.DatabaseUtils;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Data Access Object for retrieving data about {@link Loan} objects
 * persisted in a database. This class provides methods to perform CRUD
 * operations on a database table that stores information about {@link Loan}
 * objects, using a Hibernate {@link SessionFactory} object to access
 * the database.
 *
 * @author Dylan Rodgers
 */
@Transactional
public class LoanDAOImpl implements LoanDAO {

    @Autowired
    private SessionFactory sessionFactory;

    /**
     * Retrieves a {@link Loan} from the database using the {@link Loan}'s
     * {@code id} and returns it wrapped in an {@link Optional}. If a
     * {@link Loan} with the passed {@code id} doesn't
     * exist within the database, an empty {@link Optional} is returned.
     *
     * @param loanId The ID of the {@link Loan} to be retrieved
     * @return The requested {@link Loan} wrapped in an {@link Optional}
     * if it is found in the database, or an empty {@link Optional} if not.
     */
    @Override
    public Optional<Loan> get(Long loanId) {
        Loan loan = sessionFactory.getCurrentSession().get(Loan.class, loanId);
        return Optional.ofNullable(loan);
    }

    /**
     * Retrieves a {@link List} of all {@link Loan}s created, past and present,
     * for a given {@link Copy} stored in the database.
     *
     * @param copyId The ID of the {@link Copy} for which to generate the
     *               list of {@link Loan}s for.
     * @return A {@link List} of all {@link Loan}s stored in the database
     * for a given {@link Copy}.
     */
    @Override
    public List<Loan> getAllLoansForCopy(Long copyId) {
        return DatabaseUtils.getAll(Loan.class, sessionFactory, "copyId", copyId);
    }

    @Override
    public Optional<Loan> getCurrentLoanForCopy(Long copyId) {
        Loan loan = (Loan) sessionFactory.getCurrentSession()
                .createCriteria(Loan.class)
                .add(Restrictions.eq("copyId", copyId))
                .add(Restrictions.isNull("borrowerUsername"))
                .uniqueResult();
        return Optional.ofNullable(loan);
    }

    /**
     * Retrieves a {@link List} of all {@link Loan}s created, past and present,
     * for a given {@link User} stored in the database.
     *
     * @param username The ID of the {@link User} for which to generate the
     *               list of {@link Loan}s for.
     * @return A {@link List} of all {@link Loan}s stored in the database
     * for a given {@link User}.
     */
    @Override
    public List<Loan> getAllLoansForUser(String username) {
        return DatabaseUtils.getAll(Loan.class, sessionFactory, "borrowerUsername", username);
    }

    /**
     * @return A {@link List} of all {@link Loan}s stored in the database.
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<Loan> getAll() {
        return DatabaseUtils.getAll(Loan.class, sessionFactory);
    }

    /**
     * Persists a {@link Loan} object to the database.
     *
     * @param loan The {@link Loan} object to be saved to the database.
     */
    @Override
    public void save(Loan loan) {
        sessionFactory.getCurrentSession().save(loan);
    }

    /**
     * Updates a {@link Loan} object already persisted in the database
     * with new data after being changed by the application.
     *
     * @param loan The {@link Loan} object to be updated in the database.
     */
    @Override
    public void update(Loan loan) {
        sessionFactory.getCurrentSession().update(loan);
    }

    /**
     * Deletes a {@link Loan} object from the database.
     *
     * @param loan The {@link Loan} object to be deleted from the database.
     */
    @Override
    public void delete(Loan loan) {
        sessionFactory.getCurrentSession().delete(loan);
    }
}
