package com.crowvalley.dao;

import com.crowvalley.model.fine.Payment;

import java.util.List;
import java.util.Optional;

public interface PaymentDAO {

    Optional<Payment> get(Long id);

    List<Payment> getAll();

    void save(Payment payment);

    void update(Payment payment);

    void delete(Payment payment);

}
