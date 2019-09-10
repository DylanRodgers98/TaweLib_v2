package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.dao.UserDAO;
import com.crowvalley.tawelib.model.user.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    public Optional<User> get(String username);

    public List<User> getAll();

    public void save(User user);

    public void update(User user);

    public void delete(User user);

    public void setDAO(UserDAO DAO);
}
