package com.crowvalley.tawelib.dao;

import com.crowvalley.tawelib.model.resource.Book;
import com.crowvalley.tawelib.model.resource.Copy;
import com.crowvalley.tawelib.model.resource.Loan;
import com.crowvalley.tawelib.model.resource.Resource;
import com.crowvalley.tawelib.service.LoanService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/spring/applicationContext.xml")
@Transactional
public class LoanDAOIT {

    private static final String USERNAME = "TEST_USER";

    @Autowired
    private LoanDAO loanDAO;

    @Autowired
    private LoanService loanService;

    @Test
    public void testGetCurrentLoanForCopy() {
        Resource book = new Book();
        Copy copy = new Copy(book, 1, "");
        book.getCopies().add(copy);
        loanDAO.saveOrUpdate(book);

        loanService.createLoanForCopy(copy, USERNAME);

        Optional<Loan> optionalLoan = loanDAO.getCurrentLoanForCopy(copy.getId());
        assertThat(optionalLoan)
                .as("Loan retrieved from database using Copy ID")
                .isPresent();

        Loan loan = optionalLoan.get();
        assertThat(loan.getCopyId())
                .as("Loan retrieved from database has correct Copy ID set on it")
                .isEqualTo(copy.getId());

        loanService.endLoan(loan);

        optionalLoan = loanDAO.getCurrentLoanForCopy(copy.getId());
        assertThat(optionalLoan)
                .as("No Loan retrieved from database as Copy returned")
                .isEmpty();
    }

    @Test
    public void testGetAllLoansForUser() {
        Loan loan1 = new Loan(null, USERNAME, null, null);
        Loan loan2 = new Loan(null, USERNAME, null, null);
        Loan loan3 = new Loan(null, USERNAME, null, null);
        loanDAO.saveOrUpdate(loan1);
        loanDAO.saveOrUpdate(loan2);
        loanDAO.saveOrUpdate(loan3);

        List<Loan> loans = loanDAO.getAllLoansForUser(USERNAME);

        assertThat(loans)
                .as("List of all Loan entities retrieved from database for given username")
                .contains(loan1, loan2, loan3);
    }

    @Test
    public void testSearch() {
        Loan loanYesterdayToToday = new Loan(null, USERNAME, LocalDateTime.now().minusDays(1), LocalDateTime.now());
        Loan loanLastFortnightToLastWeek= new Loan(null, USERNAME, LocalDateTime.now().minusWeeks(2), LocalDateTime.now().minusWeeks(1));
        loanDAO.saveOrUpdate(loanYesterdayToToday);
        loanDAO.saveOrUpdate(loanLastFortnightToLastWeek);

        List<Loan> loansSearchedWithUsername = loanDAO.search(USERNAME, null, null);
        assertThat(loansSearchedWithUsername)
                .as("List of Loan entities retrieved from database by searching by username")
                .contains(loanYesterdayToToday, loanLastFortnightToLastWeek);

        List<Loan> loansSearchedWithDate = loanDAO.search(null, LocalDateTime.now().minusDays(1), LocalDateTime.now());
        assertThat(loansSearchedWithDate)
                .as("List of Loan entities retrieved from database by searching by date from yesterday")
                .contains(loanYesterdayToToday);

        loansSearchedWithDate = loanDAO.search(null, LocalDateTime.now().minusWeeks(2), LocalDateTime.now());
        assertThat(loansSearchedWithDate)
                .as("List of Loan entities retrieved from database by searching by date from last fortnight")
                .contains(loanYesterdayToToday, loanLastFortnightToLastWeek);

        List<Loan> loansSearchedDifferentUsername = loanDAO.search("Not Test User", null, null);
        assertThat(loansSearchedDifferentUsername)
                .as("List of Loan entities retrieved from database by searching with a different username is empty")
                .isEmpty();
    }

}
