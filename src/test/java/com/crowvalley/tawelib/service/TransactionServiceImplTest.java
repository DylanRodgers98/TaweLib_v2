package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.dao.TransactionDAO;
import com.crowvalley.tawelib.dao.TransactionDAOImpl;
import com.crowvalley.tawelib.model.fine.Payment;
import com.crowvalley.tawelib.model.fine.Transaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceImplTest {

    private static final Transaction payment1 = new Payment("User 1", new BigDecimal("2.50"));

    private static final Transaction payment2 = new Payment("User 2", new BigDecimal("2.50"));

    private static final List<Transaction> payments = Arrays.asList(payment1, payment2);

    @Mock
    private TransactionDAO DAO;

    @InjectMocks
    private TransactionServiceImpl paymentService;

    @Test
    public void testGetAll() {
        when(DAO.getAll(Transaction.class)).thenReturn(payments);

        assertThat(paymentService.getAll())
                .as("Retrieve all payments stored in the database")
                .isEqualTo(payments);
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
        paymentService.save(payment1);
        verify(DAO).saveOrUpdate(payment1);
    }

    @Test
    public void test_verifyDelete() {
        paymentService.delete(payment2);
        verify(DAO).delete(payment2);
    }

}
