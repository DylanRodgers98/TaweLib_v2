package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.model.resource.Dvd;
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
public class DvdServiceImplIT {

    @Autowired
    private ResourceService dvdService;

    @Test
    @Transactional
    public void testCRUDOperationsOnDvd() {
        JUnitSoftAssertions softly = new JUnitSoftAssertions();
        Dvd dvd = new Dvd("DVD 1", "2019", "URL", "Dylan Rodgers",
                "English", 120, "English");

        //Test Create and Retrieve operations
        dvdService.save(dvd);
        List<Dvd> dvds = dvdService.getAll();
        Long id = dvds.get(0).getId();

        softly.assertThat(dvdService.get(id).get())
                .as("Retrieve dvd from database")
                .isEqualTo(dvd);

        //Test Update operation
        Dvd dvdToUpdate = (Dvd) dvdService.get(id).get();
        dvdToUpdate.setRuntime(90);
        dvdService.update(dvdToUpdate);

        softly.assertThat(dvdService.get(id).get())
                .as("Retrieve dvd from database with updated runtime")
                .isEqualTo(dvd);

        //Test Delete operation
        dvdService.delete(dvd);

        softly.assertThat(dvdService.get(id))
                .as("Dvd deleted")
                .isEqualTo(Optional.empty());
    }
}
