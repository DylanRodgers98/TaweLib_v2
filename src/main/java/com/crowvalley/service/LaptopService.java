package com.crowvalley.service;

import com.crowvalley.dao.ResourceDAO;
import com.crowvalley.model.resource.Laptop;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

public class LaptopService {

    @Resource
    private ResourceDAO DAO;

    public Optional<Laptop> get(Long id) {
        return DAO.get(id);
    }

    public List<Laptop> getAll() {
        return DAO.getAll();
    }

    public void save(Laptop laptop){
        DAO.save(laptop);
    }

    public void update(Laptop laptop) {
        DAO.update(laptop);
    }

    public void delete(Laptop laptop) {
        DAO.delete(laptop);
    }

    public void setDAO(ResourceDAO DAO) {
        this.DAO = DAO;
    }
}
