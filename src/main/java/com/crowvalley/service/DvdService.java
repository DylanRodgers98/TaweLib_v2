package com.crowvalley.service;

import com.crowvalley.dao.IResourceDAO;
import com.crowvalley.model.resource.Dvd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

public class DvdService implements IResourceService<Dvd>{

    private static final Logger LOGGER = LoggerFactory.getLogger(DvdService.class);

    @Resource
    private IResourceDAO DAO;

    public Optional<Dvd> get(Long id) {
        Optional<Dvd> dvd = DAO.get(id);
        if (dvd.isPresent()) {
            LOGGER.info("Dvd with id {} retrieved successfully", id);
            return dvd;
        } else {
            LOGGER.warn("Could not find dvd with id {}", id);
            return Optional.empty();
        }
    }

    public List<Dvd> getAll() {
        List<Dvd> dvds = DAO.getAll();
        if (!dvds.isEmpty()) {
            LOGGER.info("All dvds retrieved successfully");
            return dvds;
        } else {
            LOGGER.warn("No dvds found in database");
            return dvds;
        }
    }

    public void save(Dvd dvd){
        DAO.save(dvd);
        LOGGER.info("Dvd with id {} saved successfully", dvd.getId());
    }

    public void update(Dvd dvd) {
        DAO.update(dvd);
        LOGGER.info("Dvd with id {} updated successfully", dvd.getId());
    }

    public void delete(Dvd dvd) {
        DAO.delete(dvd);
        LOGGER.info("Dvd with id {} saved successfully", dvd.getId());
    }

    public void setDAO(IResourceDAO DAO) {
        this.DAO = DAO;
        LOGGER.info("DvdService DAO set to {}", DAO.getClass());
    }
}
