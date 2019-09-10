package com.crowvalley.tawelib.dao;

import com.crowvalley.tawelib.model.fine.Payment;

import java.util.List;
import java.util.Optional;

public interface PaymentDAO {

    Optional<Payment> get(Long id);

    List<Payment> getAll();

    void save(Payment payment);

    void delete(Payment payment);

}
