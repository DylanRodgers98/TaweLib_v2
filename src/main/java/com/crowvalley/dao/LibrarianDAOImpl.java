package com.crowvalley.dao;

import com.crowvalley.model.user.Librarian;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class LibrarianDAOImpl implements LibrarianDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Optional<Librarian> getWithUsername(String username) {
        Librarian librarian = sessionFactory.getCurrentSession().get(Librarian.class, username);
        if (librarian != null) {
            return Optional.of(librarian);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Librarian> getWithStaffNumber(Long staffNum) {
        Librarian librarian = (Librarian) sessionFactory.getCurrentSession()
                .createCriteria(Librarian.class)
                .add(Restrictions.eq("staffNum", staffNum))
                .setMaxResults(1)
                .uniqueResult();
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
