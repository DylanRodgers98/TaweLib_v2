package com.crowvalley.service;

import com.crowvalley.dao.ResourceDAO;
import com.crowvalley.model.resource.Dvd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

public class DvdServiceImpl implements ResourceService<Dvd> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DvdServiceImpl.class);

    @Resource
    private ResourceDAO DAO;

    @Override
    public Optional<Dvd> get(Long id) {
        Optional<Dvd> dvd = DAO.get(id);
        if (dvd.isPresent()) {
            LOGGER.info("DVD with ID {} retrieved successfully", id);
            return dvd;
        } else {
            LOGGER.warn("Could not find DVD with ID {}", id);
            return Optional.empty();
        }
    }

    @Override
    public List<Dvd> getAll() {
        List<Dvd> dvds = DAO.getAll();
        if (!dvds.isEmpty()) {
            LOGGER.info("All DVDs retrieved successfully");
            return dvds;
        } else {
            LOGGER.warn("No DVDs found");
            return dvds;
        }
    }

    @Override
    public void save(Dvd dvd){
        DAO.save(dvd);
        LOGGER.info("DVD with ID {} saved successfully", dvd.getId());
    }

    @Override
    public void update(Dvd dvd) {
        DAO.update(dvd);
        LOGGER.info("DVD with ID {} updated successfully", dvd.getId());
    }

    @Override
    public void delete(Dvd dvd) {
        DAO.delete(dvd);
        LOGGER.info("DVD with ID {} saved successfully", dvd.getId());
    }

    @Override
    public void setDAO(ResourceDAO DAO) {
        this.DAO = DAO;
        LOGGER.info("DvdServiceImpl DAO set to {}", DAO.getClass());
    }
}
