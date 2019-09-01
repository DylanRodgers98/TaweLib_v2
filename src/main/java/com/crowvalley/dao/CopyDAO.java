package com.crowvalley.dao;

import com.crowvalley.model.resource.Copy;

import java.util.List;
import java.util.Optional;

public interface CopyDAO {

    Optional<Copy> get(Long id);

    List<Copy> getAll();

    void save(Copy copy);

    void update(Copy copy);

    void delete(Copy copy);

}
