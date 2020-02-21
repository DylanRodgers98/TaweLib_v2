package com.crowvalley.tawelib.dao;

import com.crowvalley.tawelib.model.resource.Resource;
import com.crowvalley.tawelib.model.resource.ResourceDTO;
import com.crowvalley.tawelib.util.DatabaseUtils;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

import java.util.List;
import java.util.Optional;

public class ResourceDAOImpl extends BaseDAOImpl implements ResourceDAO {

    @Override
    public List<ResourceDTO> getAllResourceDTOs(Class<? extends Resource> clazz) {
        return DatabaseUtils.castList(sessionFactory.getCurrentSession()
                .createCriteria(clazz)
                .setProjection(Projections.projectionList()
                        .add(Projections.id(), "id")
                        .add(Projections.property("resourceType"), "resourceType")
                        .add(Projections.property("title"), "title")
                        .add(Projections.property("year"), "year"))
                .setResultTransformer(Transformers.aliasToBean(ResourceDTO.class))
                .list(), ResourceDTO.class);
    }

    @Override
    public Optional<String> getResourceTitle(Long id, Class<? extends Resource> clazz) {
        String title = (String) sessionFactory.getCurrentSession()
                .createCriteria(clazz)
                .add(Restrictions.eq("id", id))
                .setProjection(Projections.property("title"))
                .uniqueResult();
        return Optional.ofNullable(title);
    }

    @Override
    public void deleteWithId(Long id) {
        sessionFactory.getCurrentSession()
                .createQuery("delete " + Resource.class.getName() + " where id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

}
