package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.dao.ResourceDAO;

import java.util.List;
import java.util.Optional;

/**
 * Interface for service classes to implement that deal with objects
 * of classes that extend {@link Resource} persisted in a database.
 *
 * @author Dylan Rodgers
 */
public interface ResourceService<Resource> {

    Optional<Resource> get(Long id);

    List<Resource> getAll();

    void save(Resource resource);

    void update(Resource resource);

    void delete(Resource resource);

    void setDAO(ResourceDAO DAO);
}
