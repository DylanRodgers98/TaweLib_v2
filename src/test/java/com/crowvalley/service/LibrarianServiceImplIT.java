package com.crowvalley.service;

import com.crowvalley.model.user.Librarian;
import org.assertj.core.api.SoftAssertions;
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

    @Test
    @Transactional
    public void testCRUDOperationsOnLibrarian() {
        SoftAssertions softly = new SoftAssertions();
        Librarian librarian = new Librarian("DylanRodgers98", "Dylan", "Rodgers",
                "07866345602", "5 Bowood", "Harford Drive", "Bristol",
                "South Gloucestershire", "BS16 1NS", "",
                new Date(System.currentTimeMillis()), Long.valueOf(1));

        //Test Create and Retrieve operations
        librarianService.save(librarian);
        Optional<Librarian> librarianToRetrieveByUsername = librarianService.getWithUsername("DylanRodgers98");

        softly.assertThat(librarianToRetrieveByUsername.get())
                .as("Retrieve librarian user from database with username DylanRodgers98")
                .isEqualTo(librarian);

        Optional<Librarian> librarianToRetrieveByStaffNum = librarianService.getWithStaffNumber(Long.valueOf(1));
        softly.assertThat(librarianToRetrieveByStaffNum.get())
                .as("Retrieve librarian user from database with staff number of 1")
                .isEqualTo(librarian);

        //Test Update operation
        Librarian librarianToUpdate = librarianToRetrieveByUsername.get();
        librarianToUpdate.setPhoneNum("07866 123456");
        librarianService.update(librarianToUpdate);

        softly.assertThat(librarianToRetrieveByUsername.get())
                .as("Retrieve librarian user from database with username DylanRodgers98 with updated phone number")
                .isEqualTo(librarian);

        //Test Delete operation
        librarianService.delete(librarian);

        softly.assertThat(librarianService.getWithUsername("DylanRodgers98"))
                .as("Librarian user DylanRodgers98 deleted")
                .isEqualTo(Optional.empty());
    }
}
