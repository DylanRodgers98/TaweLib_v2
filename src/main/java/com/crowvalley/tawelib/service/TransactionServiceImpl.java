package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.dao.TransactionDAO;
import com.crowvalley.tawelib.model.fine.Fine;
import com.crowvalley.tawelib.model.fine.Transaction;
import com.crowvalley.tawelib.model.resource.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

/**
 * Service class for retrieving data about {@link Transaction} objects
 * persisted in a database, using a Data Access Object (DAO) to perform
 * create, retrieve, and delete operations.
 *
 * @author Dylan Rodgers
 */
public class TransactionServiceImpl implements TransactionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionServiceImpl.class);

    private TransactionDAO DAO;

    private LoanService loanService;

    private CopyService copyService;

    /**
     * @return A {@link List} of all {@link Transaction}s retrieved by the DAO.
     */
    @Override
    public List<Transaction> getAll() {
        return DAO.getAll(Transaction.class);
    }

    /**
     * Persists a {@link Transaction} object to the database through the DAO.
     *
     * @param transaction The {@link Transaction} object to be saved to the database.
     */
    @Override
    public void save(Transaction transaction) {
        DAO.saveOrUpdate(transaction);
        LOGGER.info("Fine with ID {} saved successfully", transaction.getId());
    }

    /**
     * Deletes a {@link Transaction} object from the database through the DAO.
     *
     * @param transaction The {@link Transaction} object to be deleted from the database.
     */
    @Override
    public void delete(Transaction transaction) {
        DAO.delete(transaction);
        LOGGER.info("Fine with ID {} deleted successfully", transaction.getId());
    }

    @Override
    public Optional<Copy> getCopyFromFine(Fine fine) {
        Optional<Loan> optionalLoan = loanService.get(fine.getLoanId());
        if (optionalLoan.isPresent()) {
            Loan loan = optionalLoan.get();
            return copyService.get(loan.getCopyId());
        }
        return Optional.empty();
    }

    @Override
    public Double getTotalFinesAmountForUser(String username) {
        Double totalFinesAmount = DAO.getTotalFineAmountForUser(username);
        return totalFinesAmount != null ? totalFinesAmount : 0d;
    }

    @Override
    public Double getTotalPaymentsAmountForUser(String username) {
        Double totalPaymentsAmount = DAO.getTotalPaymentAmountForUser(username);
        return totalPaymentsAmount != null ? totalPaymentsAmount : 0d;
    }

    @Override
    public void setDAO(TransactionDAO DAO) {
        this.DAO = DAO;
        LOGGER.info("{} DAO set to {}", this.getClass().getSimpleName(), DAO.getClass().getSimpleName());
    }

    public void setLoanService(LoanService loanService) {
        this.loanService = loanService;
        LOGGER.info("{} LoanService set to {}", this.getClass().getSimpleName(), loanService.getClass().getSimpleName());
    }

    public void setCopyService(CopyService copyService) {
        this.copyService = copyService;
        LOGGER.info("{} CopyService set to {}", this.getClass().getSimpleName(), copyService.getClass().getSimpleName());
    }

}
