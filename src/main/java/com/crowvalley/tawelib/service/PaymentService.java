package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.dao.PaymentDAO;
import com.crowvalley.tawelib.model.fine.Payment;

import java.util.List;
import java.util.Optional;

public interface PaymentService {

    public Optional<Payment> get(Long id);

    public List<Payment> getAll();

    public void save(Payment payment);

    public void delete(Payment payment);

    public void setDAO(PaymentDAO DAO);
}
