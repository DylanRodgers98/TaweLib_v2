package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.dao.TransactionDAO;
import com.crowvalley.tawelib.model.fine.Fine;
import com.crowvalley.tawelib.model.fine.Payment;
import com.crowvalley.tawelib.model.fine.Transaction;
import com.crowvalley.tawelib.model.resource.Copy;

import java.util.List;
import java.util.Optional;

/**
 * Interface for service classes to implement that deal with
 * {@link Fine} objects persisted in a database.
 *
 * @author Dylan Rodgers
 */
public interface TransactionService {

    Optional<? extends Transaction> get(Long id);

    List<? extends Transaction> getAll();

    void save(Transaction transaction);

    void delete(Transaction transaction);

    Optional<Copy> getCopyFromFine(Fine fine);

    Double getTotalFineAmountForUser(String username);

    Double getTotalPaymentAmountForUser(String username);

    void setDAO(TransactionDAO DAO);
}
