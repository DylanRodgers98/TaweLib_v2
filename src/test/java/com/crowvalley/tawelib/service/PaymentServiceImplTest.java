package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.dao.PaymentDAOImpl;
import com.crowvalley.tawelib.model.fine.Payment;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PaymentServiceImplTest {

    @Mock
    private PaymentDAOImpl DAO;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    private Payment payment1;

    private Payment payment2;

    private Payment payment3;

    private Optional<Payment> optionalPayment1;

    private Optional<Payment> optionalPayment2;

    private Optional<Payment> optionalPayment3;

    private List<Payment> payments;

    @Before
    public void setup() {
        payment1 = new Payment("User 1",2.50);
        payment2 = new Payment("User 2",0.90);
        payment3 = new Payment("User 3",10.00);
        optionalPayment1 = Optional.of(payment1);
        optionalPayment2 = Optional.of(payment2);
        optionalPayment3 = Optional.of(payment3);
        payments = new ArrayList<>();
        payments.add(payment1);
        payments.add(payment2);
        payments.add(payment3);
    }

    @Test
    public void testGet() {
        Long id = Long.valueOf(1);

        when(DAO.get(id)).thenReturn(optionalPayment1);

        assertThat(paymentService.get(id))
                .as("Retrieve payment from database with ID 1")
                .isEqualTo(optionalPayment1);
    }

    @Test
    public void testGet_noPaymentFromDAO() {
        Long id = Long.valueOf(4);

        when(DAO.get(id)).thenReturn(Optional.empty());

        assertThat(paymentService.get(id))
                .as("Retrieve empty Optional from DAO")
                .isEqualTo(Optional.empty());
    }

    @Test
    public void testGetAll() {
        when(DAO.getAll()).thenReturn(payments);

        assertThat(paymentService.getAll())
                .as("Retrieve all payments stored in the database")
                .isEqualTo(payments);
    }

    @Test
    public void testGetAll_noPaymentsFromDAO() {
        when(DAO.getAll()).thenReturn(new ArrayList<>());

        assertThat(paymentService.getAll().isEmpty())
                .as("Retrieve no payments from DAO")
                .isTrue();
    }

    @Test
    public void test_verifySave() {
        paymentService.save(payment1);
        verify(DAO).save(eq(payment1));
    }

    @Test
    public void test_verifyDelete() {
        paymentService.delete(payment3);
        verify(DAO).delete(eq(payment3));
    }
}
