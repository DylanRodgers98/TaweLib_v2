package com.crowvalley.tawelib.util;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import java.util.List;
import java.util.stream.Collectors;

public class DatabaseUtils {

    public static <T> List<T> getAll(Class<T> clazz, SessionFactory sessionFactory) {
        return castList(sessionFactory.getCurrentSession()
                .createQuery("from " + clazz.getName())
                .list(), clazz);
    }

    public static <T> List<T> getAll(Class<T> clazz, SessionFactory sessionFactory, String propertyName, Object propertyValue) {
        return castList(sessionFactory.getCurrentSession()
                .createCriteria(clazz)
                .add(Restrictions.eq(propertyName, propertyValue))
                .list(), clazz);
    }

    public static <T> List<T> castList(List<?> listToBeCasted, Class<T> clazz) {
        return listToBeCasted.stream().map(clazz::cast).collect(Collectors.toList());
    }
}
