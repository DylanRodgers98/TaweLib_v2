package com.crowvalley.service;

import com.crowvalley.dao.IResourceDAO;
import com.crowvalley.model.resource.Dvd;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

public class DvdService implements IResourceService<Dvd>{

    @Resource
    private IResourceDAO DAO;

    public Optional<Dvd> get(Long id) {
        return DAO.get(id);
    }

    public List<Dvd> getAll() {
        return DAO.getAll();
    }

    public void save(Dvd dvd){
        DAO.save(dvd);
    }

    public void update(Dvd dvd) {
        DAO.update(dvd);
    }

    public void delete(Dvd dvd) {
        DAO.delete(dvd);
    }

    public void setDAO(IResourceDAO DAO) {
        this.DAO = DAO;
    }
}
