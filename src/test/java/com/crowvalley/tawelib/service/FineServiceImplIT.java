package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.model.fine.Fine;
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
@ContextConfiguration(locations = { "classpath:/spring/applicationContext.xml "})
public class FineServiceImplIT {

    @Autowired
    private FineService fineService;

    @Rule
    public final JUnitSoftAssertions softly = new JUnitSoftAssertions();

    @Test
    @Transactional
    public void testCRDOperationsOnFine() {
        Fine fine = new Fine("DylanRodgers98", 1L, 2.50);

        //Test Create and Retrieve operations
        fineService.save(fine);
        List<Fine> fines = fineService.getAll();
        Long id = fines.get(0).getId();

        softly.assertThat(fineService.get(id).get())
                .as("Retrieve fine from database")
                .isEqualTo(fine);

        //Test Delete operation
        fineService.delete(fine);

        softly.assertThat(fineService.get(id))
                .as("Fine deleted")
                .isEqualTo(Optional.empty());
    }
}
