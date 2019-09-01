package com.crowvalley.service;

import com.crowvalley.dao.ResourceDAO;
import com.crowvalley.model.resource.Laptop;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

public class LaptopServiceImpl implements ResourceService<Laptop> {

    private static final Logger LOGGER = LoggerFactory.getLogger(LaptopServiceImpl.class);

    @Resource
    private ResourceDAO DAO;

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
            LOGGER.warn("No laptops found");
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

    public void setDAO(ResourceDAO DAO) {
        this.DAO = DAO;
        LOGGER.info("LaptopServiceImpl DAO set to {}", DAO.getClass());
    }
}
