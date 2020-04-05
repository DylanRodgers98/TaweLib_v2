package com.crowvalley.tawelib.dao;

import com.crowvalley.tawelib.model.resource.Loan;
import com.crowvalley.tawelib.model.user.User;
import com.crowvalley.tawelib.util.ListUtils;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;

import java.time.LocalDateTime;
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
        return ListUtils.castList(Loan.class,
                sessionFactory.getCurrentSession()
                        .createCriteria(Loan.class)
                        .add(Restrictions.eq("borrowerUsername", username))
                        .list());
    }

    @Override
    public List<Loan> search(String username, LocalDateTime startDate, LocalDateTime endDate) {
        if (username == null && startDate == null && endDate == null) {
            throw new IllegalArgumentException("Search must be carried out with either a username, start & end date, or both");
        }

        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Loan.class);

        if (startDate != null  && endDate != null) {
            Disjunction dateDisjunction = Restrictions.disjunction();
            dateDisjunction.add(Restrictions.between("startDate", startDate, endDate));
            dateDisjunction.add(Restrictions.between("endDate", startDate, endDate));
            dateDisjunction.add(Restrictions.between("returnDate", startDate, endDate));
            criteria.add(dateDisjunction);
        }
        if (username != null) {
            criteria.add(Restrictions.ilike("borrowerUsername", username));
        }

        return ListUtils.castList(Loan.class, criteria.list());
    }

}
