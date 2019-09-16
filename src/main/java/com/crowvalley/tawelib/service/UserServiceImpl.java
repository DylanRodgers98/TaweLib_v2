package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.dao.UserDAO;
import com.crowvalley.tawelib.model.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDAO userDAO;

    @Override
    public Optional<User> get(String username) {
        Optional<User> user = userDAO.get(username);
        if (user.isPresent()) {
            LOGGER.info("User with username {} retrieved successfully", username);
            return user;
        } else {
            LOGGER.warn("Could not find user with username {}", username);
            return Optional.empty();
        }
    }

    @Override
    public List<User> getAll() {
        List<User> users = userDAO.getAll();
        if (!users.isEmpty()) {
            LOGGER.info("All users retrieved successfully");
            return users;
        } else {
            LOGGER.warn("No users found");
            return users;
        }
    }

    @Override
    public void save(User user){
        userDAO.save(user);
        LOGGER.info("User with username {} saved successfully", user.getUsername());
    }

    @Override
    public void update(User user) {
        userDAO.update(user);
        LOGGER.info("User with username {} updated successfully", user.getUsername());
    }

    @Override
    public void delete(User user) {
        userDAO.delete(user);
        LOGGER.info("User with username {} deleted successfully", user.getUsername());
    }

    @Override
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
        LOGGER.info("UserServiceImpl DAO set to {}", userDAO.getClass());
    }

}
