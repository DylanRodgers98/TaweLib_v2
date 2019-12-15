package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.model.resource.Laptop;
import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/applicationContext.xml "})
public class LaptopServiceImplIT {

    @Autowired
    private ResourceService<Laptop> laptopService;

    @Rule
    public final JUnitSoftAssertions softly = new JUnitSoftAssertions();

    @Test
    @Transactional
    public void testCRUDOperationsOnLaptop() {
        Laptop laptop = new Laptop("Laptop 1", "2019", "URL",
                "HP", "Ultrabook", "Windows 10");

        //Test Create and Retrieve operations
        laptopService.save(laptop);
        Long id = laptop.getId();

        softly.assertThat(laptopService.get(id).get())
                .as("Retrieve laptop from database")
                .isEqualTo(laptop);

        //Test Update operation
        laptop.setOs("Ubuntu");
        laptopService.update(laptop);

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
