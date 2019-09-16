package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.dao.LibrarianDAO;
import com.crowvalley.tawelib.model.user.Librarian;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class LibrarianServiceImpl implements LibrarianService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LibrarianServiceImpl.class);

    @Autowired
    private LibrarianDAO librarianDAO;

    @Override
    public Optional<Librarian> getWithUsername(String username) {
        Optional<Librarian> librarian = librarianDAO.getWithUsername(username);
        if (librarian.isPresent()) {
            LOGGER.info("Librarian with username {} retrieved successfully", username);
            return librarian;
        } else {
            LOGGER.warn("Could not find librarian with username {}", username);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Librarian> getWithStaffNumber(Long staffNum) {
        Optional<Librarian> librarian = librarianDAO.getWithStaffNumber(staffNum);
        if (librarian.isPresent()) {
            LOGGER.info("Librarian with staff number {} retrieved successfully", staffNum);
            return librarian;
        } else {
            LOGGER.warn("Could not find librarian with staff number {}", staffNum);
            return Optional.empty();
        }
    }

    @Override
    public List<Librarian> getAll() {
        List<Librarian> users = librarianDAO.getAll();
        if (!users.isEmpty()) {
            LOGGER.info("All users retrieved successfully");
            return users;
        } else {
            LOGGER.warn("No users found");
            return users;
        }
    }

    @Override
    public void save(Librarian librarian){
        librarianDAO.save(librarian);
        LOGGER.info("Librarian with username {} and staff number {} saved successfully",
                librarian.getUsername(), librarian.getStaffNum());
    }

    @Override
    public void update(Librarian librarian) {
        librarianDAO.update(librarian);
        LOGGER.info("Librarian with username {} and staff number {} updated successfully",
                librarian.getUsername(), librarian.getStaffNum());
    }

    @Override
    public void delete(Librarian librarian) {
        librarianDAO.delete(librarian);
        LOGGER.info("Librarian with username {} and staff number {} deleted successfully",
                librarian.getUsername(), librarian.getStaffNum());
    }

    @Override
    public void setLibrarianDAO(LibrarianDAO librarianDAO) {
        this.librarianDAO = librarianDAO;
        LOGGER.info("UserServiceImpl DAO set to {}", librarianDAO.getClass());
    }

}
