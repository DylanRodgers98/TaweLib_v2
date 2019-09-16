package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.dao.PaymentDAO;
import com.crowvalley.tawelib.model.fine.Payment;

import java.util.List;
import java.util.Optional;

public interface PaymentService {

    Optional<Payment> get(Long id);

    List<Payment> getAll();

    void save(Payment payment);

    void delete(Payment payment);

    void setPaymentDAO(PaymentDAO paymentDAO);
}
