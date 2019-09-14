package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.model.Loan;
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
    private ResourceFactory resourceFactory;

    @Test
    @Transactional
    public void testCRUDOperationsOnBook() {
        JUnitSoftAssertions softly = new JUnitSoftAssertions();
        Loan loan = new Loan(Long.valueOf(1), "DylanRodgers98",
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
        JUnitSoftAssertions softly = new JUnitSoftAssertions();

        Book book = resourceFactory.createBook("Book", "", "", "", "", "", "", "");
        bookService.save(book);
        Book bookRetrievedFromDatabase = bookService.getAll().get(0);

        Copy copy = resourceFactory.createCopy(bookRetrievedFromDatabase, 4);
        copyService.save(copy);
        Copy copyRetrievedFromDatabase = copyService.getAll().get(0);

        String borrower = "DylanRodgers98";
        Loan loan = resourceFactory.createLoanForCopy(copyRetrievedFromDatabase, borrower);
        loanService.save(loan);

        Loan loanRetrievedFromDatabase = loanService.getAll().get(0);

        softly.assertThat(loanRetrievedFromDatabase)
                .as("Loan retrieved from database is equal to the one saved to the database")
                .isEqualTo(loan);

        softly.assertThat(loanRetrievedFromDatabase.getCopyId())
                .as("The ID of the copy is the same as the one stored in the loan")
                .isEqualTo(copyRetrievedFromDatabase.getId());
    }
}
