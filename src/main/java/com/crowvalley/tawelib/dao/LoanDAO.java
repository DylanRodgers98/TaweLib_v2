package com.crowvalley.tawelib.dao;

import com.crowvalley.tawelib.model.resource.Copy;
import com.crowvalley.tawelib.model.resource.Loan;

import java.util.List;
import java.util.Optional;

public interface LoanDAO {

    Optional<Loan> get(Long loanId);

    List<Loan> getAllLoansForCopy(Long copyId);

    List<Loan> getAll();

    void save(Loan loan);

    void update(Loan loan);

    void delete(Loan loan);

}
