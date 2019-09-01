package com.crowvalley.service;

import com.crowvalley.dao.LoanDAO;
import com.crowvalley.model.Loan;

import java.util.List;
import java.util.Optional;

public interface LoanService {

    public Optional<Loan> get(Long id);

    public List<Loan> getAll();

    public void save(Loan loan);

    public void update(Loan loan);

    public void delete(Loan loan);

    public void setDAO(LoanDAO DAO);
}
