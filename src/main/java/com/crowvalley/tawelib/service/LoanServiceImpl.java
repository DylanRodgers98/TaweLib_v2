package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.dao.LoanDAO;
import com.crowvalley.tawelib.model.fine.Fine;
import com.crowvalley.tawelib.model.resource.Copy;
import com.crowvalley.tawelib.model.resource.Loan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.sql.Date;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

public class LoanServiceImpl implements LoanService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoanServiceImpl.class);

    @Resource
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

    @Override
    public void endLoan(Loan loan) {
        loan.setReturnDate(new Date(System.currentTimeMillis()));
        update(loan);

        Date endDate = loan.getEndDate();
        Date returnDate = loan.getReturnDate();

        if (returnDate.after(endDate)) {
            Optional<Copy> copy = copyService.get(loan.getCopyId());
            String copyType = copy.get().getResourceType();

            Double fineAmount = 0.0;
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
            fineService.save(new Fine(loan.getBorrowerUsername(), loan.getId(), fineAmount));
        }
    }

}