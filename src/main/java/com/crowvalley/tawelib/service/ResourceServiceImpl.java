package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.dao.ResourceDAO;
import com.crowvalley.tawelib.model.resource.Resource;
import com.crowvalley.tawelib.model.resource.ResourceDTO;
import com.crowvalley.tawelib.model.resource.ResourceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

/**
 * Service class for retrieving data about {@link Resource} objects
 * persisted in a database, using a Data Access Object (DAO) to perform
 * CRUD operations.
 *
 * @author Dylan Rodgers
 */
public class ResourceServiceImpl implements ResourceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceServiceImpl.class);

    private ResourceDAO resourceDAO;

    /**
     * Retrieves a {@link Resource} from the DAO using the {@link Resource}'s
     * {@code id} and returns it wrapped in an {@link Optional}. If a
     * {@link Resource} with the passed {@code id} isn't retrieved from the
     * DAO, an empty {@link Optional} is returned.
     *
     * @param id The ID of the {@link Resource} to be retrieved
     * @return The requested {@link Resource} wrapped in an {@link Optional}
     * if it isn't retrieved by the DAO, or an empty {@link Optional} if not.
     */
    @Override
    public Optional<? extends Resource> get(Long id, ResourceType resourceType) {
        return resourceDAO.getWithId(id, resourceType.getModelClass());
    }

    @Override
    public List<ResourceDTO> getAllResourceDTOs(ResourceType resourceType) {
        return resourceDAO.getAllResourceDTOs(resourceType.getModelClass());
    }

    /**
     * Persists a {@link Resource} object to the database through the DAO.
     *
     * @param resource The {@link Resource} object to be saved to the database.
     */
    @Override
    public void saveOrUpdate(Resource resource) {
        resourceDAO.saveOrUpdate(resource);
        LOGGER.info("Resource \"{}\" with ID {} saved successfully", resource, resource.getId());
    }

    /**
     * Deletes a {@link Resource} object from the database through the DAO.
     *
     * @param resource The {@link Resource} object to be deleted from the database.
     */
    @Override
    public void delete(Resource resource) {
        resourceDAO.delete(resource);
        LOGGER.info("Resource \"{}\" (ID: {}) deleted successfully", resource, resource.getId());
    }

    @Override
    public void deleteWithId(Long id) {
        //Load Resource object into memory to cascase Delete operation onto any Copy objects
        Optional<? extends Resource> resource = get(id, ResourceType.ALL);
        if (resource.isEmpty()) {
            LOGGER.error("Resource with ID {} not found so could not be deleted", id);
            throw new IllegalArgumentException("Resource with ID " + id + " not found so could not be deleted");
        }
        delete(resource.get());
        LOGGER.info("Resource (ID: {}) deleted successfully", id);
    }

    @Override
    public List<ResourceDTO> search(String query, ResourceType resourceType) {
        return resourceDAO.search(query, resourceType.getModelClass());
    }

    @Override
    public void setResourceDAO(ResourceDAO resourceDAO) {
        this.resourceDAO = resourceDAO;
        LOGGER.info("{} ResourceDAO set to {}", this.getClass().getSimpleName(), resourceDAO.getClass().getSimpleName());
    }

}
