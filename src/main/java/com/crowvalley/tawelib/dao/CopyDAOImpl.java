package com.crowvalley.tawelib.dao;

import com.crowvalley.tawelib.model.resource.Copy;
import com.crowvalley.tawelib.util.ListUtils;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Data Access Object for retrieving data about {@link Copy} objects
 * persisted in a database. This class provides methods to perform CRUD
 * operations on a database table that stores information about {@link Copy}
 * objects, using a Hibernate {@link SessionFactory} object to access
 * the database.
 *
 * @author Dylan Rodgers
 */
public class CopyDAOImpl extends BaseDAOImpl implements CopyDAO {

    @Override
    public List<Copy> getAllCopiesForResource(Long resourceId) {
        return ListUtils.castList(Copy.class,
                sessionFactory.getCurrentSession()
                        .createQuery("select c from Resource r join r.copies c where r.id = :rId")
                        .setParameter("rId", resourceId)
                        .list());
    }

}