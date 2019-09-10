package com.crowvalley.tawelib.dao;

import com.crowvalley.tawelib.model.Loan;

import java.util.List;
import java.util.Optional;

public interface LoanDAO {

    Optional<Loan> get(Long id);

    List<Loan> getAll();

    void save(Loan loan);

    void update(Loan loan);

    void delete(Loan loan);

}
