package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.model.resource.Laptop;
import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/applicationContext.xml "})
public class LaptopServiceImplIT {

    @Autowired
    private ResourceService laptopService;

    @Test
    @Transactional
    public void testCRUDOperationsOnLaptop() {
        JUnitSoftAssertions softly = new JUnitSoftAssertions();
        Laptop laptop = new Laptop("Laptop 1", "2019", "URL",
                "HP", "Ultrabook", "Windows 10");

        //Test Create and Retrieve operations
        laptopService.save(laptop);
        List<Laptop> laptops = laptopService.getAll();
        Long id = laptops.get(0).getId();

        softly.assertThat(laptopService.get(id).get())
                .as("Retrieve laptop from database")
                .isEqualTo(laptop);

        //Test Update operation
        Laptop laptopToUpdate = (Laptop) laptopService.get(id).get();
        laptopToUpdate.setOs("Ubuntu");
        laptopService.update(laptopToUpdate);

        softly.assertThat(laptopService.get(id).get())
                .as("Retrieve laptop from database with updated OS")
                .isEqualTo(laptop);

        //Test Delete operation
        laptopService.delete(laptop);

        softly.assertThat(laptopService.get(id))
                .as("Laptop deleted")
                .isEqualTo(Optional.empty());
    }
}
