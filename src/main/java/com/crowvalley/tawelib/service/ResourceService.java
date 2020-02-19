package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.dao.BaseDAO;
import com.crowvalley.tawelib.model.resource.Resource;

import java.util.List;
import java.util.Optional;

/**
 * Interface for service classes to implement that deal with objects
 * of classes that extend {@link Resource} persisted in a database.
 *
 * @author Dylan Rodgers
 */
public interface ResourceService {

    Optional<? extends Resource> get(Long id, Class<? extends Resource> clazz);

    List<? extends Resource> getAll();

    void saveOrUpdate(Resource resource);

    void delete(Resource resource);

    void setDAO(BaseDAO DAO);
}
