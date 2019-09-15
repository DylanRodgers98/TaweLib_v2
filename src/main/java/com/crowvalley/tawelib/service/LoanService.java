package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.dao.LoanDAO;
import com.crowvalley.tawelib.model.resource.Loan;

import java.util.List;
import java.util.Optional;

public interface LoanService {

    Optional<Loan> get(Long loanId);

    List<Loan> getAllLoansForCopy(Long copyId);

    List<Loan> getAll();

    void save(Loan loan);

    void update(Loan loan);

    void delete(Loan loan);

    void setDAO(LoanDAO DAO);

    void endLoan(Loan loan);
}
