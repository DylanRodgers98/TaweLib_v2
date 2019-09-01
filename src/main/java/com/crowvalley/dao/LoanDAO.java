package com.crowvalley.dao;

import com.crowvalley.model.Loan;

import java.util.List;
import java.util.Optional;

public interface LoanDAO {

    Optional<Loan> get(Long id);

    List<Loan> getAll();

    void save(Loan loan);

    void update(Loan loan);

    void delete(Loan loan);

}
