package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.dao.FineDAOImpl;
import com.crowvalley.tawelib.model.fine.Fine;
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
public class FineServiceImplTest {

    @Mock
    private FineDAOImpl DAO;

    @InjectMocks
    private FineServiceImpl fineService;

    private Fine fine1;

    private Fine fine2;

    private Fine fine3;

    private Optional<Fine> optionalFine1;

    private Optional<Fine> optionalFine2;

    private Optional<Fine> optionalFine3;

    private List<Fine> fines;

    @Before
    public void setup() {
        fine1 = new Fine("User 1", Long.valueOf(1), 2.50);
        fine2 = new Fine("User 2", Long.valueOf(2), 0.90);
        fine3 = new Fine("User 3", Long.valueOf(3), 10.00);
        optionalFine1 = Optional.of(fine1);
        optionalFine2 = Optional.of(fine2);
        optionalFine3 = Optional.of(fine3);
        fines = new ArrayList<>();
        fines.add(fine1);
        fines.add(fine2);
        fines.add(fine3);
    }

    @Test
    public void testGet() {
        Long id = Long.valueOf(1);

        when(DAO.get(id)).thenReturn(optionalFine1);

        assertThat(fineService.get(id)).as("Retrieve fine from database with ID 1").isEqualTo(optionalFine1);
    }

    @Test
    public void testGet_noFineFromDAO() {
        Long id = Long.valueOf(4);

        when(DAO.get(id)).thenReturn(Optional.empty());

        assertThat(fineService.get(id)).as("Retrieve empty Optional from DAO").isEqualTo(Optional.empty());
    }

    @Test
    public void testGetAll() {
        when(DAO.getAll()).thenReturn(fines);

        assertThat(fineService.getAll()).as("Retrieve all fines stored in the database").isEqualTo(fines);
    }

    @Test
    public void testGetAll_noFinesFromDAO() {
        when(DAO.getAll()).thenReturn(new ArrayList<>());

        assertThat(fineService.getAll().isEmpty()).as("Retrieve no fines from DAO").isTrue();
    }

    @Test
    public void test_verifySave() {
        fineService.save(fine1);
        verify(DAO).save(eq(fine1));
    }

    @Test
    public void test_verifyDelete() {
        fineService.delete(fine3);
        verify(DAO).delete(eq(fine3));
    }
}
