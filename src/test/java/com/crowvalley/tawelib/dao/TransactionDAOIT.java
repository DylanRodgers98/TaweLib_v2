package com.crowvalley.tawelib.dao;

import com.crowvalley.tawelib.model.fine.Fine;
import com.crowvalley.tawelib.model.fine.Payment;
import com.crowvalley.tawelib.model.fine.Transaction;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/spring/applicationContext.xml")
@Transactional
public class TransactionDAOIT {

    private static final String USERNAME = "TEST_USER";

    @Autowired
    private TransactionDAO transactionDAO;

    @Test
    public void testGetAllTransactionsForUser() {
        Transaction fine = new Fine(USERNAME, null, null, null);
        Transaction payment = new Payment(USERNAME, null, null);
        transactionDAO.saveOrUpdate(fine);
        transactionDAO.saveOrUpdate(payment);

        List<Transaction> transactions = transactionDAO.getAllTransactionsForUser(USERNAME);

        assertThat(transactions)
                .as("List of Transactions for a given username contains the newly persisted Transaction entities")
                .contains(fine, payment);
    }

    @Test
    public void testGetTotalFinesAmountForUser() {
        Transaction fourPoundFine = new Fine(USERNAME, null, new BigDecimal("4"), null);
        Transaction sixPoundFine = new Fine(USERNAME, null, new BigDecimal("6"), null);
        transactionDAO.saveOrUpdate(fourPoundFine);
        transactionDAO.saveOrUpdate(sixPoundFine);

        BigDecimal totalFinesAmount = transactionDAO.getTotalFinesAmountForUser(USERNAME);

        assertThat(totalFinesAmount)
                .as("Total Fines amount for user calculated correctly")
                .isEqualByComparingTo(new BigDecimal("10"));
    }

    @Test
    public void testGetTotalPaymentsAmountForUser() {
        Transaction fourPoundPayment = new Payment(USERNAME, new BigDecimal("4"), null);
        Transaction sixPoundPayment = new Payment(USERNAME, new BigDecimal("6"), null);
        transactionDAO.saveOrUpdate(fourPoundPayment);
        transactionDAO.saveOrUpdate(sixPoundPayment);

        BigDecimal totalPaymentsAmount = transactionDAO.getTotalPaymentsAmountForUser(USERNAME);

        assertThat(totalPaymentsAmount)
                .as("Total Payments amount for user calculated correctly")
                .isEqualByComparingTo(new BigDecimal("10"));
    }

    @Test
    public void testSearch() {
        Transaction fine = new Fine(USERNAME, null, null, LocalDateTime.now().minusDays(6));
        Transaction payment = new Payment(USERNAME, null, LocalDateTime.now().minusDays(1));
        transactionDAO.saveOrUpdate(fine);
        transactionDAO.saveOrUpdate(payment);

        List<Transaction> transactionsSearchedWithUsername = transactionDAO.search(USERNAME, null, null);
        assertThat(transactionsSearchedWithUsername)
                .as("List of Transaction entities retrieved from database by searching by username")
                .contains(fine, payment);

        List<Transaction> transactionsSearchedWithDate = transactionDAO.search(null, LocalDateTime.now().minusDays(1), LocalDateTime.now());
        assertThat(transactionsSearchedWithDate)
                .as("List of Transaction entities retrieved from database by searching by date from yesterday")
                .contains(payment);

        transactionsSearchedWithDate = transactionDAO.search(null, LocalDateTime.now().minusWeeks(1), LocalDateTime.now());
        assertThat(transactionsSearchedWithDate)
                .as("List of Transaction entities retrieved from database by searching by date from last fortnight")
                .contains(fine, payment);

        List<Transaction> transactionsSearchedDifferentUsername = transactionDAO.search("Not Test User", null, null);
        assertThat(transactionsSearchedDifferentUsername)
                .as("List of Transaction entities retrieved from database by searching with a different username is empty")
                .isEmpty();
    }

}
