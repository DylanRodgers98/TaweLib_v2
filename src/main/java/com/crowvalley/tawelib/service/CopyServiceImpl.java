package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.dao.CopyDAO;
import com.crowvalley.tawelib.model.resource.*;
import com.crowvalley.tawelib.model.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
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

    private CopyDAO copyDAO;

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
        return copyDAO.getWithId(id, Copy.class);
    }

    @Override
    public List<Copy> getAllCopiesNotOnLoanForResource(Long resourceId) {
        return copyDAO.getAllCopiesForResource(resourceId).stream()
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
        copyDAO.saveOrUpdate(copy);
        LOGGER.info("Copy (ID: {}) of '{}' saved successfully", copy.getId(), copy.getResource());
    }

    /**
     * Deletes a {@link Copy} object from the database through the DAO.
     *
     * @param copy The {@link Copy} object to be deleted from the database.
     */
    @Override
    public void delete(Copy copy) {
        copyDAO.delete(copy);
        LOGGER.info("Copy (ID: {}) of '{}' deleted successfully", copy.getId(), copy.getResource());
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
    public void createCopyRequest(Copy copy, String username) {
        Map<String, CopyRequest> copyRequests = copy.getCopyRequests();
        if (!copyRequests.containsKey(username)) {
            copyRequests.put(username, new CopyRequest(copy, username));
            saveOrUpdate(copy);
            LOGGER.info("Request made for copy (ID: {}) by user {} created", copy.getId(), username);
        }
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
    public void removeCopyRequest(Copy copy, String username) {
        Map<String, CopyRequest> copyRequests = copy.getCopyRequests();
        if (copyRequests.containsKey(username)) {
            copyRequests.remove(username);
            saveOrUpdate(copy);
            LOGGER.info("Request made for copy (ID: {}) by user {} deleted", copy.getId(), username);
        }
    }

    @Override
    public void setRequestStatusForUser(Copy copy, String username, CopyRequest.Status requestStatus) {
        Map<String, CopyRequest> copyRequests = copy.getCopyRequests();
        if (copyRequests.containsKey(username)) {
            CopyRequest copyRequest = copyRequests.get(username);
            copyRequest.setRequestStatus(requestStatus);
            saveOrUpdate(copy);
            LOGGER.info("Request made for copy (ID: {}) by user {} set to status: {}", copy.getId(), username, requestStatus);
        }
    }

    @Override
    public Optional<CopyRequest.Status> getRequestStatusForUser(Copy copy, String username) {
        CopyRequest copyRequest = copy.getCopyRequests().get(username);
        return copyRequest != null
                ? Optional.of(copyRequest.getRequestStatus())
                : Optional.empty();
    }

    @Override
    public void setCopyDAO(CopyDAO copyDAO) {
        this.copyDAO = copyDAO;
        LOGGER.info("{} CopyDAO set to {}", this.getClass().getSimpleName(), copyDAO.getClass().getSimpleName());
    }

    public void setLoanService(LoanService loanService) {
        this.loanService = loanService;
        LOGGER.info("{} LoanService set to {}", this.getClass().getSimpleName(), loanService.getClass().getSimpleName());
    }

}
