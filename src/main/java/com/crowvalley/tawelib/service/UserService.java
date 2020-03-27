package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.dao.UserDAO;
import com.crowvalley.tawelib.model.user.Librarian;
import com.crowvalley.tawelib.model.user.User;
import com.crowvalley.tawelib.model.fine.OutstandingFinesDTO;

import java.util.List;
import java.util.Optional;

/**
 * Interface for service classes to implement that deal with
 * {@link User} objects persisted in a database.
 *
 * @author Dylan Rodgers
 */
public interface UserService {

    Optional<? extends User> getWithUsername(String username);

    Optional<Librarian> getLibrarianUserWithStaffNumber(Long staffNum);

    List<? extends User> getAll();

    void saveOrUpdate(User user);

    void delete(User user);

    List<String> getAllUsernames();

    List<OutstandingFinesDTO> getAllUsersWithOutstandingFines();

    void setUserDAO(UserDAO userDAO);
}
