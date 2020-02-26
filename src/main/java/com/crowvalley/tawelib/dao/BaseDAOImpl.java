package com.crowvalley.tawelib.dao;

import com.crowvalley.tawelib.model.resource.Resource;
import com.crowvalley.tawelib.util.DatabaseUtils;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class BaseDAOImpl implements BaseDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseDAOImpl.class);

    protected SessionFactory sessionFactory;

    @Override
    public <T> Optional<T> getWithId(Long id, Class<T> clazz) {
        T entity = sessionFactory.getCurrentSession().get(clazz, id);
        return Optional.ofNullable(entity);
    }

    @Override
    public <T> List<T> getAll(Class<T> clazz) {
        return DatabaseUtils.getAll(clazz, sessionFactory);
    }

    @Override
    public <T> void saveOrUpdate(T entity) {
        sessionFactory.getCurrentSession().saveOrUpdate(entity);
    }

    @Override
    public <T> void delete(T entity) {
        sessionFactory.getCurrentSession().delete(entity);
    }

    @Override
    public <T> void deleteWithId(Long id, Class<T> clazz) {
        sessionFactory.getCurrentSession()
                .createQuery("delete " + clazz.getName() + " where id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        LOGGER.info("{} SessionFactory set to {}", this.getClass().getSimpleName(), sessionFactory.getClass().getSimpleName());
    }

}
