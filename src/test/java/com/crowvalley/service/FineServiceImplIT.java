package com.crowvalley.service;

import com.crowvalley.model.fine.Fine;
import org.assertj.core.api.SoftAssertions;
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
public class FineServiceImplIT {

    @Autowired
    private FineService fineService;

    @Test
    @Transactional
    public void testCRDOperationsOnFine() {
        SoftAssertions softly = new SoftAssertions();
        Fine fine = new Fine("Book 1", Long.valueOf(1), 2.50);

        //Test Create and Retrieve operations
        fineService.save(fine);
        List<Fine> fines = fineService.getAll();
        Long id = fines.get(0).getId();

        softly.assertThat(fineService.get(id).get())
                .as("Retrieve fine from database")
                .isEqualTo(fine);

        //FINE HAS NO SETTER METHODS, SO UPDATE NEED NOT BE TESTED

        //Test Delete operation
        fineService.delete(fine);

        softly.assertThat(fineService.get(id))
                .as("Fine deleted")
                .isEqualTo(Optional.empty());
    }
}
