package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.dao.ResourceDAO;
import com.crowvalley.tawelib.model.resource.Laptop;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;
import java.util.Optional;

public class LaptopServiceImpl implements ResourceService<Laptop> {

    private static final Logger LOGGER = LoggerFactory.getLogger(LaptopServiceImpl.class);

    @Qualifier("laptopDAO")
    @Autowired
    private ResourceDAO DAO;

    @Override
    public Optional<Laptop> get(Long id) {
        Optional<Laptop> laptop = DAO.get(id);
        if (laptop.isPresent()) {
            LOGGER.info("Laptop with ID {} retrieved successfully", id);
            return laptop;
        } else {
            LOGGER.warn("Could not find laptop with ID {}", id);
            return Optional.empty();
        }
    }

    @Override
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

    @Override
    public void save(Laptop laptop) {
        DAO.save(laptop);
        LOGGER.info("Laptop with ID {} saved successfully", laptop.getId());
    }

    @Override
    public void update(Laptop laptop) {
        DAO.update(laptop);
        LOGGER.info("Laptop with ID {} updated successfully", laptop.getId());
    }

    @Override
    public void delete(Laptop laptop) {
        DAO.delete(laptop);
        LOGGER.info("Laptop with ID {} deleted successfully", laptop.getId());
    }

    @Override
    public void setDAO(ResourceDAO DAO) {
        this.DAO = DAO;
        LOGGER.info("LaptopServiceImpl DAO set to {}", DAO.getClass());
    }
}
