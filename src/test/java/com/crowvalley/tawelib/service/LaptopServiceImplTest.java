package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.dao.LaptopDAOImpl;
import com.crowvalley.tawelib.model.resource.Laptop;
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
public class LaptopServiceImplTest {

    @Mock
    private LaptopDAOImpl DAO;

    @InjectMocks
    private LaptopServiceImpl laptopService;

    private Laptop laptop1;

    private Laptop laptop2;

    private Laptop laptop3;

    private Optional<Laptop> optionalLaptop1;

    private Optional<Laptop> optionalLaptop2;

    private Optional<Laptop> optionalLaptop3;

    private List<Laptop> laptops;

    @Before
    public void setup() {
        laptop1 = new Laptop("Laptop 1", "2019", "url", "HP", "Ultrabook", "Ubuntu");
        laptop2 = new Laptop("Laptop 2", "2018", "url", "Acer", "Aspire", "Windows 10");
        laptop3 = new Laptop("Laptop 3", "2017", "url", "Apple", "Macbook", "OS X");
        optionalLaptop1 = Optional.of(laptop1);
        optionalLaptop2 = Optional.of(laptop2);
        optionalLaptop3 = Optional.of(laptop3);
        laptops = new ArrayList<>();
        laptops.add(laptop1);
        laptops.add(laptop2);
        laptops.add(laptop3);
    }

    @Test
    public void testGet() {
        Long id = Long.valueOf(1);

        when(DAO.get(id)).thenReturn(optionalLaptop1);

        assertThat(laptopService.get(id))
                .as("Retrieve laptop from DAO with ID of 1")
                .isEqualTo(optionalLaptop1);
    }

    @Test
    public void testGet_noLaptopFromDAO() {
        Long id = Long.valueOf(2);

        when(DAO.get(id)).thenReturn(Optional.empty());

        assertThat(laptopService.get(id))
                .as("Retrieve empty Optional from DAO")
                .isEqualTo(Optional.empty());
    }

    @Test
    public void testGetAll() {
        when(DAO.getAll()).thenReturn(laptops);

        assertThat(laptopService.getAll())
                .as("Retrieve all laptops from DAO")
                .isEqualTo(laptops);
    }

    @Test
    public void testGetAll_noLaptopsFromDAO() {
        when(DAO.getAll()).thenReturn(new ArrayList<>());

        assertThat(laptopService.getAll().isEmpty())
                .as("Retrieve no laptops from DAO")
                .isTrue();
    }

    @Test
    public void test_verifySave() {
        laptopService.save(laptop1);
        verify(DAO).save(eq(laptop1));
    }

    @Test
    public void test_verifyUpdate() {
        laptopService.update(laptop2);
        verify(DAO).update(eq(laptop2));
    }

    @Test
    public void test_verifyDelete() {
        laptopService.delete(laptop3);
        verify(DAO).delete(eq(laptop3));
    }
}
