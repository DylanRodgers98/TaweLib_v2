package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.dao.LoanDAO;
import com.crowvalley.tawelib.model.fine.Fine;
import com.crowvalley.tawelib.model.resource.Copy;
import com.crowvalley.tawelib.model.resource.Loan;
import com.crowvalley.tawelib.model.resource.ResourceType;
import com.crowvalley.tawelib.model.user.User;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

/**
 * Service class for retrieving data about {@link Loan} objects
 * persisted in a database, using a Data Access Object (DAO) to perform
 * CRUD operations.
 *
 * @author Dylan Rodgers
 */
public class LoanServiceImpl implements LoanService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoanServiceImpl.class);

    private LoanDAO DAO;

    private TransactionService transactionService;

    private CopyService copyService;

    /**
     * Retrieves a {@link Loan} from the DAO using the {@link Loan}'s
     * {@code id} and returns it wrapped in an {@link Optional}. If a
     * {@link Loan} with the passed {@code id} isn't retrieved from the
     * DAO, an empty {@link Optional} is returned.
     *
     * @param loanId The ID of the {@link Loan} to be retrieved.
     * @return The requested {@link Loan} wrapped in an {@link Optional}
     * if it isn't retrieved by the DAO, or an empty {@link Optional} if not.
     */
    @Override
    public Optional<Loan> get(Long loanId) {
        return DAO.getWithId(loanId, Loan.class);
    }

    /**
     * Retrieves a {@link List} of all {@link Loan}s created, past and present,
     * for a given {@link Copy} stored in the database.
     *
     * @param copyId The ID of the {@link Copy} for which to generate the
     *               list of {@link Loan}s for.
     * @return A {@link List} of all {@link Loan}s stored in the database
     * for a given {@link Copy}.
     */
    @Override
    public List<Loan> getAllLoansForCopy(Long copyId) {
        return DAO.getAllLoansForCopy(copyId);
    }

    @Override
    public Optional<Loan> getCurrentLoanForCopy(Long copyId) {
        return DAO.getCurrentLoanForCopy(copyId);
    }

    @Override
    public boolean isCopyOnLoan(Long copyId) {
        return getCurrentLoanForCopy(copyId).isPresent();
    }

    @Override
    public String getUsernameOfCurrentBorrowerForCopy(Long copyId) {
        Optional<Loan> loanForCopy = getCurrentLoanForCopy(copyId);
        if (loanForCopy.isPresent()) {
            return loanForCopy.get().getBorrowerUsername();
        }
        return StringUtils.EMPTY;
    }

    /**
     * Retrieves a {@link List} of all {@link Loan}s created, past and present,
     * for a given {@link User} stored in the database.
     *
     * @param username The ID of the {@link User} for which to generate the
     *               list of {@link Loan}s for.
     * @return A {@link List} of all {@link Loan}s stored in the database
     * for a given {@link User}.
     */
    @Override
    public List<Loan> getAllLoansForUser(String username) {
        return DAO.getAllLoansForUser(username);
    }

    /**
     * @return A {@link List} of all {@link Loan}s retrieved by the DAO.
     */
    @Override
    public List<Loan> getAll() {
        return DAO.getAll(Loan.class);
    }

    /**
     * Persists a {@link Loan} object to the database through the DAO.
     *
     * @param loan The {@link Loan} object to be saved to the database.
     */
    @Override
    public void saveOrUpdate(Loan loan) {
        DAO.saveOrUpdate(loan);
        LOGGER.info("Loan (ID: {}) for copy (ID: {}) saved successfully", loan.getId(), loan.getCopyId());
    }

    /**
     * Deletes a {@link Loan} object from the database through the DAO.
     *
     * @param loan The {@link Loan} object to be deleted from the database.
     */
    @Override
    public void delete(Loan loan) {
        DAO.delete(loan);
        LOGGER.info("Loan (ID: {}) for copy (ID: {}) deleted successfully", loan.getId(), loan.getCopyId());
    }

    /**
     * Carries out the process of ending a loan for the passed in {@link Loan}
     * object. "Ending a loan" means setting the {@code returnDate} to
     * {@code System.getCurrentTimeMillis()} and determining whether any
     * {@link Fine}s need to be created for the {@link User} returning the
     * {@link Copy}.
     *
     * @param loan The {@link Loan} object for which to end the loan
     */
    @Override
    public void endLoan(Loan loan) {
        loan.setReturnDate(new Date(System.currentTimeMillis()));
        saveOrUpdate(loan);
        LOGGER.info("Copy (ID: {}) returned by {} on {}",
                loan.getCopyId(), loan.getBorrowerUsername(), loan.getReturnDate());

        Date endDate = loan.getEndDate();
        Date returnDate = loan.getReturnDate();

        if (returnDate.after(endDate)) {
            Optional<Copy> copy = copyService.get(loan.getCopyId());
            if (copy.isEmpty()) {
                LOGGER.error("Could not retrieve copy (ID: {}) from database", loan.getCopyId());
                throw new IllegalStateException("Could not retrieve copy from database");
            } else {
                ResourceType copyType = copy.get().getResourceType();

                Double fineAmount = 0.00;
                if (copyType.equals(ResourceType.BOOK)) {
                    fineAmount = Fine.BOOK_FINE_AMOUNT_PER_DAY;
                } else if (copyType.equals(ResourceType.DVD)) {
                    fineAmount = Fine.DVD_FINE_AMOUNT_PER_DAY;
                } else if (copyType.equals(ResourceType.LAPTOP)) {
                    fineAmount = Fine.LAPTOP_FINE_AMOUNT_PER_DAY;
                }
                Long dayDiffBetweenEndAndReturnDates = ChronoUnit.DAYS.between(endDate.toLocalDate(), returnDate.toLocalDate());
                fineAmount *= dayDiffBetweenEndAndReturnDates;

                Fine fine = new Fine(loan.getBorrowerUsername(), loan.getId(), fineAmount);
                transactionService.save(fine);
                LOGGER.info("£{} fine (ID: {}) issued to {}",
                        String.format("%.2f", fine.getAmount()), fine.getId(), fine.getUsername());
            }
        }
    }

    @Override
    public void setDAO(LoanDAO DAO) {
        this.DAO = DAO;
        LOGGER.info("{} DAO set to {}", this.getClass().getSimpleName(), DAO.getClass().getSimpleName());
    }

    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
        LOGGER.info("{} FineService set to {}", this.getClass().getSimpleName(), transactionService.getClass().getSimpleName());
    }

    public void setCopyService(CopyService copyService) {
        this.copyService = copyService;
        LOGGER.info("{} CopyService set to {}", this.getClass().getSimpleName(), copyService.getClass().getSimpleName());
    }
}
