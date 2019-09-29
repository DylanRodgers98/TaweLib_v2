package com.crowvalley.tawelib.dao;

import com.crowvalley.tawelib.model.fine.Fine;

import java.util.List;
import java.util.Optional;

/**
 * Data Access Object interface for classes to implement that deal with
 * {@link Fine} objects persisted in a database.
 *
 * @author Dylan Rodgers
 */
public interface FineDAO {

    Optional<Fine> get(Long id);

    List<Fine> getAllFinesForUser(String username);

    List<Fine> getAll();

    void save(Fine fine);

    void delete(Fine fine);

}
