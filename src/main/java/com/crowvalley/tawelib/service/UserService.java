package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.dao.UserDAO;
import com.crowvalley.tawelib.model.user.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> get(String username);

    List<User> getAll();

    void save(User user);

    void update(User user);

    void delete(User user);

    void setDAO(UserDAO DAO);
}
