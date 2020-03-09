package com.crowvalley.tawelib.dao;

import com.crowvalley.tawelib.model.resource.Copy;
import com.crowvalley.tawelib.model.resource.Resource;
import com.crowvalley.tawelib.model.resource.ResourceDTO;
import com.crowvalley.tawelib.util.ListUtils;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class ResourceDAOImpl extends BaseDAOImpl implements ResourceDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceDAOImpl.class);

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
    public void deleteWithId(Long resourceId) {
        List<Long> copyIds = ListUtils.castList(Long.class,
                sessionFactory.getCurrentSession()
                        .createCriteria(Copy.class)
                        .add(Restrictions.eq("resourceId", resourceId))
                        .setProjection(Projections.id())
                        .list());

        for (Long copyId : copyIds) {
            deleteWithId(copyId, Copy.class);
            LOGGER.info("Copy (ID: {}) of Resource (ID: {}) deleted successfully", copyId, resourceId);
        }
        deleteWithId(resourceId, Resource.class);
    }
}
