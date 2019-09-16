package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.dao.ResourceDAO;
import com.crowvalley.tawelib.model.resource.Dvd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class DvdServiceImpl implements ResourceService<Dvd> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DvdServiceImpl.class);

    @Autowired
    private ResourceDAO dvdDAO;

    @Override
    public Optional<Dvd> get(Long id) {
        Optional<Dvd> dvd = dvdDAO.get(id);
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
        List<Dvd> dvds = dvdDAO.getAll();
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
        dvdDAO.save(dvd);
        LOGGER.info("DVD with ID {} saved successfully", dvd.getId());
    }

    @Override
    public void update(Dvd dvd) {
        dvdDAO.update(dvd);
        LOGGER.info("DVD with ID {} updated successfully", dvd.getId());
    }

    @Override
    public void delete(Dvd dvd) {
        dvdDAO.delete(dvd);
        LOGGER.info("DVD with ID {} saved successfully", dvd.getId());
    }

    @Override
    public void setLaptopDAO(ResourceDAO laptopDAO) {
        this.dvdDAO = laptopDAO;
        LOGGER.info("DvdServiceImpl DAO set to {}", laptopDAO.getClass());
    }
}
