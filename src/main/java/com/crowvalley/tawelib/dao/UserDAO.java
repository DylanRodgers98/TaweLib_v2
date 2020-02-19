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
public interface UserDAO extends BaseDAO {

    List<String> getAllUsernames();

    Optional<? extends User> getWithUsername(String username);

    Optional<Librarian> getLibrarianUserWithStaffNumber(Long staffNum);

}
