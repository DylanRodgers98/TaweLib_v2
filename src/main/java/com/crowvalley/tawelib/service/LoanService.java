package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.dao.LoanDAO;
import com.crowvalley.tawelib.model.resource.Loan;

import java.util.List;
import java.util.Optional;

/**
 * Interface for service classes to implement that deal with
 * {@link Loan} objects persisted in a database.
 *
 * @author Dylan Rodgers
 */
public interface LoanService {

    Optional<Loan> get(Long loanId);

    List<Loan> getAllLoansForCopy(Long copyId);

    Loan getCurrentLoanForCopy(Long copyId);

    List<Loan> getAllLoansForUser(String username);

    List<Loan> getAll();

    void save(Loan loan);

    void update(Loan loan);

    void delete(Loan loan);

    void setDAO(LoanDAO DAO);

    void endLoan(Loan loan);
}
