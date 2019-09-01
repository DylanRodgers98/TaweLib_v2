package com.crowvalley.dao;

import com.crowvalley.model.fine.Payment;
import org.hibernate.SessionFactory;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

public class PaymentDAOImpl implements PaymentDAO {

    @Resource
    private SessionFactory sessionFactory;

    @Override
    public Optional<Payment> get(Long id) {
        return Optional.of(sessionFactory.getCurrentSession().get(Payment.class, id));
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Payment> getAll() {
        return sessionFactory.getCurrentSession().createQuery("from Payment").list();
    }

    @Override
    public void save(Payment payment) {
        sessionFactory.getCurrentSession().save(payment);
    }

    @Override
    public void update(Payment payment) {
        sessionFactory.getCurrentSession().update(payment);
    }

    @Override
    public void delete(Payment payment) {
        sessionFactory.getCurrentSession().delete(payment);
    }
}
