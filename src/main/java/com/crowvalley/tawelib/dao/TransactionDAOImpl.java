package com.crowvalley.tawelib.dao;

import com.crowvalley.tawelib.model.fine.Fine;
import com.crowvalley.tawelib.model.fine.Payment;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.math.BigDecimal;

/**
 * Data Access Object for retrieving data about {@link Fine} objects
 * persisted in a database. This class provides methods to perform create,
 * retrieve, and update operations on a database table that stores
 * information about {@link Fine} objects, using a Hibernate
 * {@link SessionFactory} object to access the database.
 *
 * @author Dylan Rodgers
 */
public class TransactionDAOImpl extends BaseDAOImpl implements TransactionDAO {

    @Override
    public BigDecimal getTotalFineAmountForUser(String username) {
        return (BigDecimal) sessionFactory.getCurrentSession()
                .createCriteria(Fine.class)
                .add(Restrictions.eq("username", username))
                .setProjection(Projections.sum("amount"))
                .uniqueResult();
    }

    @Override
    public BigDecimal getTotalPaymentAmountForUser(String username) {
        return (BigDecimal) sessionFactory.getCurrentSession()
                .createCriteria(Payment.class)
                .add(Restrictions.eq("username", username))
                .setProjection(Projections.sum("amount"))
                .uniqueResult();
    }

}
