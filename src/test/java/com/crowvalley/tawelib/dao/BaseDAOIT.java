package com.crowvalley.tawelib.dao;

import com.crowvalley.tawelib.model.resource.Book;
import com.crowvalley.tawelib.model.resource.Dvd;
import com.crowvalley.tawelib.model.resource.Laptop;
import com.crowvalley.tawelib.model.resource.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/spring/applicationContext.xml")
@Transactional
public class BaseDAOIT {

    @Autowired
    private BaseDAO baseDAO;

    @Test
    public void testCRUD() {
        Resource book = new Book();
        baseDAO.saveOrUpdate(book);

        Long bookId = book.getId();
        assertThat(bookId)
                .as("Persisted Book has an assigned ID")
                .isNotNull();

        Optional<Book> optionalBook = baseDAO.getWithId(bookId, Book.class);
        assertThat(optionalBook)
                .as("Book retrieved from database using ID")
                .isPresent();

        Book bookFromDatabase = optionalBook.get();
        assertThat(bookFromDatabase)
                .as("Book entity retrieved from database is equal to Book object persisted to database")
                .isEqualTo(book);

        book.setTitle("Updated Book");
        baseDAO.saveOrUpdate(book);

        optionalBook = baseDAO.getWithId(bookId, Book.class);
        assertThat(optionalBook)
                .as("Book retrieved from database using ID")
                .isPresent();

        bookFromDatabase = optionalBook.get();
        assertThat(bookFromDatabase)
                .as("Book entity retrieved from database is equal to updated Book object persisted to database")
                .isEqualTo(book);

        baseDAO.delete(book);

        optionalBook = baseDAO.getWithId(bookId, Book.class);
        assertThat(optionalBook)
                .as("No Book retrieved from database after being deleted")
                .isEmpty();
    }

    @Test
    public void testGetAll() {
        Resource book = new Book();
        Resource dvd = new Dvd();
        Resource laptop = new Laptop();
        baseDAO.saveOrUpdate(book);
        baseDAO.saveOrUpdate(dvd);
        baseDAO.saveOrUpdate(laptop);

        List<Resource> resources = baseDAO.getAll(Resource.class);

        assertThat(resources)
                .as("List of all Resource entities retrieved from database contains the newly persisted Resource objects")
                .contains(book, dvd, laptop);
    }

}
