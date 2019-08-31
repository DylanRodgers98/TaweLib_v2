package com.crowvalley.service;

import com.crowvalley.dao.ILibrarianDAO;
import com.crowvalley.model.user.Librarian;

import java.util.List;
import java.util.Optional;

public interface ILibrarianService {

    public Optional<Librarian> getWithUsername(String username);

    public Optional<Librarian> getWithStaffNumber(Long staffNum);

    public List<Librarian> getAll();

    public void save(Librarian librarian);

    public void update(Librarian librarian);

    public void delete(Librarian librarian);

    public void setDAO(ILibrarianDAO DAO);
}
