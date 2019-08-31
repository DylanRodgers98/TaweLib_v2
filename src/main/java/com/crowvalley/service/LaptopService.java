package com.crowvalley.service;

import com.crowvalley.dao.IResourceDAO;
import com.crowvalley.model.resource.Laptop;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

public class LaptopService implements IResourceService<Laptop> {

    private static final Logger LOGGER = LoggerFactory.getLogger(LaptopService.class);

    @Resource
    private IResourceDAO DAO;

    public Optional<Laptop> get(Long id) {
        Optional<Laptop> laptop = DAO.get(id);
        if (laptop.isPresent()) {
            LOGGER.info("Laptop with id {} retrieved successfully", id);
            return laptop;
        } else {
            LOGGER.warn("Could not find laptop with id {}", id);
            return Optional.empty();
        }
    }

    public List<Laptop> getAll() {
        List<Laptop> laptops = DAO.getAll();
        if (!laptops.isEmpty()) {
            LOGGER.info("All laptops retrieved successfully");
            return laptops;
        } else {
            LOGGER.warn("No laptops found in database");
            return laptops;
        }
    }

    public void save(Laptop laptop){
        DAO.save(laptop);
        LOGGER.info("Laptop with id {} saved successfully", laptop.getId());
    }

    public void update(Laptop laptop) {
        DAO.update(laptop);
        LOGGER.info("Laptop with id {} updated successfully", laptop.getId());
    }

    public void delete(Laptop laptop) {
        DAO.delete(laptop);
        LOGGER.info("Laptop with id {} deleted successfully", laptop.getId());
    }

    public void setDAO(IResourceDAO DAO) {
        this.DAO = DAO;
        LOGGER.info("LaptopService DAO set to {}", DAO.getClass());
    }
}
