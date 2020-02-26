package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.dao.BaseDAO;
import com.crowvalley.tawelib.dao.ResourceDAO;
import com.crowvalley.tawelib.model.resource.Book;
import com.crowvalley.tawelib.model.resource.Resource;
import com.crowvalley.tawelib.model.resource.ResourceDTO;
import com.crowvalley.tawelib.model.resource.ResourceType;
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
public class ResourceServiceImplTest {

    private static final Long ID = 1L;

    private static final Book BOOK_1 = new Book("Book 1", "2019", "url", "Dylan Rodgers", "Penguin", "Sci-fi", "isbn", "English");

    private static final List<ResourceDTO> RESOURCES = Arrays.asList(new ResourceDTO());

    @Mock
    private ResourceDAO DAO;

    @InjectMocks
    private ResourceServiceImpl resourceService;

    @Test
    public void testGet() {
        when(DAO.getWithId(ID, Book.class)).thenReturn(Optional.of(BOOK_1));

        assertThat(resourceService.get(ID, ResourceType.BOOK).get())
                .as("Book retrieved from DAO")
                .isEqualTo(BOOK_1);
    }

    @Test
    public void testGet_noBookFromDAO() {
        when(DAO.getWithId(ID, Book.class)).thenReturn(Optional.empty());

        assertThat(resourceService.get(ID, ResourceType.BOOK))
                .as("No resource retrieved from DAO")
                .isEqualTo(Optional.empty());
    }

    @Test
    public void testGetAll() {
        when(DAO.getAllResourceDTOs(Resource.class)).thenReturn(RESOURCES);

        assertThat(resourceService.getAllResourceDTOs())
                .as("All resources retrieved from DAO")
                .isEqualTo(RESOURCES);
    }

    @Test
    public void testGetAll_noBooksFromDAO() {
        when(DAO.getAllResourceDTOs(Resource.class)).thenReturn(new ArrayList<>());

        assertThat(resourceService.getAllResourceDTOs().isEmpty())
                .as("No resources retrieved from DAO")
                .isTrue();
    }

    @Test
    public void test_verifySaveOrUpdate() {
        resourceService.saveOrUpdate(BOOK_1);
        verify(DAO).saveOrUpdate(BOOK_1);
    }

    @Test
    public void test_verifyDelete() {
        resourceService.delete(BOOK_1);
        verify(DAO).delete(BOOK_1);
    }

}
