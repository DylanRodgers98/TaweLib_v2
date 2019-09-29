package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.dao.PaymentDAO;
import com.crowvalley.tawelib.model.fine.Payment;

import java.util.List;
import java.util.Optional;

/**
 * Interface for service classes to implement that deal with
 * {@link Payment} objects persisted in a database.
 *
 * @author Dylan Rodgers
 */
public interface PaymentService {

    Optional<Payment> get(Long id);

    List<Payment> getAll();

    void save(Payment payment);

    void delete(Payment payment);

    void setDAO(PaymentDAO DAO);
}
