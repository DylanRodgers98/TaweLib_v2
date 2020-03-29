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
import java.util.Optional;

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
    public Optional<ResourceDTO> getResourceDTO(Long id, Class<? extends Resource> clazz) {
        ResourceDTO resourceDTO = (ResourceDTO) sessionFactory.getCurrentSession()
                .createCriteria(clazz)
                .add(Restrictions.eq("id", id))
                .setProjection(RESOURCE_DTO_PROJECTION_LIST)
                .setResultTransformer(Transformers.aliasToBean(ResourceDTO.class))
                .uniqueResult();
        return Optional.ofNullable(resourceDTO);
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
