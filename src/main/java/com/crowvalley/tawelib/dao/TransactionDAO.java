package com.crowvalley.tawelib.dao;

import com.crowvalley.tawelib.model.fine.Fine;

/**
 * Data Access Object interface for classes to implement that deal with
 * {@link Fine} objects persisted in a database.
 *
 * @author Dylan Rodgers
 */
public interface TransactionDAO extends BaseDAO {

    Double getTotalFineAmountForUser(String username);

    Double getTotalPaymentAmountForUser(String username);

}
