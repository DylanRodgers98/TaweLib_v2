package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.dao.ResourceDAO;

import java.util.List;
import java.util.Optional;

public interface ResourceService<Resource> {

    Optional<Resource> get(Long id);

    List<Resource> getAll();

    void save(Resource resource);

    void update(Resource resource);

    void delete(Resource resource);

    void setDAO(ResourceDAO DAO);
}
