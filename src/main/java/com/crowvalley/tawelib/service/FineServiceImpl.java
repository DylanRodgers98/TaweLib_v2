package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.dao.FineDAO;
import com.crowvalley.tawelib.model.fine.Fine;
import com.crowvalley.tawelib.model.resource.*;
import com.crowvalley.tawelib.model.user.User;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

/**
 * Service class for retrieving data about {@link Fine} objects
 * persisted in a database, using a Data Access Object (DAO) to perform
 * create, retrieve, and delete operations.
 *
 * @author Dylan Rodgers
 */
public class FineServiceImpl implements FineService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FineServiceImpl.class);

    private FineDAO DAO;

    private LoanService loanService;

    private CopyService copyService;

    /**
     * Retrieves a {@link Fine} from the DAO using the {@link Fine}'s
     * {@code id} and returns it wrapped in an {@link Optional}. If a
     * {@link Fine} with the passed {@code id} isn't retrieved from the
     * DAO, an empty {@link Optional} is returned.
     *
     * @param id The ID of the {@link Fine} to be retrieved
     * @return The requested {@link Fine} wrapped in an {@link Optional}
     * if it isn't retrieved by the DAO, or an empty {@link Optional} if not.
     */
    @Override
    public Optional<Fine> get(Long id) {
        return DAO.get(id);
    }

    /**
     * Retrieves a {@link List} of all {@link Fine}s created, past and present,
     * for a given {@link User} stored in the database.
     *
     * @param username The ID of the {@link User} for which to generate the
     *               list of {@link Fine}s for.
     * @return A {@link List} of all {@link Fine}s stored in the database
     * for a given {@link User}.
     */
    @Override
    public List<Fine> getAllFinesForUser(String username) {
        return DAO.getAllFinesForUser(username);
    }

    @Override
    public Double getTotalFineAmountForUser(String username) {
        return DAO.getTotalFineAmountForUser(username);
    }

    /**
     * @return A {@link List} of all {@link Fine}s retrieved by the DAO.
     */
    @Override
    public List<Fine> getAll() {
        return DAO.getAll();
    }

    @Override
    public Optional<Copy> getCopyFromFine(Fine fine) {
        Optional<Loan> optionalLoan = loanService.get(fine.getLoanId());
        if (optionalLoan.isPresent()) {
            Loan loan = optionalLoan.get();
            return copyService.get(loan.getCopyId());
        }
        return Optional.empty();
    }

    /**
     * Persists a {@link Fine} object to the database through the DAO.
     *
     * @param fine The {@link Fine} object to be saved to the database.
     */
    @Override
    public void save(Fine fine) {
        DAO.save(fine);
        LOGGER.info("Fine with ID {} saved successfully", fine.getId());
    }

    /**
     * Deletes a {@link Fine} object from the database through the DAO.
     *
     * @param fine The {@link Fine} object to be deleted from the database.
     */
    @Override
    public void delete(Fine fine) {
        DAO.delete(fine);
        LOGGER.info("Fine with ID {} deleted successfully", fine.getId());
    }

    @Override
    public void setDAO(FineDAO DAO) {
        this.DAO = DAO;
        LOGGER.info("{} DAO set to {}", this.getClass().getSimpleName(), DAO.getClass().getSimpleName());
    }

    public void setLoanService(LoanService loanService) {
        this.loanService = loanService;
        LOGGER.info("{} LoanService set to {}", this.getClass().getSimpleName(), loanService.getClass().getSimpleName());
    }

    public void setCopyService(CopyService copyService) {
        this.copyService = copyService;
        LOGGER.info("{} CopyService set to {}", this.getClass().getSimpleName(), copyService.getClass().getSimpleName());
    }

}
