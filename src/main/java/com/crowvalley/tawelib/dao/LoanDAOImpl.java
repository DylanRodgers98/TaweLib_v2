package com.crowvalley.tawelib.dao;

import com.crowvalley.tawelib.model.resource.Loan;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class LoanDAOImpl implements LoanDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Optional<Loan> get(Long loanId) {
        Loan loan = sessionFactory.getCurrentSession().get(Loan.class, loanId);
        return Optional.ofNullable(loan);
    }

    @Override
    public List<Loan> getAllLoansForCopy(Long copyId) {
        return sessionFactory.getCurrentSession()
                .createCriteria(Loan.class)
                .add(Restrictions.eq("copyId", copyId))
                .list();
    }

    @Override
    public List<Loan> getAllLoansForUser(String username) {
        return sessionFactory.getCurrentSession()
                .createCriteria(Loan.class)
                .add(Restrictions.eq("borrowerUsername", username))
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Loan> getAll() {
        return sessionFactory.getCurrentSession().createQuery("from Loan").list();
    }

    @Override
    public void save(Loan loan) {
        sessionFactory.getCurrentSession().save(loan);
    }

    @Override
    public void update(Loan loan) {
        sessionFactory.getCurrentSession().update(loan);
    }

    @Override
    public void delete(Loan loan) {
        sessionFactory.getCurrentSession().delete(loan);
    }
}
