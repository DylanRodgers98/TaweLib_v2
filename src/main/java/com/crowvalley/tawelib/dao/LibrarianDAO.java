package com.crowvalley.tawelib.dao;

import com.crowvalley.tawelib.model.user.Librarian;

import java.util.List;
import java.util.Optional;

public interface LibrarianDAO {

    Optional<Librarian> getWithUsername(String username);

    Optional<Librarian> getWithStaffNumber(Long staffNum);

    List<Librarian> getAll();

    void save(Librarian librarian);

    void update(Librarian librarian);

    void delete(Librarian librarian);

}