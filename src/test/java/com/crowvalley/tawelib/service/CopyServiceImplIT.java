package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.model.resource.Book;
import com.crowvalley.tawelib.model.resource.Copy;
import com.crowvalley.tawelib.model.resource.CopyRequest;
import com.crowvalley.tawelib.model.resource.ResourceFactory;
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
@ContextConfiguration(locations = {"classpath:/spring/applicationContext.xml "})
public class CopyServiceImplIT {

    @Rule
    public final JUnitSoftAssertions softly = new JUnitSoftAssertions();
    @Autowired
    private ResourceFactory resourceFactory;
    @Autowired
    private ResourceService bookService;
    @Autowired
    private CopyService copyService;

    @Test
    @Transactional
    public void testCRUDOperationsOnCopy() {
        //Create Copy of a book
        Book book = resourceFactory.createBook("Book 1", "2019", "URL", "Dylan Rodgers",
                "Penguin", "Sci-fi", "ISBN", "English");
        bookService.save(book);
        Copy copyOfBook = resourceFactory.createCopy(book, 4);

        //Test Create and Retrieve operations
        copyService.save(copyOfBook);
        List<Copy> copies = copyService.getAll();
        Long id = copies.get(0).getId();

        softly.assertThat(copyService.get(id).get())
                .as("Retrieve copy from database")
                .isEqualTo(copyOfBook);

        //Test Update operation
        Copy copyToUpdate = copyService.get(id).get();
        copyToUpdate.setLoanDurationAsDays(7);
        copyService.update(copyToUpdate);

        softly.assertThat(copyService.get(id).get())
                .as("Retrieve copy from database with updated loan duration")
                .isEqualTo(copyOfBook);

        //Test Delete operation
        copyService.delete(copyOfBook);

        softly.assertThat(copyService.get(id))
                .as("Copy deleted")
                .isEqualTo(Optional.empty());
    }

    @Test
    @Transactional
    public void testCreateCopyRequestForCopyAndSaveToDatabase() {
        //Create Copy of a book
        Book book = resourceFactory.createBook("Book 1", "2019", "URL", "Dylan Rodgers",
                "Penguin", "Sci-fi", "ISBN", "English");
        bookService.save(book);
        Copy copyOfBook = resourceFactory.createCopy(book, 4);

        //Create CopyRequest for the Copy
        copyOfBook.createCopyRequest("DylanRodgers98");

        //Save Copy to database
        copyService.save(copyOfBook);
        List<Copy> copies = copyService.getAll();
        Long id = copies.get(0).getId();

        //Test Copy and its CopyRequest can be retrieved from database
        Optional<Copy> retrievedCopy = copyService.get(id);

        softly.assertThat(retrievedCopy.get())
                .as("Retrieve copy from database")
                .isEqualTo(copyOfBook);

        CopyRequest expectedCopyRequest = copyOfBook.getCopyRequests().get(0);

        softly.assertThat(retrievedCopy.get().getCopyRequests())
                .as("Retrieve list of copy requests for copy retrieved from database")
                .contains(expectedCopyRequest);
    }

    @Transactional
    @Test
    public void testCreateAndDeleteCopyRequestOnPersistedCopy() {
        //Create Copy of a book
        Book book = resourceFactory.createBook("Book 1", "2019", "URL", "Dylan Rodgers",
                "Penguin", "Sci-fi", "ISBN", "English");
        bookService.save(book);
        Copy copyOfBook = resourceFactory.createCopy(book, 4);
        copyService.save(copyOfBook);

        //Create CopyRequest for the Copy
        String username = "DylanRodgers98";
        copyService.createCopyRequestForPersistedCopy(copyOfBook, username);

        //Retrieve Copy and newly created CopyRequest from database
        Copy retrievedCopy = copyService.getAll().get(0);
        CopyRequest copyRequest = retrievedCopy.getCopyRequests().get(0);

        //Test Copy saved the copy request correctly
        softly.assertThat(copyRequest.getUsername())
                .as("Copy request created for copy by user")
                .isEqualTo(username);

        softly.assertThat(copyRequest.getCopy())
                .as("Copy request created for copy")
                .isEqualTo(copyOfBook);

        softly.assertThat(copyRequest.getRequestDate().toLocalDateTime().toLocalDate())
                .as("Copy request created for copy by user")
                .isEqualTo(new Date(System.currentTimeMillis()).toLocalDate());

        //Delete copy request from the Copy
        copyService.deleteCopyRequestFromPersistedCopy(copyOfBook, username);

        //Test Copy does not contain the copy request
        softly.assertThat(retrievedCopy.getCopyRequests())
                .as("Copy after deleting copy request")
                .doesNotContain(copyRequest);
    }
}
