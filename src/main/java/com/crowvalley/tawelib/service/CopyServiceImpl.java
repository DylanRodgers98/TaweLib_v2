package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.dao.CopyDAO;
import com.crowvalley.tawelib.model.resource.Copy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

public class CopyServiceImpl implements CopyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CopyServiceImpl.class);

    @Resource
    private CopyDAO DAO;

    @Override
    public Optional<Copy> get(Long id) {
        Optional<Copy> copy = DAO.get(id);
        if (copy.isPresent()) {
            LOGGER.info("Copy with ID {} retrieved successfully", id);
            return copy;
        } else {
            LOGGER.warn("Could not find copy with ID {}", id);
            return Optional.empty();
        }
    }

    @Override
    public List<Copy> getAll() {
        List<Copy> copies = DAO.getAll();
        if (!copies.isEmpty()) {
            LOGGER.info("All copies retrieved successfully");
            return copies;
        } else {
            LOGGER.warn("No copies found");
            return copies;
        }
    }

    @Override
    public void save(Copy copy){
        DAO.save(copy);
        LOGGER.info("Copy (ID: {}) of {} (ID: {}) saved successfully", copy.getId(), copy.getResourceType(), copy.getResourceId());
    }

    @Override
    public void update(Copy copy) {
        DAO.update(copy);
        LOGGER.info("Copy (ID: {}) of {} (ID: {}) updated successfully", copy.getId(), copy.getResourceType(), copy.getResourceId());
    }

    @Override
    public void delete(Copy copy) {
        DAO.delete(copy);
        LOGGER.info("Copy (ID: {}) of {} (ID: {}) deleted successfully", copy.getId(), copy.getResourceType(), copy.getResourceId());
    }

    @Override
    public void setDAO(CopyDAO DAO) {
        this.DAO = DAO;
        LOGGER.info("CopyServiceImpl DAO set to {}", DAO.getClass());
    }

    @Override
    public void createCopyRequestForPersistedCopy(Long id, String username) {
        Optional<Copy> copy = get(id);
        if (copy.isPresent()) {
            createCopyRequestForPersistedCopy(copy.get(), username);
        }
    }

    @Override
    public void createCopyRequestForPersistedCopy(Copy copy, String username) {
        copy.createCopyRequest(username);
        update(copy);
    }

    @Override
    public void deleteCopyRequestFromPersistedCopy(Long id, String username) {
        Optional<Copy> copy = get(id);
        if (copy.isPresent()) {
            deleteCopyRequestFromPersistedCopy(copy.get(), username);
        }
    }

    @Override
    public void deleteCopyRequestFromPersistedCopy(Copy copy, String username) {
        copy.deleteCopyRequestForUser(username);
        update(copy);
    }

}
