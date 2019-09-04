package com.crowvalley.service;

import com.crowvalley.model.resource.Book;
import org.assertj.core.api.SoftAssertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/applicationContext.xml "})
public class BookServiceImplIT {

    @Autowired
    private ResourceService bookService;

    @Test
    @Transactional
    public void testCRUDOperationsOnBook() {
        SoftAssertions softly = new SoftAssertions();
        Book book = new Book("Book 1", "2019", "URL", "Dylan Rodgers",
                "Penguin", "Sci-fi", "ISBN", "English");

        //Test Create and Retrieve operations
        bookService.save(book);
        List<Book> books = bookService.getAll();
        Long id = books.get(0).getId();

        softly.assertThat(bookService.get(id).get())
                .as("Retrieve book from database")
                .isEqualTo(book);

        //Test Update operation
        Book bookToUpdate = (Book) bookService.get(id).get();
        bookToUpdate.setIsbn("12345678");
        bookService.update(bookToUpdate);

        softly.assertThat(bookService.get(id).get())
                .as("Retrieve book from database with updated ISBN")
                .isEqualTo(book);

        //Test Delete operation
        bookService.delete(book);

        softly.assertThat(bookService.get(id))
                .as("Book deleted")
                .isEqualTo(Optional.empty());
    }
}
