package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.model.fine.Payment;
import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/applicationContext.xml "})
public class PaymentServiceImplIT {

    @Autowired
    private PaymentService paymentService;

    @Test
    @Transactional
    public void testCRDOperationsOnFine() {
        JUnitSoftAssertions softly = new JUnitSoftAssertions();
        Payment payment = new Payment("DylanRodgers98", 2.50);

        //Test Create and Retrieve operations
        paymentService.save(payment);
        List<Payment> payments = paymentService.getAll();
        Long id = payments.get(0).getId();

        softly.assertThat(paymentService.get(id).get())
                .as("Retrieve payment from database")
                .isEqualTo(payment);

        //PAYMENT HAS NO SETTER METHODS, SO UPDATE NEED NOT BE TESTED

        //Test Delete operation
        paymentService.delete(payment);

        softly.assertThat(paymentService.get(id))
                .as("Payment deleted")
                .isEqualTo(Optional.empty());
    }
}
