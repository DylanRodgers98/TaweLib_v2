package com.crowvalley.tawelib.dao;

import com.crowvalley.tawelib.model.resource.Book;
import com.crowvalley.tawelib.model.resource.Copy;
import com.crowvalley.tawelib.model.resource.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/spring/applicationContext.xml")
@Transactional
public class CopyDAOIT {

    @Autowired
    private CopyDAO copyDAO;

    @Test
    public void testGetAllCopiesForResource() {
        Resource book = new Book();
        Copy copy1 = new Copy(book, 1, "");
        Copy copy2 = new Copy(book, 1, "");
        Copy copy3 = new Copy(book, 1, "");
        book.getCopies().add(copy1);
        book.getCopies().add(copy2);
        book.getCopies().add(copy3);
        copyDAO.saveOrUpdate(book);

        Long bookId = book.getId();
        assertThat(bookId)
                .as("Persisted Book has an assigned ID")
                .isNotNull();

        List<Copy> copies = copyDAO.getAllCopiesForResource(bookId);
        assertThat(copies)
                .as("List of all Copies of Resource retrieved from database contains the newly persisted Copy objects")
                .contains(copy1, copy2, copy3);

    }

}
