package com.crowvalley.service;

import com.crowvalley.dao.IResourceDAO;

import java.util.List;
import java.util.Optional;

public interface IResourceService<Resource> {

    public Optional<Resource> get(Long id);

    public List<Resource> getAll();

    public void save(Resource resource);

    public void update(Resource resource);

    public void delete(Resource resource);

    public void setDAO(IResourceDAO DAO);
}
