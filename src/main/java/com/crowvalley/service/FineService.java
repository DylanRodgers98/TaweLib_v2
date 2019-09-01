package com.crowvalley.service;

import com.crowvalley.dao.FineDAO;
import com.crowvalley.model.fine.Fine;

import java.util.List;
import java.util.Optional;

public interface FineService {

    public Optional<Fine> get(Long id);

    public List<Fine> getAll();

    public void save(Fine fine);

    public void update(Fine fine);

    public void delete(Fine fine);

    public void setDAO(FineDAO DAO);
}
