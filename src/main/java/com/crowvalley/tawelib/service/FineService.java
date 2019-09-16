package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.dao.FineDAO;
import com.crowvalley.tawelib.model.fine.Fine;

import java.util.List;
import java.util.Optional;

public interface FineService {

    Optional<Fine> get(Long id);

    List<Fine> getAll();

    void save(Fine fine);

    void delete(Fine fine);

    void setFineDAO(FineDAO fineDAO);
}
