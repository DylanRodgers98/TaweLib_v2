package com.crowvalley.service;

import com.crowvalley.dao.IUserDAO;
import com.crowvalley.model.user.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    public Optional<User> get(String username);

    public List<User> getAll();

    public void save(User user);

    public void update(User user);

    public void delete(User user);

    public void setDAO(IUserDAO DAO);
}
