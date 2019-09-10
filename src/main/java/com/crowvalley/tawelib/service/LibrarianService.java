package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.dao.LibrarianDAO;
import com.crowvalley.tawelib.model.user.Librarian;

import java.util.List;
import java.util.Optional;

public interface LibrarianService {

    public Optional<Librarian> getWithUsername(String username);

    public Optional<Librarian> getWithStaffNumber(Long staffNum);

    public List<Librarian> getAll();

    public void save(Librarian librarian);

    public void update(Librarian librarian);

    public void delete(Librarian librarian);

    public void setDAO(LibrarianDAO DAO);
}
