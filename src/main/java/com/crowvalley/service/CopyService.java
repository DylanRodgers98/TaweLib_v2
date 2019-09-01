package com.crowvalley.service;

import com.crowvalley.dao.CopyDAO;
import com.crowvalley.model.resource.Copy;

import java.util.List;
import java.util.Optional;

public interface CopyService {

    public Optional<Copy> get(Long id);

    public List<Copy> getAll();

    public void save(Copy copy);

    public void update(Copy copy);

    public void delete(Copy copy);

    public void setDAO(CopyDAO DAO);
}
