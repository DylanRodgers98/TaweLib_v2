package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.dao.CopyDAO;
import com.crowvalley.tawelib.model.resource.*;
import com.crowvalley.tawelib.model.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for retrieving data about {@link Copy} objects
 * persisted in a database, using a Data Access Object (DAO) to perform
 * CRUD operations.
 *
 * @author Dylan Rodgers
 */
public class CopyServiceImpl implements CopyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CopyServiceImpl.class);

    private CopyDAO DAO;

    private LoanService loanService;

    /**
     * Retrieves a {@link Copy} from the DAO using the {@link Copy}'s
     * {@code copyId} and returns it wrapped in an {@link Optional}. If a
     * {@link Copy} with the passed {@code copyId} isn't retrieved from the
     * DAO, an empty {@link Optional} is returned.
     *
     * @param id The ID of the {@link Copy} to be retrieved
     * @return The requested {@link Copy} wrapped in an {@link Optional}
     * if it isn't retrieved by the DAO, or an empty {@link Optional} if not.
     */
    @Override
    public Optional<Copy> get(Long id) {
        return DAO.getWithId(id, Copy.class);
    }

    /**
     * @return A {@link List} of all {@link Copy}s retrieved by the DAO.
     */
    @Override
    public List<Copy> getAll() {
        return DAO.getAll(Copy.class);
    }

    @Override
    public List<Copy> getAllCopiesForResource(Long resourceId) {
        return DAO.getAllCopiesForResource(resourceId);
    }

    @Override
    public List<Copy> getAllCopiesNotOnLoanForResource(Long resourceId) {
        return getAllCopiesForResource(resourceId).stream()
                .filter(copy -> !loanService.isCopyOnLoan(copy.getId()))
                .collect(Collectors.toList());
    }

    /**
     * Persists a {@link Copy} object to the database through the DAO.
     *
     * @param copy The {@link Copy} object to be saved to the database.
     */
    @Override
    public void saveOrUpdate(Copy copy) {
        DAO.saveOrUpdate(copy);
        LOGGER.info("Copy (ID: {}) of {} (ID: {}) saved successfully", copy.getId(), copy.getResourceType(), copy.getResourceId());
    }

    /**
     * Deletes a {@link Copy} object from the database through the DAO.
     *
     * @param copy The {@link Copy} object to be deleted from the database.
     */
    @Override
    public void delete(Copy copy) {
        DAO.delete(copy);
        LOGGER.info("Copy (ID: {}) of {} (ID: {}) deleted successfully", copy.getId(), copy.getResourceType(), copy.getResourceId());
    }

    /**
     * Adds a {@link CopyRequest} within a persisted {@link Copy} object.
     *
     * @param copyId The ID of the {@link Copy} object for which to create a
     *           {@link CopyRequest} for.
     * @param username The {@code username} of the {@link User} for which
     *                 to create a {@link CopyRequest} for.
     */
    @Override
    public void createCopyRequestForPersistedCopy(Long copyId, String username) {
        get(copyId).ifPresent(copy -> createCopyRequestForPersistedCopy(copy, username));
    }

    /**
     * Adds a {@link CopyRequest} within a persisted {@link Copy} object.
     *
     * @param copy The {@link Copy} object for which to create a
     *             {@link CopyRequest} for.
     * @param username The {@code username} of the {@link User} for which
     *                 to create a {@link CopyRequest} for.
     */
    @Override
    public void createCopyRequestForPersistedCopy(Copy copy, String username) {
        copy.createCopyRequest(username);
        saveOrUpdate(copy);
    }

    /**
     * Deletes a {@link CopyRequest} from within a persisted {@link Copy}
     * object.
     *
     * @param copyId The ID of the {@link Copy} object for which to delete a
     *           {@link CopyRequest} from.
     * @param username The {@code username} of the {@link User} for which
     *                 to delete the {@link CopyRequest} for.
     */
    @Override
    public void deleteCopyRequestFromPersistedCopy(Long copyId, String username) {
        get(copyId).ifPresent(copy -> deleteCopyRequestFromPersistedCopy(copy, username));
    }

    /**
     * Deletes a {@link CopyRequest} from within a persisted {@link Copy}
     * object.
     *
     * @param copy The {@link Copy} object for which to delete a
     *             {@link CopyRequest} from.
     * @param username The {@code username} of the {@link User} for which
     *                 to delete the {@link CopyRequest} for.
     */
    @Override
    public void deleteCopyRequestFromPersistedCopy(Copy copy, String username) {
        copy.deleteCopyRequestForUser(username);
        saveOrUpdate(copy);
    }

    @Override
    public void setDAO(CopyDAO DAO) {
        this.DAO = DAO;
        LOGGER.info("{} DAO set to {}", this.getClass().getSimpleName(), DAO.getClass().getSimpleName());
    }

    public void setLoanService(LoanService loanService) {
        this.loanService = loanService;
        LOGGER.info("{} LoanService set to {}", this.getClass().getSimpleName(), loanService.getClass().getSimpleName());
    }

}
