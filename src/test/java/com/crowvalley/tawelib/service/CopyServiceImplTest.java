package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.dao.CopyDAO;
import com.crowvalley.tawelib.model.resource.Copy;
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
public class CopyServiceImplTest {

    private static final Long ID = 1L;

    private static final Copy copy1 = new Copy(1L, ResourceType.BOOK, 7);

    private static final Copy copy2 = new Copy(2L, ResourceType.DVD, 3);

    private static final List<Copy> copies = Arrays.asList(copy1, copy2);

    @Mock
    private CopyDAO DAO;

    @InjectMocks
    private CopyServiceImpl copyService;

    @Test
    public void testGet() {
        when(DAO.getWithId(ID, Copy.class)).thenReturn(Optional.of(copy1));

        assertThat(copyService.get(ID).get())
                .as("Retrieve copy from database with ID 1")
                .isEqualTo(copy1);
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
        when(DAO.getAll(Copy.class)).thenReturn(copies);

        assertThat(copyService.getAll())
                .as("Retrieve all copies stored in the database")
                .isEqualTo(copies);
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
        copyService.saveOrUpdate(copy1);
        verify(DAO).saveOrUpdate(copy1);
    }

    @Test
    public void test_verifyDelete() {
        copyService.delete(copy2);
        verify(DAO).delete(copy2);
    }
}
