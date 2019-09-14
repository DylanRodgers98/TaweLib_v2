package com.crowvalley.tawelib.dao;

import com.crowvalley.tawelib.model.fine.Fine;

import java.util.List;
import java.util.Optional;

public interface FineDAO {

    Optional<Fine> get(Long id);

    List<Fine> getAll();

    void save(Fine fine);

    void delete(Fine fine);

}