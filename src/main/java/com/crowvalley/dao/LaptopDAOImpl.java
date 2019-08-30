package com.crowvalley.dao;

import com.crowvalley.model.resource.Laptop;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.annotation.Resource;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

public class LaptopDAOImpl implements ResourceDAO<Laptop> {

    @Resource
    private SessionFactory sessionFactory;

    @Override
    public Optional<Laptop> get(Long id) {
        return Optional.of(sessionFactory.getCurrentSession().get(Laptop.class, id));
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Laptop> getAll() {
        return sessionFactory.getCurrentSession().createQuery("from Laptop").list();
    }

    @Override
    public void save(Laptop laptop) {
        sessionFactory.getCurrentSession().save(laptop);
    }

    @Override
    public void update(Laptop laptop) {
        sessionFactory.getCurrentSession().update(laptop);
    }

    @Override
    public void delete(Laptop laptop) {
        sessionFactory.getCurrentSession().delete(laptop);
    }
}
