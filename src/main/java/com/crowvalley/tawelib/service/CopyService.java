package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.dao.CopyDAO;
import com.crowvalley.tawelib.model.resource.Copy;
import com.crowvalley.tawelib.model.resource.CopyRequest;

import java.util.List;
import java.util.Optional;

/**
 * Interface for service classes to implement that deal with
 * {@link Copy} objects persisted in a database.
 *
 * @author Dylan Rodgers
 */
public interface CopyService {

    Optional<Copy> get(Long id);

    List<Copy> getAll();

    void saveOrUpdate(Copy copy);

    void delete(Copy copy);

    List<Copy> getAllCopiesNotOnLoanForResource(Long resourceId);

    void createCopyRequest(Copy copy, String username);

    void removeCopyRequest(Copy copy, String username);

    void setRequestStatusForUser(Copy copy, String username, CopyRequest.Status requestStatus);

    Optional<CopyRequest.Status> getRequestStatusForUser(Copy copy, String username);

    void setDAO(CopyDAO DAO);

}
