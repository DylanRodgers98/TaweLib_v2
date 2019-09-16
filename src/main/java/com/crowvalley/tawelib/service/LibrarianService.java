package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.dao.LibrarianDAO;
import com.crowvalley.tawelib.model.user.Librarian;

import java.util.List;
import java.util.Optional;

public interface LibrarianService {

    Optional<Librarian> getWithUsername(String username);

    Optional<Librarian> getWithStaffNumber(Long staffNum);

    List<Librarian> getAll();

    void save(Librarian librarian);

    void update(Librarian librarian);

    void delete(Librarian librarian);

    void setLibrarianDAO(LibrarianDAO librarianDAO);
}
