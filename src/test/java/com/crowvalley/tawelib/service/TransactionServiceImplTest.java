package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.dao.TransactionDAO;
import com.crowvalley.tawelib.model.fine.Payment;
import com.crowvalley.tawelib.model.fine.Transaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceImplTest {

    private static final Transaction PAYMENT_1 = new Payment("User 1", new BigDecimal("2.50"), LocalDateTime.now());

    private static final Transaction PAYMENT_2 = new Payment("User 2", new BigDecimal("2.50"), LocalDateTime.now());

    private static final List<Transaction> PAYMENTS = Arrays.asList(PAYMENT_1, PAYMENT_2);

    @Mock
    private TransactionDAO DAO;

    @InjectMocks
    private TransactionServiceImpl paymentService;

    @Test
    public void testGetAll() {
        when(DAO.getAll(Transaction.class)).thenReturn(PAYMENTS);

        assertThat(paymentService.getAll())
                .as("Retrieve all payments stored in the database")
                .isEqualTo(PAYMENTS);
    }

    @Test
    public void testGetAll_noPaymentsFromDAO() {
        when(DAO.getAll(Transaction.class)).thenReturn(new ArrayList<>());

        assertThat(paymentService.getAll().isEmpty())
                .as("Retrieve no payments from DAO")
                .isTrue();
    }

    @Test
    public void test_verifySave() {
        paymentService.save(PAYMENT_1);
        verify(DAO).saveOrUpdate(PAYMENT_1);
    }

    @Test
    public void test_verifyDelete() {
        paymentService.delete(PAYMENT_2);
        verify(DAO).delete(PAYMENT_2);
    }

}
