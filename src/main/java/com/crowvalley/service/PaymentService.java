package com.crowvalley.service;

import com.crowvalley.dao.PaymentDAO;
import com.crowvalley.model.fine.Payment;

import java.util.List;
import java.util.Optional;

public interface PaymentService {

    public Optional<Payment> get(Long id);

    public List<Payment> getAll();

    public void save(Payment payment);

    public void delete(Payment payment);

    public void setDAO(PaymentDAO DAO);
}
