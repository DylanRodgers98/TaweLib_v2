package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.model.fine.Fine;
import com.crowvalley.tawelib.model.resource.Loan;
import com.crowvalley.tawelib.model.resource.Book;
import com.crowvalley.tawelib.model.resource.Copy;
import com.crowvalley.tawelib.model.resource.ResourceFactory;
import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/applicationContext.xml "})
public class LoanServiceImplIT {

    @Autowired
    private LoanService loanService;

    @Autowired
    private BookServiceImpl bookService;

    @Autowired
    private CopyService copyService;

    @Autowired
    private FineService fineService;

    @Autowired
    private ResourceFactory resourceFactory;

    @Test
    @Transactional
    public void testCRUDOperationsOnLoan() {
        JUnitSoftAssertions softly = new JUnitSoftAssertions();
        Loan loan = new Loan(1L, "DylanRodgers98",
                new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis() + 1000000));

        //Test Create and Retrieve operations
        loanService.save(loan);
        List<Loan> loans = loanService.getAll();
        Long id = loans.get(0).getId();

        softly.assertThat(loanService.get(id).get())
                .as("Retrieve loan from database")
                .isEqualTo(loan);

        //Test Update operation
        Loan loanToUpdate = loanService.get(id).get();
        loanToUpdate.setReturnDate(new Date(System.currentTimeMillis() + 9000000));
        loanService.update(loanToUpdate);

        softly.assertThat(loanService.get(id).get())
                .as("Retrieve loan from database with updated return date")
                .isEqualTo(loan);

        //Test Delete operation
        loanService.delete(loan);

        softly.assertThat(loanService.get(id))
                .as("Loan deleted")
                .isEqualTo(Optional.empty());
    }

    @Test
    @Transactional
    public void testCreateLoanForACopy() {
        Book book = resourceFactory.createBook("Book", "", "", "", "", "", "", "");
        bookService.save(book);

        Copy copy = resourceFactory.createCopy(book, 4);
        copyService.save(copy);

        String borrower = "DylanRodgers98";
        Loan loan = resourceFactory.createLoanForCopy(copy, borrower);
        loanService.save(loan);

        Loan loanRetrievedFromDatabase = loanService.getAll().get(0);

        JUnitSoftAssertions softly = new JUnitSoftAssertions();
        softly.assertThat(loanRetrievedFromDatabase)
                .as("Loan retrieved from database is equal to the one saved to the database")
                .isEqualTo(loan);

        softly.assertThat(loanRetrievedFromDatabase.getCopyId())
                .as("The ID of the copy is the same as the one stored in the loan")
                .isEqualTo(copy.getId());
    }

    //TODO: FINISH THIS TEST CASE
    @Test
    @Transactional
    public void testCopyReturnedLateAndFineCreated() {
        Book book = resourceFactory.createBook("Book", "", "", "", "", "", "", "");
        bookService.save(book);

        Copy copy = resourceFactory.createCopy(book, 4);
        copyService.save(copy);

        String borrower = "DylanRodgers98";
        Loan loan = new Loan(copy.getId(), borrower, new Date(119, 8, 9), new Date(119, 8,13)); //Year 119 due to how Date calculates year as year+1970, and month 8 for Sept due to how Date starts the months at 0
        loanService.save(loan);

        JUnitSoftAssertions softly = new JUnitSoftAssertions();
        softly.assertThat(loan.getEndDate())
                .as("Loan retrieved from database with end date before current time")
                .isBefore(new Date(System.currentTimeMillis()));

        softly.assertThat(loan.getReturnDate())
                .as("Loan retrieved from database with copy not yet returned")
                .isNull();

        loanService.endLoan(loan);
        List<Fine> fines = fineService.getAll();

        softly.assertThat(fines.get(0).getLoanId())
                .as("New fine created for late return of copy")
                .isEqualTo(loan.getId());

        softly.assertThat(fines.get(0).getUsername())
                .as("New fine for user")
                .isEqualTo(loan.getBorrowerUsername());
    }

    @Test
    @Transactional
    public void testReturnCopyAndEndLoan() {
        Book book = resourceFactory.createBook("Book", "", "", "", "", "", "", "");
        bookService.save(book);

        Copy copy = resourceFactory.createCopy(book, 4);
        copyService.save(copy);

        String borrower = "DylanRodgers98";
        Loan loan = resourceFactory.createLoanForCopy(copy, borrower);
        loanService.save(loan);

        JUnitSoftAssertions softly = new JUnitSoftAssertions();
        softly.assertThat(loan.getReturnDate())
                .as("Loan retrieved from database with copy not yet returned")
                .isNull();

        loanService.endLoan(loan);
        softly.assertThat(loan.getReturnDate())
                .as("Loan retrieved from database with copy now returned, with return date equal to today's date")
                .isEqualTo(new Date(System.currentTimeMillis()));
    }

    @Test
    @Transactional
    public void testGetAllLoansForACopy() {
        Book book = resourceFactory.createBook("Book", "", "", "", "", "", "", "");
        bookService.save(book);

        Copy copy1 = resourceFactory.createCopy(book, 4);
        Copy copy2 = resourceFactory.createCopy(book, 7);
        copyService.save(copy1);
        copyService.save(copy2);

        Loan loan1 = resourceFactory.createLoanForCopy(copy1, "One");
        Loan loan2 = resourceFactory.createLoanForCopy(copy1, "Two");
        Loan loan3 = resourceFactory.createLoanForCopy(copy1, "Three");
        Loan loan4 = resourceFactory.createLoanForCopy(copy2, "Four");
        loanService.save(loan1);
        loanService.save(loan2);
        loanService.save(loan3);
        loanService.save(loan4);

        List<Loan> loansFromDatabase = loanService.getAllLoansForCopy(copy1.getId());

        JUnitSoftAssertions softly = new JUnitSoftAssertions();
        softly.assertThat(loansFromDatabase)
                .as("Loans retrieved from database for the copy ID passed in")
                .containsExactly(loan1, loan2, loan3)
                .doesNotContain(loan4);
    }
}
