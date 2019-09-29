package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.dao.FineDAO;
import com.crowvalley.tawelib.model.fine.Fine;

import java.util.List;
import java.util.Optional;

/**
 * Interface for service classes to implement that deal with
 * {@link Fine} objects persisted in a database.
 *
 * @author Dylan Rodgers
 */
public interface FineService {

    Optional<Fine> get(Long id);

    List<Fine> getAll();

    void save(Fine fine);

    void delete(Fine fine);

    void setDAO(FineDAO DAO);
}
