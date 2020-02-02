package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.dao.UserDAO;
import com.crowvalley.tawelib.model.user.User;

import java.util.List;
import java.util.Optional;

/**
 * Interface for service classes to implement that deal with
 * {@link User} objects persisted in a database.
 *
 * @author Dylan Rodgers
 */
public interface UserService {

    Optional<User> get(String username);

    List<User> getAll();

    List<String> getAllUsernames();

    void save(User user);

    void update(User user);

    void delete(User user);

    void setDAO(UserDAO DAO);
}
