package com.crowvalley.tawelib.dao;

import com.crowvalley.tawelib.model.resource.Copy;
import com.crowvalley.tawelib.model.resource.Loan;
import com.crowvalley.tawelib.model.user.User;
import com.crowvalley.tawelib.util.DatabaseUtils;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

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
public class LoanDAOImpl extends BaseDAOImpl implements LoanDAO {

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
                .add(Restrictions.isNull("returnDate"))
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

}
