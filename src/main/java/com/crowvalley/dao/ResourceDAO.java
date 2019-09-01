package com.crowvalley.dao;

import java.util.List;
import java.util.Optional;

public interface ResourceDAO<Resource> {

    Optional<Resource> get(Long id);

    List<Resource> getAll();

    void save(Resource resource);

    void update(Resource resource);

    void delete(Resource resource);

}
