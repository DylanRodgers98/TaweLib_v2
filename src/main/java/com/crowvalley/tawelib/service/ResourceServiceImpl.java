package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.dao.BaseDAO;
import com.crowvalley.tawelib.model.resource.Resource;
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

    private BaseDAO DAO;

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
    public Optional<? extends Resource> get(Long id, Class<? extends Resource> clazz) {
        return DAO.getWithId(id, clazz);
    }

    /**
     * @return A {@link List} of all {@link Resource}s retrieved by the DAO.
     */
    @Override
    public List<? extends Resource> getAll() {
        return DAO.getAll(Resource.class);
    }

    /**
     * Persists a {@link Resource} object to the database through the DAO.
     *
     * @param resource The {@link Resource} object to be saved to the database.
     */
    @Override
    public void saveOrUpdate(Resource resource) {
        DAO.saveOrUpdate(resource);
        LOGGER.info("Resource \"{}\" with ID {} saved successfully", resource, resource.getId());
    }

    /**
     * Deletes a {@link Resource} object from the database through the DAO.
     *
     * @param resource The {@link Resource} object to be deleted from the database.
     */
    @Override
    public void delete(Resource resource) {
        DAO.delete(resource);
        LOGGER.info("Resource \"{}\" with ID {} deleted successfully", resource, resource.getId());
    }

    @Override
    public void setDAO(BaseDAO DAO) {
        this.DAO = DAO;
        LOGGER.info("{} DAO set to {}", this.getClass().getSimpleName(), DAO.getClass().getSimpleName());
    }

}
