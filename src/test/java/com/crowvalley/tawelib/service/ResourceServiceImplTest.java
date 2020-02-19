package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.dao.BaseDAO;
import com.crowvalley.tawelib.model.resource.Book;
import com.crowvalley.tawelib.model.resource.Resource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceImplTest {

    @Mock
    private BaseDAO DAO;

    @InjectMocks
    private ResourceServiceImpl resourceService;

    private Book book1;

    private Book book2;

    private Book book3;

    private Optional<Book> optionalBook1;

    private Optional<Book> optionalBook2;

    private Optional<Book> optionalBook3;

    private List<Resource> books;

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
        Long id = 1L;

        when(DAO.getWithId(id, Book.class)).thenReturn(optionalBook1);

        assertThat(resourceService.get(id))
                .as("Retrieve book from database with ID of 1")
                .isEqualTo(optionalBook1);
    }

    @Test
    public void testGet_noBookFromDAO() {
        Long id = 2L;

        when(DAO.getWithId(id, Resource.class)).thenReturn(Optional.empty());

        assertThat(resourceService.get(id))
                .as("Retrieve empty Optional from DAO")
                .isEqualTo(Optional.empty());
    }

    @Test
    public void testGetAll() {
        when(DAO.getAll(Resource.class)).thenReturn(books);

        assertThat(resourceService.getAll())
                .as("Retrieve all books stored in the database")
                .isEqualTo(books);
    }

    @Test
    public void testGetAll_noBooksFromDAO() {
        when(DAO.getAll(Resource.class)).thenReturn(new ArrayList<>());

        assertThat(resourceService.getAll().isEmpty())
                .as("Retrieve no books from DAO")
                .isTrue();
    }

    @Test
    public void test_verifySaveOrUpdate() {
        resourceService.saveOrUpdate(book1);
        verify(DAO).saveOrUpdate(book1);
    }

    @Test
    public void test_verifyDelete() {
        resourceService.delete(book3);
        verify(DAO).delete(book3);
    }
}
