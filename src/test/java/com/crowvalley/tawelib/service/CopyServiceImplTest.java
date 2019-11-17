package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.dao.CopyDAOImpl;
import com.crowvalley.tawelib.model.resource.Copy;
import com.crowvalley.tawelib.model.resource.ResourceType;
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
public class CopyServiceImplTest {

    @Mock
    private CopyDAOImpl DAO;

    @InjectMocks
    private CopyServiceImpl copyService;

    private Copy copy1;

    private Copy copy2;

    private Copy copy3;

    private Optional<Copy> optionalCopy1;

    private Optional<Copy> optionalCopy2;

    private Optional<Copy> optionalCopy3;

    private List<Copy> copies;

    @Before
    public void setup() {
        copy1 = new Copy(1L, ResourceType.BOOK, 7);
        copy2 = new Copy(2L, ResourceType.DVD, 3);
        copy3 = new Copy(3L, ResourceType.LAPTOP, 14);
        optionalCopy1 = Optional.of(copy1);
        optionalCopy2 = Optional.of(copy2);
        optionalCopy3 = Optional.of(copy3);
        copies = new ArrayList<>();
        copies.add(copy1);
        copies.add(copy2);
        copies.add(copy3);
    }

    @Test
    public void testGet() {
        Long id = 1L;

        when(DAO.get(id)).thenReturn(optionalCopy1);

        assertThat(copyService.get(id))
                .as("Retrieve copy from database with ID 1")
                .isEqualTo(optionalCopy1);
    }

    @Test
    public void testGet_noCopyFromDAO() {
        Long id = 4L;

        when(DAO.get(id)).thenReturn(Optional.empty());

        assertThat(copyService.get(id))
                .as("Retrieve empty Optional from DAO")
                .isEqualTo(Optional.empty());
    }

    @Test
    public void testGetAll() {
        when(DAO.getAll()).thenReturn(copies);

        assertThat(copyService.getAll())
                .as("Retrieve all copies stored in the database")
                .isEqualTo(copies);
    }

    @Test
    public void testGetAll_noCopiesFromDAO() {
        when(DAO.getAll()).thenReturn(new ArrayList<>());

        assertThat(copyService.getAll().isEmpty())
                .as("Retrieve no copies from DAO")
                .isTrue();
    }

    @Test
    public void test_verifySave() {
        copyService.save(copy1);
        verify(DAO).save(eq(copy1));
    }

    @Test
    public void test_verifyUpdate() {
        copyService.update(copy2);
        verify(DAO).update(eq(copy2));
    }

    @Test
    public void test_verifyDelete() {
        copyService.delete(copy3);
        verify(DAO).delete(eq(copy3));
    }
}
