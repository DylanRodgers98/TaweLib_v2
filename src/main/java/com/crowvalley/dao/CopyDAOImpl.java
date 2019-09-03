package com.crowvalley.dao;

import com.crowvalley.model.resource.Copy;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class CopyDAOImpl implements CopyDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Optional<Copy> get(Long id) {
        Copy copy = sessionFactory.getCurrentSession().get(Copy.class, id);
        if (copy != null) {
            return Optional.of(copy);
        } else {
            return Optional.empty();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Copy> getAll() {
        return sessionFactory.getCurrentSession().createQuery("from Copy").list();
    }

    @Override
    public void save(Copy copy) {
        sessionFactory.getCurrentSession().save(copy);
    }

    @Override
    public void update(Copy copy) {
        sessionFactory.getCurrentSession().update(copy);
    }

    @Override
    public void delete(Copy copy) {
        sessionFactory.getCurrentSession().delete(copy);
    }
}
