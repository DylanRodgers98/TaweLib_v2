package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.dao.CopyDAO;
import com.crowvalley.tawelib.model.resource.Copy;

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

    List<Copy> getAllCopiesForResource(Long resourceId);

    List<Copy> getAllCopiesNotOnLoanForResource(Long resourceId);

    void createCopyRequestForPersistedCopy(Long copyId, String username);

    void createCopyRequestForPersistedCopy(Copy copy, String username);

    void deleteCopyRequestFromPersistedCopy(Long copyId, String username);

    void deleteCopyRequestFromPersistedCopy(Copy copy, String username);

    void setDAO(CopyDAO DAO);

}
