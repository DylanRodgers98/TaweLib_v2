package com.crowvalley.dao;

import com.crowvalley.model.resource.Dvd;
import org.hibernate.SessionFactory;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

public class DvdDAOImpl implements ResourceDAO<Dvd> {

    @Resource
    private SessionFactory sessionFactory;

    @Override
    public Optional<Dvd> get(Long id) {
        return Optional.of(sessionFactory.getCurrentSession().get(Dvd.class, id));
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Dvd> getAll() {
        return sessionFactory.getCurrentSession().createQuery("from Dvd").list();
    }

    @Override
    public void save(Dvd dvd) {
        sessionFactory.getCurrentSession().save(dvd);
    }

    @Override
    public void update(Dvd dvd) {
        sessionFactory.getCurrentSession().update(dvd);
    }

    @Override
    public void delete(Dvd dvd) {
        sessionFactory.getCurrentSession().delete(dvd);
    }
}
