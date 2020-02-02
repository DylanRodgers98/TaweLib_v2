package com.crowvalley.tawelib.dao;

import com.crowvalley.tawelib.model.user.Librarian;
import com.crowvalley.tawelib.model.user.User;

import java.util.List;
import java.util.Optional;

/**
 * Data Access Object interface for classes to implement that deal with
 * {@link User} objects persisted in a database, but not objects of
 * classes that extend {@link User} (e.g. {@link Librarian} objects).
 *
 * @author Dylan Rodgers
 */
public interface UserDAO {

    Optional<User> get(String username);

    List<User> getAll();

    List<String> getAllUsernames();

    void save(User user);

    void update(User user);

    void delete(User user);

}
