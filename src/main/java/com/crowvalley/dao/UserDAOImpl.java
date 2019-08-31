package com.crowvalley.dao;

import com.crowvalley.model.user.User;
import org.hibernate.SessionFactory;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

public class UserDAOImpl implements IUserDAO {

    @Resource
    private SessionFactory sessionFactory;

    @Override
    public Optional<User> get(String username) {
        return Optional.of(sessionFactory.getCurrentSession().get(User.class, username));
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAll() {
        return sessionFactory.getCurrentSession().createQuery("from User").list();
    }

    @Override
    public void save(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    public void update(User user) {
        sessionFactory.getCurrentSession().update(user);
    }

    @Override
    public void delete(User user) {
        sessionFactory.getCurrentSession().delete(user);
    }
}
