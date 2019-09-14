package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.dao.LoanDAO;
import com.crowvalley.tawelib.model.resource.Loan;

import java.util.List;
import java.util.Optional;

public interface LoanService {

    public Optional<Loan> get(Long loanId);

    List<Loan> getAllLoansForCopy(Long copyId);

    public List<Loan> getAll();

    public void save(Loan loan);

    public void update(Loan loan);

    public void delete(Loan loan);

    public void setDAO(LoanDAO DAO);

    public void endLoan(Loan loan);
}
