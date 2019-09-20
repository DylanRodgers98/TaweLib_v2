package com.crowvalley.tawelib.dao;

import com.crowvalley.tawelib.model.user.Librarian;
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
        return Optional.ofNullable(librarian);
    }

    @Override
    public Optional<Librarian> getWithStaffNumber(Long staffNum) {
        List<Librarian> librarians = sessionFactory.getCurrentSession()
                .createCriteria(Librarian.class)
                .add(Restrictions.eq("staffNum", staffNum))
                .list();

        if (librarians.size() > 1) {
            throw new IllegalStateException("More than one Librarian retrieved from database with same staff number");
        }

        if (!librarians.isEmpty()) {
            return Optional.of(librarians.get(0));
        } else {
            return Optional.empty();
        }
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
