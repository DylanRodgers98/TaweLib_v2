package com.crowvalley.tawelib.dao;

import com.crowvalley.tawelib.model.user.User;

import java.util.List;
import java.util.Optional;

public interface UserDAO {

    Optional<User> get(String username);

    List<User> getAll();

    void save(User user);

    void update(User user);

    void delete(User user);

}
