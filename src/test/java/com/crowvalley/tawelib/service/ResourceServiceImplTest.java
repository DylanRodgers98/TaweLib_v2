package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.dao.ResourceDAO;
import com.crowvalley.tawelib.model.resource.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ResourceServiceImplTest {

    private static final Long ID = 1L;

    @Mock
    private Book mockBook;

    @Mock
    private Dvd mockDvd;

    @Mock
    private Laptop mockLaptop;

    @Mock
    private ResourceDTO mockResourceDTO;

    @Mock
    private ResourceDAO mockResourceDAO;

    @InjectMocks
    private ResourceServiceImpl resourceService;

    @Test
    public void testGet_Book() {
        when(mockResourceDAO.getWithId(ID, Book.class)).thenReturn(Optional.of(mockBook));

        assertThat(resourceService.get(ID, ResourceType.BOOK))
                .as("Book retrieved from database")
                .isEqualTo(Optional.of(mockBook));
    }

    @Test
    public void testGet_NoBookFromDAO() {
        when(mockResourceDAO.getWithId(ID, Book.class)).thenReturn(Optional.empty());

        assertThat(resourceService.get(ID, ResourceType.BOOK))
                .as("No Book retrieved from DAO")
                .isEqualTo(Optional.empty());
    }

    @Test
    public void testGet_Dvd() {
        when(mockResourceDAO.getWithId(ID, Dvd.class)).thenReturn(Optional.of(mockDvd));

        assertThat(resourceService.get(ID, ResourceType.DVD))
                .as("DVD retrieved from database")
                .isEqualTo(Optional.of(mockDvd));
    }

    @Test
    public void testGet_NoDvdFromDAO() {
        when(mockResourceDAO.getWithId(ID, Dvd.class)).thenReturn(Optional.empty());

        assertThat(resourceService.get(ID, ResourceType.DVD))
                .as("No DVD retrieved from DAO")
                .isEqualTo(Optional.empty());
    }

    @Test
    public void testGet_Laptop() {
        when(mockResourceDAO.getWithId(ID, Laptop.class)).thenReturn(Optional.of(mockLaptop));

        assertThat(resourceService.get(ID, ResourceType.LAPTOP))
                .as("Laptop retrieved from database")
                .isEqualTo(Optional.of(mockLaptop));
    }

    @Test
    public void testGet_NoLaptopFromDAO() {
        when(mockResourceDAO.getWithId(ID, Laptop.class)).thenReturn(Optional.empty());

        assertThat(resourceService.get(ID, ResourceType.LAPTOP))
                .as("No Laptop retrieved from DAO")
                .isEqualTo(Optional.empty());
    }

    @Test
    public void testGetAllResourceDTOs() {
        when(mockResourceDAO.getAllResourceDTOs(Resource.class)).thenReturn(Collections.singletonList(mockResourceDTO));

        assertThat(resourceService.getAllResourceDTOs(ResourceType.ALL))
                .as("All ResourceDTOs retrieved from database")
                .containsExactly(mockResourceDTO);
    }

    @Test
    public void testGetAllResourceDTOs_NothingReturned() {
        when(mockResourceDAO.getAllResourceDTOs(Resource.class)).thenReturn(Collections.emptyList());

        assertThat(resourceService.getAllResourceDTOs(ResourceType.ALL))
                .as("No ResourceDTOs retrieved from database")
                .isEmpty();
    }

    @Test
    public void testSaveOrUpdate() {
        resourceService.saveOrUpdate(mockBook);
        verify(mockResourceDAO).saveOrUpdate(mockBook);
    }

    @Test
    public void testDelete() {
        resourceService.delete(mockBook);
        verify(mockResourceDAO).delete(mockBook);
    }

    @Test
    public void testDeleteWithId() {
        when(mockResourceDAO.getWithId(ID, Resource.class)).thenReturn(Optional.of(mockBook));

        resourceService.deleteWithId(ID);

        verify(mockResourceDAO).delete(mockBook);
    }

    @Test
    public void testDeleteWithId_ResourceNotFound() {
        when(mockResourceDAO.getWithId(ID, Resource.class)).thenReturn(Optional.empty());

        assertThatCode(() -> resourceService.deleteWithId(ID))
                .as("Exception throw because resource with given ID not found in database")
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testSearch() {
        when(mockResourceDAO.search(anyString(), eq(Resource.class)))
                .thenReturn(Collections.singletonList(mockResourceDTO));

        assertThat(resourceService.search("TEST_SEARCH", ResourceType.ALL))
                .as("ResourceDTOs found with search query")
                .containsExactly(mockResourceDTO);
    }

}
