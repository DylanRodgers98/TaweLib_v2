package com.crowvalley.tawelib.dao;

import com.crowvalley.tawelib.model.resource.Resource;
import com.crowvalley.tawelib.model.resource.ResourceDTO;
import com.crowvalley.tawelib.util.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

import java.util.List;

public class ResourceDAOImpl extends BaseDAOImpl implements ResourceDAO {

    private static final ProjectionList RESOURCE_DTO_PROJECTION_LIST = Projections.projectionList()
            .add(Projections.id(), "id")
            .add(Projections.property("resourceType"), "resourceType")
            .add(Projections.property("title"), "title")
            .add(Projections.property("year"), "year");

    @Override
    public List<ResourceDTO> getAllResourceDTOs(Class<? extends Resource> clazz) {
        return ListUtils.castList(ResourceDTO.class,
                sessionFactory.getCurrentSession()
                        .createCriteria(clazz)
                        .setProjection(RESOURCE_DTO_PROJECTION_LIST)
                        .setResultTransformer(Transformers.aliasToBean(ResourceDTO.class))
                        .list());
    }

    @Override
    public List<ResourceDTO> search(String query, Class<? extends Resource> clazz) {
        Disjunction searchDisjunction = Restrictions.disjunction();
        searchDisjunction.add(Restrictions.ilike("title", query));
        if (StringUtils.isNumeric(query)) {
            searchDisjunction.add(Restrictions.eq("year", query));
        }

        return ListUtils.castList(ResourceDTO.class,
                sessionFactory.getCurrentSession()
                        .createCriteria(clazz)
                        .add(searchDisjunction)
                        .setProjection(RESOURCE_DTO_PROJECTION_LIST)
                        .setResultTransformer(Transformers.aliasToBean(ResourceDTO.class))
                        .list());
    }
}
