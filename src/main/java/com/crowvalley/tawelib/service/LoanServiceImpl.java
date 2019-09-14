package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.dao.LoanDAO;
import com.crowvalley.tawelib.model.Loan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

public class LoanServiceImpl implements LoanService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoanServiceImpl.class);

    @Resource
    private LoanDAO DAO;

    @Override
    public Optional<Loan> get(Long id) {
        Optional<Loan> loan = DAO.get(id);
        if (loan.isPresent()) {
            LOGGER.info("Loan (ID: {}) for copy (ID: {}) retrieved successfully", id, loan.get().getCopyId());
            return loan;
        } else {
            LOGGER.warn("Could not find loan with ID {}", id);
            return Optional.empty();
        }
    }

    @Override
    public List<Loan> getAll() {
        List<Loan> loans = DAO.getAll();
        if (!loans.isEmpty()) {
            LOGGER.info("All loans retrieved successfully");
            return loans;
        } else {
            LOGGER.warn("No loans found");
            return loans;
        }
    }

    @Override
    public void save(Loan loan){
        DAO.save(loan);
        LOGGER.info("Loan with ID {} saved successfully", loan.getId());
    }

    @Override
    public void update(Loan loan) {
        DAO.update(loan);
        LOGGER.info("Loan with ID {} updated successfully", loan.getId());
    }

    @Override
    public void delete(Loan loan) {
        DAO.delete(loan);
        LOGGER.info("Loan with ID {} deleted successfully", loan.getId());
    }

    @Override
    public void setDAO(LoanDAO DAO) {
        this.DAO = DAO;
        LOGGER.info("LoanServiceImpl DAO set to {}", DAO.getClass());
    }

}
