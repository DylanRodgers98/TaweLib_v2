package com.crowvalley.service;

import com.crowvalley.dao.PaymentDAO;
import com.crowvalley.model.fine.Payment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

public class PaymentServiceImpl implements PaymentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentServiceImpl.class);

    @Resource
    private PaymentDAO DAO;

    @Override
    public Optional<Payment> get(Long id) {
        Optional<Payment> payment = DAO.get(id);
        if (payment.isPresent()) {
            LOGGER.info("Payment with ID {} retrieved successfully", id);
            return payment;
        } else {
            LOGGER.warn("Could not payment fine with ID {}", id);
            return Optional.empty();
        }
    }

    @Override
    public List<Payment> getAll() {
        List<Payment> payments = DAO.getAll();
        if (!payments.isEmpty()) {
            LOGGER.info("All payments retrieved successfully");
            return payments;
        } else {
            LOGGER.warn("No fines found");
            return payments;
        }
    }

    @Override
    public void save(Payment payment){
        DAO.save(payment);
        LOGGER.info("Payment with ID {} saved successfully", payment.getId());
    }

    @Override
    public void update(Payment payment) {
        DAO.update(payment);
        LOGGER.info("Payment with ID {} updated successfully", payment.getId());
    }

    @Override
    public void delete(Payment payment) {
        DAO.delete(payment);
        LOGGER.info("Payment with ID {} deleted successfully", payment.getId());
    }

    @Override
    public void setDAO(PaymentDAO DAO) {
        this.DAO = DAO;
        LOGGER.info("PaymentServiceImpl DAO set to {}", DAO.getClass());
    }

}
