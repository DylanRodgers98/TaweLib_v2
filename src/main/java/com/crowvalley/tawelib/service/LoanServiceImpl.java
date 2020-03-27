package com.crowvalley.tawelib.service;

import static java.time.temporal.ChronoUnit.DAYS;

import com.crowvalley.tawelib.dao.LoanDAO;
import com.crowvalley.tawelib.model.fine.Fine;
import com.crowvalley.tawelib.model.resource.Copy;
import com.crowvalley.tawelib.model.resource.CopyRequest;
import com.crowvalley.tawelib.model.resource.Loan;
import com.crowvalley.tawelib.model.resource.ResourceType;
import com.crowvalley.tawelib.model.user.User;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Service class for retrieving data about {@link Loan} objects
 * persisted in a database, using a Data Access Object (DAO) to perform
 * CRUD operations.
 *
 * @author Dylan Rodgers
 */
public class LoanServiceImpl implements LoanService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoanServiceImpl.class);

    private static final String COPY_HAS_NO_ID_ERROR_MESSAGE = "Cannot create loan. The copy has no ID, so a " +
            "loan instance cannot reference it. Likely cause is that the copy hasn't been persisted to the database yet";

    private LoanDAO loanDAO;

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
        return loanDAO.getWithId(loanId, Loan.class);
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
        return loanDAO.getAllLoansForCopy(copyId);
    }

    @Override
    public Optional<Loan> getCurrentLoanForCopy(Long copyId) {
        return loanDAO.getCurrentLoanForCopy(copyId);
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
        return loanDAO.getAllLoansForUser(username);
    }

    /**
     * @return A {@link List} of all {@link Loan}s retrieved by the DAO.
     */
    @Override
    public List<Loan> getAll() {
        return loanDAO.getAll(Loan.class);
    }

    /**
     * Creates a {@link Loan} for a given {@link Copy}.
     *
     * @param copy             The {@link Copy} for which to create a {@link Loan} for.
     * @param borrowerUsername The {@code username} of the {@link User} who
     *                         is borrowing the passed in {@link Copy}.
     */
    @Override
    public void createLoanForCopy(Copy copy, String borrowerUsername) {
        Long id = copy.getId();
        Assert.notNull(id, COPY_HAS_NO_ID_ERROR_MESSAGE);

        LocalDateTime startDateTime = LocalDateTime.now();
        LocalDate endDate = startDateTime.plusDays(copy.getLoanDurationAsDays()).toLocalDate();
        LocalDateTime endDateTime = LocalDateTime.of(endDate, LocalTime.of(23, 59, 59));

        saveOrUpdate(new Loan(id, borrowerUsername, startDateTime, endDateTime));

        setCollectedStatusThenRemoveCopyRequest(copy, borrowerUsername);
    }

    private void setCollectedStatusThenRemoveCopyRequest(Copy copy, String borrowerUsername) {
        Optional<CopyRequest.Status> status = copyService.getRequestStatusForUser(copy, borrowerUsername);
        if (status.isPresent()) {
            Set<CopyRequest.Status> nonCollectedStatus = EnumSet.complementOf(EnumSet.of(CopyRequest.Status.COLLECTED));
            if (nonCollectedStatus.contains(status.get())) {
                copyService.setRequestStatusForUser(copy, borrowerUsername, CopyRequest.Status.COLLECTED);
            }
            copyService.removeCopyRequest(copy, borrowerUsername);
        }
    }

    /**
     * Persists a {@link Loan} object to the database through the DAO.
     *
     * @param loan The {@link Loan} object to be saved to the database.
     */
    @Override
    public void saveOrUpdate(Loan loan) {
        loanDAO.saveOrUpdate(loan);
        LOGGER.info("Loan (ID: {}) for copy (ID: {}) saved successfully", loan.getId(), loan.getCopyId());
    }

    /**
     * Deletes a {@link Loan} object from the database through the DAO.
     *
     * @param loan The {@link Loan} object to be deleted from the database.
     */
    @Override
    public void delete(Loan loan) {
        loanDAO.delete(loan);
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
        loan.setReturnDate(LocalDateTime.now());
        saveOrUpdate(loan);
        LOGGER.info("Copy (ID: {}) returned by {} on {}",
                loan.getCopyId(), loan.getBorrowerUsername(), loan.getReturnDate());

        LocalDateTime endDate = loan.getEndDate();
        LocalDateTime returnDate = loan.getReturnDate();

        if (returnDate.isAfter(endDate)) {
            Optional<Copy> copy = copyService.get(loan.getCopyId());
            if (copy.isEmpty()) {
                LOGGER.error("Could not retrieve copy (ID: {}) from database", loan.getCopyId());
                throw new IllegalStateException("Could not retrieve copy (ID: "+ loan.getCopyId() + ") from database");
            } else {
                ResourceType copyType = copy.get().getResource().getResourceType();

                BigDecimal fineAmount = null;
                if (copyType.equals(ResourceType.BOOK)) {
                    fineAmount = Fine.BOOK_FINE_AMOUNT_PER_DAY;
                } else if (copyType.equals(ResourceType.DVD)) {
                    fineAmount = Fine.DVD_FINE_AMOUNT_PER_DAY;
                } else if (copyType.equals(ResourceType.LAPTOP)) {
                    fineAmount = Fine.LAPTOP_FINE_AMOUNT_PER_DAY;
                }
                Assert.notNull(fineAmount, "Cannot resolve fine amount against late-returned loan (ID: " + loan.getId() + ")");

                long dayDiffBetweenEndAndReturnDates = DAYS.between(endDate, returnDate);
                fineAmount = fineAmount.multiply(BigDecimal.valueOf(dayDiffBetweenEndAndReturnDates));

                Fine fine = new Fine(loan.getBorrowerUsername(), loan.getId(), fineAmount);
                transactionService.save(fine);
                LOGGER.info("£{} fine (ID: {}) issued to {}",
                        String.format("%.2f", fine.getAmount()), fine.getId(), fine.getUsername());
            }
        }
    }

    @Override
    public void setLoanDAO(LoanDAO loanDAO) {
        this.loanDAO = loanDAO;
        LOGGER.info("{} LoanDAO set to {}", this.getClass().getSimpleName(), loanDAO.getClass().getSimpleName());
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
