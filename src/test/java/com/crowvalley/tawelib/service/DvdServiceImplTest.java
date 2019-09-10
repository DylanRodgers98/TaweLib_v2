package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.dao.DvdDAOImpl;
import com.crowvalley.tawelib.model.resource.Dvd;
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
public class DvdServiceImplTest {

    @Mock
    private DvdDAOImpl DAO;

    @InjectMocks
    private DvdServiceImpl dvdService;

    private Dvd dvd1;

    private Dvd dvd2;

    private Dvd dvd3;

    private Optional<Dvd> optionalDvd1;

    private Optional<Dvd> optionalDvd2;

    private Optional<Dvd> optionalDvd3;

    private List<Dvd> dvds;

    @Before
    public void setup() {
        dvd1 = new Dvd("Dvd 1", "2019", "url", "Dylan Rodgers", "English", 120, "English");
        dvd2 = new Dvd("Dvd 2", "2018", "url", "Lucy Rodgers", "Cymraeg", 90, "English");
        dvd3 = new Dvd("Dvd 3", "2017", "url", "Eleanor Maltby", "Francais", 105, "English");
        optionalDvd1 = Optional.of(dvd1);
        optionalDvd2 = Optional.of(dvd2);
        optionalDvd3 = Optional.of(dvd3);
        dvds = new ArrayList<>();
        dvds.add(dvd1);
        dvds.add(dvd2);
        dvds.add(dvd3);
    }

    @Test
    public void testGet() {
        Long id = Long.valueOf(1);

        when(DAO.get(id)).thenReturn(optionalDvd1);

        assertThat(dvdService.get(id)).as("Retrieve dvd from database with ID of 1").isEqualTo(optionalDvd1);
    }

    @Test
    public void testGet_noDvdFromDAO() {
        Long id = Long.valueOf(2);

        when(DAO.get(id)).thenReturn(Optional.empty());

        assertThat(dvdService.get(id)).as("Retrieve empty Optional from DAO").isEqualTo(Optional.empty());
    }

    @Test
    public void testGetAll() {
        when(DAO.getAll()).thenReturn(dvds);

        assertThat(dvdService.getAll()).as("Retrieve all dvds stored in the database").isEqualTo(dvds);
    }

    @Test
    public void testGetAll_noDvdsFromDAO() {
        when(DAO.getAll()).thenReturn(new ArrayList<>());

        assertThat(dvdService.getAll().isEmpty()).as("Retrieve no dvds from DAO").isTrue();
    }

    @Test
    public void test_verifySave() {
        dvdService.save(dvd1);
        verify(DAO).save(eq(dvd1));
    }

    @Test
    public void test_verifyUpdate() {
        dvdService.update(dvd2);
        verify(DAO).update(eq(dvd2));
    }

    @Test
    public void test_verifyDelete() {
        dvdService.delete(dvd3);
        verify(DAO).delete(eq(dvd3));
    }
}
