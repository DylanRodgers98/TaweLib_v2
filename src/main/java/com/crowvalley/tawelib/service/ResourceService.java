package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.dao.ResourceDAO;
import com.crowvalley.tawelib.model.resource.Copy;
import com.crowvalley.tawelib.model.resource.Resource;
import com.crowvalley.tawelib.model.resource.ResourceDTO;
import com.crowvalley.tawelib.model.resource.ResourceType;

import java.util.List;
import java.util.Optional;

/**
 * Interface for service classes to implement that deal with objects
 * of classes that extend {@link Resource} persisted in a database.
 *
 * @author Dylan Rodgers
 */
public interface ResourceService {

    Optional<? extends Resource> get(Long id, ResourceType resourceType);

    List<ResourceDTO> getAllResourceDTOs();

    List<ResourceDTO> getAllResourceDTOs(ResourceType resourceType);

    Optional<ResourceDTO> getResourceDTOFromCopy(Copy copy);

    void saveOrUpdate(Resource resource);

    void delete(Resource resource);

    void deleteWithId(Long id);

    void setDAO(ResourceDAO DAO);

}
