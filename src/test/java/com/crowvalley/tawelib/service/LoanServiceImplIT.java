package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.model.fine.Fine;
import com.crowvalley.tawelib.model.resource.*;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/applicationContext.xml "})
public class LoanServiceImplIT {

    private static final String USERNAME = "DylanRodgers98";

    private static final int DAYS_LATE = 2;

    private Book book = ResourceFactory.createBook("Book", "2015", StringUtils.EMPTY, "Dylan Rodgers", "Penguin", "Children", "Foo", "English");

    private Dvd dvd = ResourceFactory.createDvd("Dvd", "2018", StringUtils.EMPTY, "Dylan Rodgers", "English", 120, "Welsh");

    private Laptop laptop = ResourceFactory.createLaptop("Laptop", "2014", StringUtils.EMPTY, "Acer", "Aspire", "Windows 10");

    @Autowired
    private LoanService loanService;

    @Autowired
    private BookServiceImpl bookService;

    @Autowired
    private DvdServiceImpl dvdService;

    @Autowired
    private LaptopServiceImpl laptopService;

    @Autowired
    private CopyService copyService;

    @Autowired
    private FineService fineService;

    @Rule
    public final JUnitSoftAssertions softly = new JUnitSoftAssertions();

    @Test
    @Transactional
    public void testCRUDOperationsOnLoan() {
        Date startDate = new Date(System.currentTimeMillis());
        Date endDate = new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(4));
        Loan loan = new Loan(1L, USERNAME, startDate, endDate);

        //Test Create and Retrieve operations
        loanService.save(loan);
        Long id = loan.getId();

        softly.assertThat(loanService.get(id).get())
                .as("Retrieve loan from database")
                .isEqualTo(loan);

        //Test Update operation
        loan.setReturnDate(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(7)));
        loanService.update(loan);

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
        bookService.save(book);

        Copy copy = ResourceFactory.createCopy(book, 4);
        copyService.save(copy);

        Loan loan = ResourceFactory.createLoanForCopy(copy, USERNAME);
        loanService.save(loan);

        Optional<Loan> loanRetrievedFromDatabase = loanService.get(loan.getId());

        softly.assertThat(loanRetrievedFromDatabase.get())
                .as("Loan retrieved from database is equal to the one saved to the database")
                .isEqualTo(loan);

        softly.assertThat(loanRetrievedFromDatabase.get().getCopyId())
                .as("The ID of the copy is the same as the one stored in the loan")
                .isEqualTo(copy.getId());
    }

    @Test
    @Transactional
    public void testBookReturnedLateAndFineCreated() {
        bookService.save(book);

        Copy copy = ResourceFactory.createCopy(book, 4);
        copyService.save(copy);

        Loan loan = createLateLoan(copy.getId());
        loanService.save(loan);

        softly.assertThat(loan.getEndDate())
                .as("Loan retrieved from database with end date before current time")
                .isBefore(new Date(System.currentTimeMillis()));

        softly.assertThat(loan.getReturnDate())
                .as("Loan retrieved from database with copy not yet returned")
                .isNull();

        loanService.endLoan(loan);
        List<Fine> fines = fineService.getAllFinesForUser(USERNAME);

        softly.assertThat(fines.get(0).getLoanId())
                .as("New fine created for late return of copy")
                .isEqualTo(loan.getId());

        softly.assertThat(fines.get(0).getUsername())
                .as("New fine for user")
                .isEqualTo(loan.getBorrowerUsername());

        Double expectedAmount = Fine.BOOK_FINE_AMOUNT_PER_DAY * DAYS_LATE;
        softly.assertThat(fines.get(0).getAmount())
                .as("New fine amount")
                .isEqualTo(expectedAmount);
    }

    @Test
    @Transactional
    public void testDvdReturnedLateAndFineCreated() {
        dvdService.save(dvd);

        Copy copy = ResourceFactory.createCopy(dvd, 4);
        copyService.save(copy);

        Loan loan = createLateLoan(copy.getId());
        loanService.save(loan);

        softly.assertThat(loan.getEndDate())
                .as("Loan retrieved from database with end date before current time")
                .isBefore(new Date(System.currentTimeMillis()));

        softly.assertThat(loan.getReturnDate())
                .as("Loan retrieved from database with copy not yet returned")
                .isNull();

        loanService.endLoan(loan);
        List<Fine> fines = fineService.getAllFinesForUser(USERNAME);

        softly.assertThat(fines.get(0).getLoanId())
                .as("New fine created for late return of copy")
                .isEqualTo(loan.getId());

        softly.assertThat(fines.get(0).getUsername())
                .as("New fine for user")
                .isEqualTo(loan.getBorrowerUsername());

        Double expectedAmount = Fine.DVD_FINE_AMOUNT_PER_DAY * DAYS_LATE;
        softly.assertThat(fines.get(0).getAmount())
                .as("New fine amount")
                .isEqualTo(expectedAmount);
    }

    @Test
    @Transactional
    public void testLaptopReturnedLateAndFineCreated() {
        laptopService.save(laptop);

        Copy copy = ResourceFactory.createCopy(laptop, 4);
        copyService.save(copy);

        Loan loan = createLateLoan(copy.getId());
        loanService.save(loan);

        softly.assertThat(loan.getEndDate())
                .as("Loan retrieved from database with end date before current time")
                .isBefore(new Date(System.currentTimeMillis()));

        softly.assertThat(loan.getReturnDate())
                .as("Loan retrieved from database with copy not yet returned")
                .isNull();

        loanService.endLoan(loan);
        List<Fine> fines = fineService.getAllFinesForUser(USERNAME);

        softly.assertThat(fines.get(0).getLoanId())
                .as("New fine created for late return of copy")
                .isEqualTo(loan.getId());

        softly.assertThat(fines.get(0).getUsername())
                .as("New fine for user")
                .isEqualTo(loan.getBorrowerUsername());

        Double expectedAmount = Fine.LAPTOP_FINE_AMOUNT_PER_DAY * DAYS_LATE;
        softly.assertThat(fines.get(0).getAmount())
                .as("New fine amount")
                .isEqualTo(expectedAmount);
    }

    @Test
    @Transactional
    public void testReturnCopyAndEndLoan() {
        bookService.save(book);

        Copy copy = ResourceFactory.createCopy(book, 4);
        copyService.save(copy);

        Loan loan = ResourceFactory.createLoanForCopy(copy, USERNAME);
        loanService.save(loan);

        softly.assertThat(loan.getReturnDate())
                .as("Loan retrieved from database with copy not yet returned")
                .isNull();

        loanService.endLoan(loan);
        softly.assertThat(loan.getReturnDate().toLocalDate())
                .as("Loan retrieved from database with copy now returned, with return date equal to today's date")
                .isEqualTo(new Date(System.currentTimeMillis()).toLocalDate());
    }

    @Test
    @Transactional
    public void testGetAllLoansForACopy() {
        bookService.save(book);

        Copy copy1 = ResourceFactory.createCopy(book, 4);
        Copy copy2 = ResourceFactory.createCopy(book, 7);
        copyService.save(copy1);
        copyService.save(copy2);

        Loan loan1 = ResourceFactory.createLoanForCopy(copy1, "One");
        Loan loan2 = ResourceFactory.createLoanForCopy(copy1, "Two");
        Loan loan3 = ResourceFactory.createLoanForCopy(copy1, "Three");
        Loan loan4 = ResourceFactory.createLoanForCopy(copy2, "Four");
        loanService.save(loan1);
        loanService.save(loan2);
        loanService.save(loan3);
        loanService.save(loan4);

        List<Loan> loansFromDatabase = loanService.getAllLoansForCopy(copy1.getId());

        softly.assertThat(loansFromDatabase)
                .as("Loans retrieved from database for the copy ID passed in")
                .containsExactly(loan1, loan2, loan3)
                .doesNotContain(loan4);
    }

    @Test
    @Transactional
    public void testGetAllLoansForUser() {
        bookService.save(book);
        dvdService.save(dvd);
        laptopService.save(laptop);

        Copy copy1 = ResourceFactory.createCopy(book, 4);
        Copy copy2 = ResourceFactory.createCopy(dvd, 7);
        Copy copy3 = ResourceFactory.createCopy(laptop, 14);
        copyService.save(copy1);
        copyService.save(copy2);
        copyService.save(copy3);

        Loan loan1 = ResourceFactory.createLoanForCopy(copy1, USERNAME);
        Loan loan2 = ResourceFactory.createLoanForCopy(copy2, USERNAME);
        Loan loan3 = ResourceFactory.createLoanForCopy(copy3, USERNAME);
        loanService.save(loan1);
        loanService.save(loan2);
        loanService.save(loan3);

        loanService.endLoan(loan1);

        Loan loanNotByUser = ResourceFactory.createLoanForCopy(copy1, "Other User");
        loanService.save(loanNotByUser);

        List<Loan> loansFromDatabase = loanService.getAllLoansForUser(USERNAME);

        softly.assertThat(loansFromDatabase)
                .as("Loans retrieved from database for the username passed in")
                .containsExactly(loan1, loan2, loan3)
                .doesNotContain(loanNotByUser);
    }

    private Loan createLateLoan(Long copyId) {
        Date startDate = new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(5));
        Date endDate = new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(DAYS_LATE));
        return new Loan(copyId, USERNAME, startDate, endDate);
    }
}
