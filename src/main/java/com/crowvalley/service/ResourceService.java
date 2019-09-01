package com.crowvalley.service;

import com.crowvalley.dao.ResourceDAO;

import java.util.List;
import java.util.Optional;

public interface ResourceService<Resource> {

    public Optional<Resource> get(Long id);

    public List<Resource> getAll();

    public void save(Resource resource);

    public void update(Resource resource);

    public void delete(Resource resource);

    public void setDAO(ResourceDAO DAO);
}
