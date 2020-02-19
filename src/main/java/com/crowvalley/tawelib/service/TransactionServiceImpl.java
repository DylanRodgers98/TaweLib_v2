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
     * Retrieves a {@link Transaction} from the DAO using the {@link Transaction}'s
     * {@code id} and returns it wrapped in an {@link Optional}. If a
     * {@link Transaction} with the passed {@code id} isn't retrieved from the
     * DAO, an empty {@link Optional} is returned.
     *
     * @param id The ID of the {@link Transaction} to be retrieved
     * @return The requested {@link Transaction} wrapped in an {@link Optional}
     * if it isn't retrieved by the DAO, or an empty {@link Optional} if not.
     */
    @Override
    public Optional<Fine> get(Long id) {
        return DAO.getWithId(id, Fine.class);
    }

    @Override
    public Double getTotalFineAmountForUser(String username) {
        return DAO.getTotalFineAmountForUser(username);
    }

    /**
     * @return A {@link List} of all {@link Transaction}s retrieved by the DAO.
     */
    @Override
    public List<Transaction> getAll() {
        return DAO.getAll(Transaction.class);
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
    public Double getTotalPaymentAmountForUser(String username) {
        return DAO.getTotalPaymentAmountForUser(username);
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
