package com.crowvalley.service;

import com.crowvalley.dao.FineDAO;
import com.crowvalley.model.fine.Fine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

public class FineServiceImpl implements FineService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FineServiceImpl.class);

    @Resource
    private FineDAO DAO;

    public Optional<Fine> get(Long id) {
        Optional<Fine> fine = DAO.get(id);
        if (fine.isPresent()) {
            LOGGER.info("Fine with ID {} retrieved successfully", id);
            return fine;
        } else {
            LOGGER.warn("Could not find fine with username {}", id);
            return Optional.empty();
        }
    }

    public List<Fine> getAll() {
        List<Fine> fines = DAO.getAll();
        if (!fines.isEmpty()) {
            LOGGER.info("All fines retrieved successfully");
            return fines;
        } else {
            LOGGER.warn("No fines found");
            return fines;
        }
    }

    public void save(Fine fine){
        DAO.save(fine);
        LOGGER.info("Fine with username {} saved successfully", fine.getId());
    }

    public void delete(Fine fine) {
        DAO.delete(fine);
        LOGGER.info("Fine with username {} deleted successfully", fine.getId());
    }

    public void setDAO(FineDAO DAO) {
        this.DAO = DAO;
        LOGGER.info("FineServiceImpl DAO set to {}", DAO.getClass());
    }

}
