package com.crowvalley.tawelib.dao;

import com.crowvalley.tawelib.model.fine.Fine;

import java.math.BigDecimal;

/**
 * Data Access Object interface for classes to implement that deal with
 * {@link Fine} objects persisted in a database.
 *
 * @author Dylan Rodgers
 */
public interface TransactionDAO extends BaseDAO {

    BigDecimal getTotalFineAmountForUser(String username);

    BigDecimal getTotalPaymentAmountForUser(String username);

}
