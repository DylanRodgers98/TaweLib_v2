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
public interface LoanDAO {

    Optional<Loan> get(Long loanId);

    List<Loan> getAllLoansForCopy(Long copyId);

    Loan getCurrentLoanForCopy(Long copyId);

    List<Loan> getAllLoansForUser(String username);

    List<Loan> getAll();

    void save(Loan loan);

    void update(Loan loan);

    void delete(Loan loan);

}
