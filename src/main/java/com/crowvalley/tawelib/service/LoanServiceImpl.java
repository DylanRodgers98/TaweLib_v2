package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.dao.LoanDAO;
import com.crowvalley.tawelib.model.fine.Fine;
import com.crowvalley.tawelib.model.resource.Copy;
import com.crowvalley.tawelib.model.resource.Loan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

public class LoanServiceImpl implements LoanService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoanServiceImpl.class);

    @Autowired
    private LoanDAO DAO;

    @Autowired
    private FineService fineService;

    @Autowired
    private CopyService copyService;

    @Override
    public Optional<Loan> get(Long loanId) {
        Optional<Loan> loan = DAO.get(loanId);
        if (loan.isPresent()) {
            LOGGER.info("Loan (ID: {}) for copy (ID: {}) retrieved successfully", loanId, loan.get().getCopyId());
            return loan;
        } else {
            LOGGER.warn("Could not find loan with ID {}", loanId);
            return Optional.empty();
        }
    }

    @Override
    public List<Loan> getAllLoansForCopy(Long copyId) {
        List<Loan> loans = DAO.getAllLoansForCopy(copyId);
        if (!loans.isEmpty()) {
            LOGGER.info("All loans for copy (ID: {}) retrieved successfully", copyId);
            return loans;
        } else {
            LOGGER.warn("No loans found for copy with ID {}", copyId);
            return loans;
        }
    }

    @Override
    public List<Loan> getAllLoansForUser(String username) {
        List<Loan> loans = DAO.getAllLoansForUser(username);
        if (!loans.isEmpty()) {
            LOGGER.info("All loans for user {} retrieved successfully", username);
            return loans;
        } else {
            LOGGER.warn("No loans found for user {}", username);
            return loans;
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
    public void save(Loan loan) {
        DAO.save(loan);
        LOGGER.info("Loan (ID: {}) for copy (ID: {}) saved successfully", loan.getId(), loan.getCopyId());
    }

    @Override
    public void update(Loan loan) {
        DAO.update(loan);
        LOGGER.info("Loan (ID: {}) for copy (ID: {}) updated successfully", loan.getId(), loan.getCopyId());
    }

    @Override
    public void delete(Loan loan) {
        DAO.delete(loan);
        LOGGER.info("Loan (ID: {}) for copy (ID: {}) deleted successfully", loan.getId(), loan.getCopyId());
    }

    @Override
    public void setDAO(LoanDAO DAO) {
        this.DAO = DAO;
        LOGGER.info("LoanServiceImpl DAO set to {}", DAO.getClass());
    }

    @Override
    public void endLoan(Loan loan) {
        loan.setReturnDate(new Date(System.currentTimeMillis()));
        update(loan);
        LOGGER.info("Copy (ID: {}) returned by {} on {}",
                loan.getCopyId(), loan.getBorrowerUsername(), loan.getReturnDate());

        Date endDate = loan.getEndDate();
        Date returnDate = loan.getReturnDate();

        if (returnDate.after(endDate)) {
            Optional<Copy> copy = copyService.get(loan.getCopyId());
            String copyType = copy.get().getResourceType();

            Double fineAmount = 0.00;
            switch (copyType) {
                case Copy.BOOK_TYPE:
                    fineAmount = Fine.BOOK_FINE_AMOUNT_PER_DAY;
                    break;
                case Copy.DVD_TYPE:
                    fineAmount = Fine.DVD_FINE_AMOUNT_PER_DAY;
                    break;
                case Copy.LAPTOP_TYPE:
                    fineAmount = Fine.LAPTOP_FINE_AMOUNT_PER_DAY;
                    break;
            }
            Long dayDiffBetweenEndAndReturnDates = ChronoUnit.DAYS.between(endDate.toLocalDate(), returnDate.toLocalDate());
            fineAmount *= dayDiffBetweenEndAndReturnDates;
            Fine fine = new Fine(loan.getBorrowerUsername(), loan.getId(), fineAmount);
            fineService.save(fine);
            LOGGER.info("£{} fine (ID: {}) issued to {}",
                    String.format("%.2f", fine.getAmount()), fine.getId(), fine.getUsername());
        }
    }

}
