package com.crowvalley.service;

import com.crowvalley.dao.IUserDAO;
import com.crowvalley.model.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements IUserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Resource
    private IUserDAO DAO;

    public Optional<User> get(String username) {
        Optional<User> user = DAO.get(username);
        if (user.isPresent()) {
            LOGGER.info("User with username {} retrieved successfully", username);
            return user;
        } else {
            LOGGER.warn("Could not find user with username {}", username);
            return Optional.empty();
        }
    }

    public List<User> getAll() {
        List<User> users = DAO.getAll();
        if (!users.isEmpty()) {
            LOGGER.info("All users retrieved successfully");
            return users;
        } else {
            LOGGER.warn("No users found");
            return users;
        }
    }

    public void save(User user){
        DAO.save(user);
        LOGGER.info("User with username {} saved successfully", user.getUsername());
    }

    public void update(User user) {
        DAO.update(user);
        LOGGER.info("User with username {} updated successfully", user.getUsername());
    }

    public void delete(User user) {
        DAO.delete(user);
        LOGGER.info("User with username {} deleted successfully", user.getUsername());
    }

    public void setDAO(IUserDAO DAO) {
        this.DAO = DAO;
        LOGGER.info("UserServiceImpl DAO set to {}", DAO.getClass());
    }

}
