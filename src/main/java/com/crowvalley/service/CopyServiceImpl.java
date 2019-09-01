package com.crowvalley.service;

import com.crowvalley.dao.CopyDAO;
import com.crowvalley.dao.FineDAO;
import com.crowvalley.model.fine.Fine;
import com.crowvalley.model.resource.Copy;
import com.crowvalley.model.resource.CopyRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

public class CopyServiceImpl implements CopyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CopyServiceImpl.class);

    @Resource
    private CopyDAO DAO;

    public Optional<Copy> get(Long id) {
        Optional<Copy> copy = DAO.get(id);
        if (copy.isPresent()) {
            LOGGER.info("Copy with ID {} retrieved successfully", id);
            return copy;
        } else {
            LOGGER.warn("Could not find copy with username {}", id);
            return Optional.empty();
        }
    }

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

    public void save(Copy copy){
        DAO.save(copy);
        LOGGER.info("Copy with username {} saved successfully", copy.getId());
    }

    public void update(Copy copy) {
        DAO.update(copy);
        LOGGER.info("Copy with username {} updated successfully", copy.getId());
    }

    public void delete(Copy copy) {
        DAO.delete(copy);
        LOGGER.info("Copy with username {} deleted successfully", copy.getId());
    }

    public void setDAO(CopyDAO DAO) {
        this.DAO = DAO;
        LOGGER.info("CopyServiceImpl DAO set to {}", DAO.getClass());
    }

}
