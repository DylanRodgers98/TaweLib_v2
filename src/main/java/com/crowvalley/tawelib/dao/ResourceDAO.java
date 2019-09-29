package com.crowvalley.tawelib.dao;

import java.util.List;
import java.util.Optional;

/**
 * Data Access Object interface for classes to implement that deal with
 * {@link Resource} objects persisted in a database.
 *
 * @author Dylan Rodgers
 */
public interface ResourceDAO<Resource> {

    Optional<Resource> get(Long id);

    List<Resource> getAll();

    void save(Resource resource);

    void update(Resource resource);

    void delete(Resource resource);

}
