package com.crowvalley.tawelib.dao;

import com.crowvalley.tawelib.model.fine.Payment;

import java.util.List;
import java.util.Optional;

/**
 * Data Access Object interface for classes to implement that deal with
 * {@link Payment} objects persisted in a database.
 *
 * @author Dylan Rodgers
 */
public interface PaymentDAO {

    Optional<Payment> get(Long id);

    List<Payment> getAll();

    List<Payment> getAllPaymentsForUser(String username);

    void save(Payment payment);

    void delete(Payment payment);

}
