package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.dao.LoanDAO;
import com.crowvalley.tawelib.model.resource.Copy;
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

    List<Loan> getAll();

    void createLoanForCopy(Copy copy, String borrowerUsername);

    void saveOrUpdate(Loan loan);

    void delete(Loan loan);

    void endLoan(Loan loan);

    List<Loan> getAllLoansForCopy(Long copyId);

    Optional<Loan> getCurrentLoanForCopy(Long copyId);

    boolean isCopyOnLoan(Long copyId);

    String getUsernameOfCurrentBorrowerForCopy(Long copyId);

    List<Loan> getAllLoansForUser(String username);

    void setDAO(LoanDAO DAO);
}
