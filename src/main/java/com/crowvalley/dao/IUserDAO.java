package com.crowvalley.dao;

import com.crowvalley.model.user.User;

import java.util.List;
import java.util.Optional;

public interface IUserDAO {

    Optional<User> get(String username);

    List<User> getAll();

    void save(User user);

    void update(User user);

    void delete(User user);

}
