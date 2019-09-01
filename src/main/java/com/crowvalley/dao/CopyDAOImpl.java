package com.crowvalley.dao;

import com.crowvalley.model.resource.Copy;
import org.hibernate.SessionFactory;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

public class CopyDAOImpl implements CopyDAO {

    @Resource
    private SessionFactory sessionFactory;

    @Override
    public Optional<Copy> get(Long id) {
        return Optional.of(sessionFactory.getCurrentSession().get(Copy.class, id));
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
