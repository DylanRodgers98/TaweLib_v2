package com.crowvalley.service;

import com.crowvalley.dao.ILibrarianDAO;
import com.crowvalley.model.user.Librarian;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

public class LibrarianServiceImpl implements ILibrarianService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LibrarianServiceImpl.class);

    @Resource
    private ILibrarianDAO DAO;

    public Optional<Librarian> getWithUsername(String username) {
        Optional<Librarian> librarian = DAO.getWithUsername(username);
        if (librarian.isPresent()) {
            LOGGER.info("Librarian with username {} retrieved successfully", username);
            return librarian;
        } else {
            LOGGER.warn("Could not find librarian with username {}", username);
            return Optional.empty();
        }
    }

    public Optional<Librarian> getWithStaffNumber(Long staffNum) {
        Optional<Librarian> librarian = DAO.getWithStaffNumber(staffNum);
        if (librarian.isPresent()) {
            LOGGER.info("Librarian with staff number {} retrieved successfully", staffNum);
            return librarian;
        } else {
            LOGGER.warn("Could not find librarian with staff number {}", staffNum);
            return Optional.empty();
        }
    }

    public List<Librarian> getAll() {
        List<Librarian> users = DAO.getAll();
        if (!users.isEmpty()) {
            LOGGER.info("All users retrieved successfully");
            return users;
        } else {
            LOGGER.warn("No users found");
            return users;
        }
    }

    public void save(Librarian librarian){
        DAO.save(librarian);
        LOGGER.info("Librarian with username {} and staff number {} saved successfully",
                librarian.getUsername(), librarian.getStaffNum());
    }

    public void update(Librarian librarian) {
        DAO.update(librarian);
        LOGGER.info("Librarian with username {} and staff number {} updated successfully",
                librarian.getUsername(), librarian.getStaffNum());
    }

    public void delete(Librarian librarian) {
        DAO.delete(librarian);
        LOGGER.info("Librarian with username {} and staff number {} deleted successfully",
                librarian.getUsername(), librarian.getStaffNum());
    }

    public void setDAO(ILibrarianDAO DAO) {
        this.DAO = DAO;
        LOGGER.info("UserServiceImpl DAO set to {}", DAO.getClass());
    }

}
