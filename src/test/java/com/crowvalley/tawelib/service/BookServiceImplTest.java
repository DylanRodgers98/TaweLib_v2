package com.crowvalley.tawelib.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.crowvalley.tawelib.dao.BookDAOImpl;
import com.crowvalley.tawelib.model.resource.Book;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceImplTest {

    @Mock
    private BookDAOImpl DAO;

    @InjectMocks
    private BookServiceImpl bookService;

    private Book book1;

    private Book book2;

    private Book book3;

    private Optional<Book> optionalBook1;

    private Optional<Book> optionalBook2;

    private Optional<Book> optionalBook3;

    private List<Book> books;

    @Before
    public void setup() {
        book1 = new Book("Book 1", "2019", "url", "Dylan Rodgers", "Penguin", "Sci-fi", "isbn", "English");
        book2 = new Book("Book 2", "2018", "url", "Lucy Rodgers", "Harper Collins", "Crime", "isbn", "Cymraeg");
        book3 = new Book("Book 3", "2017", "url", "Eleanor Maltby", "Allen & Unwin", "Fantasy", "isbn", "Francais");
        optionalBook1 = Optional.of(book1);
        optionalBook2 = Optional.of(book2);
        optionalBook3 = Optional.of(book3);
        books = new ArrayList<>();
        books.add(book1);
        books.add(book2);
        books.add(book3);
    }

    @Test
    public void testGet() {
        Long id = Long.valueOf(1);

        when(DAO.get(id)).thenReturn(optionalBook1);

        assertThat(bookService.get(id))
                .as("Retrieve book from database with ID of 1")
                .isEqualTo(optionalBook1);
    }

    @Test
    public void testGet_noBookFromDAO() {
        Long id = Long.valueOf(2);

        when(DAO.get(id)).thenReturn(Optional.empty());

        assertThat(bookService.get(id))
                .as("Retrieve empty Optional from DAO")
                .isEqualTo(Optional.empty());
    }

    @Test
    public void testGetAll() {
        when(DAO.getAll()).thenReturn(books);

        assertThat(bookService.getAll())
                .as("Retrieve all books stored in the database")
                .isEqualTo(books);
    }

    @Test
    public void testGetAll_noBooksFromDAO() {
        when(DAO.getAll()).thenReturn(new ArrayList<>());

        assertThat(bookService.getAll().isEmpty())
                .as("Retrieve no books from DAO")
                .isTrue();
    }

    @Test
    public void test_verifySave() {
        bookService.save(book1);
        verify(DAO).save(eq(book1));
    }

    @Test
    public void test_verifyUpdate() {
        bookService.update(book2);
        verify(DAO).update(eq(book2));
    }

    @Test
    public void test_verifyDelete() {
        bookService.delete(book3);
        verify(DAO).delete(eq(book3));
    }
}
