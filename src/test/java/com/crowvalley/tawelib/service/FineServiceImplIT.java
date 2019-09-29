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
@ContextConfiguration(locations = {"classpath:/spring/applicationContext.xml "})
public class FineServiceImplIT {

    @Rule
    public final JUnitSoftAssertions softly = new JUnitSoftAssertions();
    @Autowired
    private FineService fineService;

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

    @Test
    @Transactional
    public void testGetAllFinesForUser() {
        String username = "DylanRodgers98";

        Fine fine1 = new Fine(username, 1L, 2.50);
        Fine fine2 = new Fine("Definitely not DylanRodgers98", 2L, 4.50);
        Fine fine3 = new Fine(username, 3L, 7.50);
        fineService.save(fine1);
        fineService.save(fine2);
        fineService.save(fine3);

        List<Fine> fines = fineService.getAllFinesForUser(username);
        softly.assertThat(fines)
                .as("List of fines retrieved from database for a given user")
                .contains(fine1, fine3)
                .doesNotContain(fine2);
    }
}
