package com.crowvalley.dao;

import com.crowvalley.model.fine.Fine;
import org.hibernate.SessionFactory;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

public class FineDAOImpl implements FineDAO {

    @Resource
    private SessionFactory sessionFactory;

    @Override
    public Optional<Fine> get(Long id) {
        return Optional.of(sessionFactory.getCurrentSession().get(Fine.class, id));
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Fine> getAll() {
        return sessionFactory.getCurrentSession().createQuery("from Fine").list();
    }

    @Override
    public void save(Fine fine) {
        sessionFactory.getCurrentSession().save(fine);
    }

    @Override
    public void update(Fine fine) {
        sessionFactory.getCurrentSession().update(fine);
    }

    @Override
    public void delete(Fine fine) {
        sessionFactory.getCurrentSession().delete(fine);
    }
}
