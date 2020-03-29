package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.dao.TransactionDAO;
import com.crowvalley.tawelib.model.fine.Fine;
import com.crowvalley.tawelib.model.fine.Transaction;
import com.crowvalley.tawelib.model.resource.Copy;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Interface for service classes to implement that deal with
 * {@link Fine} objects persisted in a database.
 *
 * @author Dylan Rodgers
 */
public interface TransactionService {

    List<? extends Transaction> getAll();

    List<? extends Transaction> getAllTransactionsForUser(String username);

    void save(Transaction transaction);

    Optional<Copy> getCopyFromFine(Fine fine);

    BigDecimal getTotalFinesAmountForUser(String username);

    BigDecimal getTotalPaymentsAmountForUser(String username);

    List<Transaction> search(LocalDateTime startDate, LocalDateTime endDate);

    List<Transaction> search(String username, LocalDateTime startDate, LocalDateTime endDate);

    void setTransactionDAO(TransactionDAO transactionDAO);
}
