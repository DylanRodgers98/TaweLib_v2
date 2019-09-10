package com.crowvalley.tawelib.dao;

import com.crowvalley.tawelib.model.Loan;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class LoanDAOImpl implements LoanDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Optional<Loan> get(Long id) {
        Loan loan = sessionFactory.getCurrentSession().get(Loan.class, id);
        if (loan != null) {
            return Optional.of(loan);
        } else {
            return Optional.empty();
        }
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
