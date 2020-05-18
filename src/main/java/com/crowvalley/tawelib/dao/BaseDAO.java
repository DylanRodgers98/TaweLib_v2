package com.crowvalley.tawelib.dao;

import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public interface BaseDAO {

    <T> Optional<T> getWithId(Long id, Class<T> clazz);

    <T> List<T> getAll(Class<T> clazz);

    <T> void saveOrUpdate(T entity);

    <T> void delete(T entity);

    void setSessionFactory(SessionFactory sessionFactory);

}
