package com.crowvalley.dao;

import com.crowvalley.model.fine.Fine;

import java.util.List;
import java.util.Optional;

public interface FineDAO {

    Optional<Fine> get(Long id);

    List<Fine> getAll();

    void save(Fine fine);

    void delete(Fine fine);

}
