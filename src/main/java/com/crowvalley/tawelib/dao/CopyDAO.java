package com.crowvalley.tawelib.dao;

import com.crowvalley.tawelib.model.resource.Copy;

import java.util.List;
import java.util.Optional;

/**
 * Data Access Object interface for classes to implement that deal with
 * {@link Copy} objects persisted in a database.
 *
 * @author Dylan Rodgers
 */
public interface CopyDAO {

    Optional<Copy> get(Long id);

    List<Copy> getAll();

    void save(Copy copy);

    void update(Copy copy);

    void delete(Copy copy);

}
