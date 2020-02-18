package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.dao.CopyDAO;
import com.crowvalley.tawelib.model.resource.Copy;
import com.crowvalley.tawelib.model.resource.Resource;

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

    List<Copy> getAllCopiesForResource(Long resourceId);

    List<Copy> getAllCopiesNotOnLoanForResource(Long resourceId);

    void save(Copy copy);

    void update(Copy copy);

    void delete(Copy copy);

    void createCopyRequestForPersistedCopy(Long id, String username);

    void createCopyRequestForPersistedCopy(Copy copy, String username);

    void deleteCopyRequestFromPersistedCopy(Long id, String username);

    void deleteCopyRequestFromPersistedCopy(Copy copy, String username);

    Optional<? extends Resource> getResourceFromCopy(Copy copy);

    void setDAO(CopyDAO DAO);

}
