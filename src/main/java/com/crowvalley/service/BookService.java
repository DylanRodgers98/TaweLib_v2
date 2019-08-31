package com.crowvalley.service;

import com.crowvalley.dao.IResourceDAO;
import com.crowvalley.model.resource.Book;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

public class BookService implements IResourceService<Book> {

    @Resource
    private IResourceDAO DAO;

    public Optional<Book> get(Long id) {
        return DAO.get(id);
    }

    public List<Book> getAll() {
        return DAO.getAll();
    }

    public void save(Book book){
        DAO.save(book);
    }

    public void update(Book book) {
        DAO.update(book);
    }

    public void delete(Book book) {
        DAO.delete(book);
    }

    public void setDAO(IResourceDAO DAO) {
        this.DAO = DAO;
    }
}
