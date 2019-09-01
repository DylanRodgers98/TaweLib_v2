package com.crowvalley.dao;

import com.crowvalley.model.user.Librarian;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

public class LibrarianDAOImpl implements LibrarianDAO {

    @Resource
    private SessionFactory sessionFactory;

    @Override
    public Optional<Librarian> getWithUsername(String username) {
        return Optional.of(sessionFactory.getCurrentSession().get(Librarian.class, username));
    }

    @Override
    public Optional<Librarian> getWithStaffNumber(Long staffNum) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Librarian.class);
        Librarian librarian = (Librarian) criteria.add(Restrictions.eq("staffNum", staffNum));
        return Optional.of(librarian);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Librarian> getAll() {
        return sessionFactory.getCurrentSession().createQuery("from Librarian").list();
    }

    @Override
    public void save(Librarian librarian) {
        sessionFactory.getCurrentSession().save(librarian);
    }

    @Override
    public void update(Librarian librarian) {
        sessionFactory.getCurrentSession().update(librarian);
    }

    @Override
    public void delete(Librarian librarian) {
        sessionFactory.getCurrentSession().delete(librarian);
    }
}
