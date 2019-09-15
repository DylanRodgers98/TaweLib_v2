package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.dao.CopyDAO;
import com.crowvalley.tawelib.model.resource.Copy;

import java.util.List;
import java.util.Optional;

public interface CopyService {

    Optional<Copy> get(Long id);

    List<Copy> getAll();

    void save(Copy copy);

    void update(Copy copy);

    void delete(Copy copy);

    void setDAO(CopyDAO DAO);
}
