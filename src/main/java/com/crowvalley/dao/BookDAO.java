package com.crowvalley.dao;

import com.crowvalley.model.resource.Book;
import org.hibernate.SessionFactory;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

public class BookDAO implements IResourceDAO<Book> {

    @Resource
    private SessionFactory sessionFactory;

    @Override
    public Optional<Book> get(Long id) {
        return Optional.of(sessionFactory.getCurrentSession().get(Book.class, id));
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Book> getAll() {
        return sessionFactory.getCurrentSession().createQuery("from Book").list();
    }

    @Override
    public void save(Book book) {
        sessionFactory.getCurrentSession().save(book);
    }

    @Override
    public void update(Book book) {
        sessionFactory.getCurrentSession().update(book);
    }

    @Override
    public void delete(Book book) {
        sessionFactory.getCurrentSession().delete(book);
    }
}
