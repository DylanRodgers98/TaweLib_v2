package com.crowvalley.tawelib.dao;

import com.crowvalley.tawelib.model.fine.Fine;
import com.crowvalley.tawelib.model.fine.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Data Access Object interface for classes to implement that deal with
 * {@link Fine} objects persisted in a database.
 *
 * @author Dylan Rodgers
 */
public interface TransactionDAO extends BaseDAO {

    List<Transaction> getAllTransactionsForUser(String username);

    BigDecimal getTotalFinesAmountForUser(String username);

    BigDecimal getTotalPaymentsAmountForUser(String username);

    List<Transaction> search(String username, LocalDateTime startDate, LocalDateTime endDate);

}
