package com.crowvalley.dao;

import com.crowvalley.model.Loan;
import org.hibernate.SessionFactory;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

public class LoanDAOImpl implements LoanDAO {

    @Resource
    private SessionFactory sessionFactory;

    @Override
    public Optional<Loan> get(Long id) {
        return Optional.of(sessionFactory.getCurrentSession().get(Loan.class, id));
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
