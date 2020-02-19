package com.crowvalley.tawelib.dao;

import com.crowvalley.tawelib.model.resource.Loan;

import java.util.List;
import java.util.Optional;

/**
 * Data Access Object interface for classes to implement that deal with
 * {@link Loan} objects persisted in a database.
 *
 * @author Dylan Rodgers
 */
public interface LoanDAO extends BaseDAO {

    List<Loan> getAllLoansForCopy(Long copyId);

    Optional<Loan> getCurrentLoanForCopy(Long copyId);

    List<Loan> getAllLoansForUser(String username);

}
