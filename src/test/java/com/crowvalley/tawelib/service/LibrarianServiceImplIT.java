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
import java.util.List;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/applicationContext.xml" })
public class LibrarianServiceImplIT {

    private static final String USERNAME = "DylanRodgers98";

    private static final Long STAFF_NUMBER = -1L;

    @Autowired
    private LibrarianService librarianService;

    @Rule
    public final JUnitSoftAssertions softly = new JUnitSoftAssertions();

    @Test
    @Transactional
    public void testCRUDOperationsOnLibrarian() {
        Librarian librarian = new Librarian(USERNAME, "Dylan", "Rodgers",
                "07866345602", "5 Bowood", "Harford Drive", "Bristol",
                "South Gloucestershire", "BS16 1NS", "",
                new Date(System.currentTimeMillis()), STAFF_NUMBER);

        //Test Create and Retrieve operations
        librarianService.save(librarian);

        softly.assertThat(librarianService.getWithUsername(USERNAME).get())
                .as("Retrieve librarian user from database with username DylanRodgers98")
                .isEqualTo(librarian);

        softly.assertThat(librarianService.getWithStaffNumber(STAFF_NUMBER).get())
                .as("Retrieve librarian user from database with staff number of 1")
                .isEqualTo(librarian);

        List<Librarian> librarians = librarianService.getAll();
        softly.assertThat(librarians)
                .as("List of all librarians retrieved from database")
                .contains(librarian);

        //Test Update operation
        String newPhoneNumber = "07866 123456";
        librarian.setPhoneNum(newPhoneNumber);
        librarianService.update(librarian);

        softly.assertThat(librarianService.getWithUsername(USERNAME).get())
                .as("Retrieve librarian user from database with username DylanRodgers98 with updated phone number")
                .isEqualTo(librarian);

        softly.assertThat(librarianService.getWithUsername(USERNAME).get().getPhoneNum())
                .as("Updated librarian user's phone number")
                .isEqualTo(newPhoneNumber);

        //Test Delete operation
        librarianService.delete(librarian);

        softly.assertThat(librarianService.getWithUsername(USERNAME))
                .as("Librarian user DylanRodgers98 deleted")
                .isEqualTo(Optional.empty());

        softly.assertThat(librarianService.getWithStaffNumber(STAFF_NUMBER))
                .as("Librarian user with staff number of 1 deleted")
                .isEqualTo(Optional.empty());
    }

    @Test(expected = IllegalArgumentException.class)
    @Transactional
    public void testSaveLibrarianWithDuplicateStaffNumber() {
        Librarian librarian1 = new Librarian(USERNAME, "Dylan", "Rodgers",
                "07866345602", "5 Bowood", "Harford Drive", "Bristol",
                "South Gloucestershire", "BS16 1NS", "",
                new Date(System.currentTimeMillis()), STAFF_NUMBER);

        Librarian librarian2 = new Librarian("Nenner11", "Eleanor", "Maltby",
                "07866345602", "28", "Parnell Road", "Bristol",
                "South Gloucestershire", "BS16 1WA", "",
                new Date(System.currentTimeMillis()), STAFF_NUMBER);

        librarianService.save(librarian1);
        librarianService.save(librarian2);
    }
}
