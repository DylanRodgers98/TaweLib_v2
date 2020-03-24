package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.dao.CopyDAO;
import com.crowvalley.tawelib.model.resource.Book;
import com.crowvalley.tawelib.model.resource.Copy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CopyServiceImplTest {

    private static final Long ID = 1L;

    private static final Book BOOK = new Book("Book 1", "2019", "url", "Dylan Rodgers", "Penguin", "Sci-fi", "isbn", "English");

    private static final Copy COPY_1 = new Copy(BOOK, 7, "Location 1");

    private static final Copy COPY_2 = new Copy(BOOK, 3, "Location 2");

    private static final List<Copy> COPIES = Arrays.asList(COPY_1, COPY_2);

    @Mock
    private CopyDAO DAO;

    @InjectMocks
    private CopyServiceImpl copyService;

    @Test
    public void testGet() {
        when(DAO.getWithId(ID, Copy.class)).thenReturn(Optional.of(COPY_1));

        assertThat(copyService.get(ID).get())
                .as("Retrieve copy from database with ID 1")
                .isEqualTo(COPY_1);
    }

    @Test
    public void testGet_noCopyFromDAO() {
        when(DAO.getWithId(ID, Copy.class)).thenReturn(Optional.empty());

        assertThat(copyService.get(ID))
                .as("Retrieve empty Optional from DAO")
                .isEqualTo(Optional.empty());
    }

    @Test
    public void testGetAll() {
        when(DAO.getAll(Copy.class)).thenReturn(COPIES);

        assertThat(copyService.getAll())
                .as("Retrieve all copies stored in the database")
                .isEqualTo(COPIES);
    }

    @Test
    public void testGetAll_noCopiesFromDAO() {
        when(DAO.getAll(Copy.class)).thenReturn(new ArrayList<>());

        assertThat(copyService.getAll().isEmpty())
                .as("Retrieve no copies from DAO")
                .isTrue();
    }

    @Test
    public void test_verifySave() {
        copyService.saveOrUpdate(COPY_1);
        verify(DAO).saveOrUpdate(COPY_1);
    }

    @Test
    public void test_verifyDelete() {
        copyService.delete(COPY_2);
        verify(DAO).delete(COPY_2);
    }
}
