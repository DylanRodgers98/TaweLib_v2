package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.model.user.Librarian;
import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/applicationContext.xml" })
public class LibrarianServiceImplIT {

    @Autowired
    private LibrarianService librarianService;

    @Rule
    public final JUnitSoftAssertions softly = new JUnitSoftAssertions();

    @Test
    @Transactional
    public void testCRUDOperationsOnLibrarian() {
        Librarian librarian = new Librarian("DylanRodgers98", "Dylan", "Rodgers",
                "07866345602", "5 Bowood", "Harford Drive", "Bristol",
                "South Gloucestershire", "BS16 1NS", "",
                new Date(System.currentTimeMillis()), 1L);

        //Test Create and Retrieve operations
        librarianService.save(librarian);

        softly.assertThat(librarianService.getWithUsername("DylanRodgers98").get())
                .as("Retrieve librarian user from database with username DylanRodgers98")
                .isEqualTo(librarian);

        softly.assertThat(librarianService.getWithStaffNumber(1L).get())
                .as("Retrieve librarian user from database with staff number of 1")
                .isEqualTo(librarian);

        //Test Update operation
        Librarian librarianToUpdate = librarianService.getWithUsername("DylanRodgers98").get();
        String newPhoneNumber = "07866 123456";
        librarianToUpdate.setPhoneNum(newPhoneNumber);
        librarianService.update(librarianToUpdate);

        softly.assertThat(librarianService.getWithUsername("DylanRodgers98").get())
                .as("Retrieve librarian user from database with username DylanRodgers98 with updated phone number")
                .isEqualTo(librarian);

        softly.assertThat(librarianService.getWithUsername("DylanRodgers98").get().getPhoneNum())
                .as("Updated librarian user's phone number")
                .isEqualTo(newPhoneNumber);

        //Test Delete operation
        librarianService.delete(librarian);

        softly.assertThat(librarianService.getWithUsername("DylanRodgers98"))
                .as("Librarian user DylanRodgers98 deleted")
                .isEqualTo(Optional.empty());

        softly.assertThat(librarianService.getWithStaffNumber(1L))
                .as("Librarian user with staff number of 1 deleted")
                .isEqualTo(Optional.empty());
    }
}
