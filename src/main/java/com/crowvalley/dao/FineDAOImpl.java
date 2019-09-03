package com.crowvalley.dao;

import com.crowvalley.model.fine.Fine;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class FineDAOImpl implements FineDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Optional<Fine> get(Long id) {
        Fine fine = sessionFactory.getCurrentSession().get(Fine.class, id);
        if (fine != null) {
            return Optional.of(fine);
        } else {
            return Optional.empty();
        }
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
