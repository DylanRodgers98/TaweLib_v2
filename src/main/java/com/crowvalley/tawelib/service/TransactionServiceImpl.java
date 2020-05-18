package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.dao.TransactionDAO;
import com.crowvalley.tawelib.model.fine.Fine;
import com.crowvalley.tawelib.model.fine.OutstandingFinesDTO;
import com.crowvalley.tawelib.model.fine.Transaction;
import com.crowvalley.tawelib.model.resource.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    private TransactionDAO transactionDAO;

    private LoanService loanService;

    private CopyService copyService;

    private UserService userService;

    /**
     * @return A {@link List} of all {@link Transaction}s retrieved by the DAO.
     */
    @Override
    public List<Transaction> getAll() {
        return transactionDAO.getAll(Transaction.class);
    }

    @Override
    public List<Transaction> getAllTransactionsForUser(String username) {
        return transactionDAO.getAllTransactionsForUser(username);
    }

    /**
     * Persists a {@link Transaction} object to the database through the DAO.
     *
     * @param transaction The {@link Transaction} object to be saved to the database.
     */
    @Override
    public void save(Transaction transaction) {
        transactionDAO.saveOrUpdate(transaction);
        LOGGER.info("Fine with ID {} saved successfully", transaction.getId());
    }

    @Override
    public List<OutstandingFinesDTO> getAllUsersWithOutstandingFines() {
        List<OutstandingFinesDTO> usersWithOutstandingFines = new ArrayList<>();
        for (String username : userService.getAllUsernames()) {
            BigDecimal finesForUser = getTotalFinesAmountForUser(username);
            BigDecimal paymentsForUser = getTotalPaymentsAmountForUser(username);
            BigDecimal outstandingFines = finesForUser.subtract(paymentsForUser);
            if (outstandingFines.compareTo(BigDecimal.ZERO) > 0) {
                usersWithOutstandingFines.add(new OutstandingFinesDTO(username, outstandingFines));
            }
        }
        return usersWithOutstandingFines;
    }

    @Override
    public BigDecimal getTotalFinesAmountForUser(String username) {
        BigDecimal totalFinesAmount = transactionDAO.getTotalFinesAmountForUser(username);
        return totalFinesAmount != null ? totalFinesAmount : BigDecimal.ZERO;
    }

    @Override
    public BigDecimal getTotalPaymentsAmountForUser(String username) {
        BigDecimal totalPaymentsAmount = transactionDAO.getTotalPaymentsAmountForUser(username);
        return totalPaymentsAmount != null ? totalPaymentsAmount : BigDecimal.ZERO;
    }

    @Override
    public List<Transaction> search(LocalDateTime startDate, LocalDateTime endDate) {
        return search(null, startDate, endDate);
    }

    @Override
    public List<Transaction> search(String username, LocalDateTime startDate, LocalDateTime endDate) {
        return transactionDAO.search(username, startDate, endDate);
    }

    @Override
    public void setTransactionDAO(TransactionDAO transactionDAO) {
        this.transactionDAO = transactionDAO;
        LOGGER.info("{} TransactionDAO set to {}", this.getClass().getSimpleName(), transactionDAO.getClass().getSimpleName());
    }

    public void setLoanService(LoanService loanService) {
        this.loanService = loanService;
        LOGGER.info("{} LoanService set to {}", this.getClass().getSimpleName(), loanService.getClass().getSimpleName());
    }

    public void setCopyService(CopyService copyService) {
        this.copyService = copyService;
        LOGGER.info("{} CopyService set to {}", this.getClass().getSimpleName(), copyService.getClass().getSimpleName());
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
        LOGGER.info("{} UserService set to {}", this.getClass().getSimpleName(), userService.getClass().getSimpleName());
    }

}
