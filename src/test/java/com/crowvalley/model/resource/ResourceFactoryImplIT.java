package com.crowvalley.model.resource;

import com.crowvalley.service.CopyService;
import com.crowvalley.service.ResourceService;
import org.assertj.core.api.SoftAssertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/applicationContext.xml "})
public class ResourceFactoryImplIT {

    @Autowired
    private ResourceService bookService;

    @Autowired
    private ResourceService dvdService;

    @Autowired
    private ResourceService laptopService;

    @Autowired
    private CopyService copyService;

    @Autowired
    private ResourceFactory resourceFactory;

    @Test
    @Transactional
    public void testAddBookToDatabase_CreateCopyOfBook() {
        bookService.save(resourceFactory.createBook("","","","","","","",""));

        List<Book> books = bookService.getAll();
        Book book = books.get(0);

        copyService.save(resourceFactory.createCopy(book, 4));

        Copy copy = copyService.getAll().get(0);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(copy.getResourceId()).as("Resource ID of the copy persisted to database").isEqualTo(book.getId());
        softly.assertThat(copy.getResourceType()).as("Resource type of the copy persisted to database").isEqualTo("Book");
    }

    @Test
    @Transactional
    public void testAddDvdToDatabase_CreateCopyOfDvd() {
        dvdService.save(resourceFactory.createDvd("","","","","",120,""));

        List<Dvd> dvds = dvdService.getAll();
        Dvd dvd = dvds.get(0);

        copyService.save(resourceFactory.createCopy(dvd, 4));

        Copy copy = copyService.getAll().get(0);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(copy.getResourceId()).as("Resource ID of the copy persisted to database").isEqualTo(dvd.getId());
        softly.assertThat(copy.getResourceType()).as("Resource type of the copy persisted to database").isEqualTo("Dvd");
    }

    @Test
    @Transactional
    public void testAddLaptopToDatabase_CreateCopyOfLaptop() {
        laptopService.save(resourceFactory.createLaptop("","","","","",""));

        List<Laptop> laptops = laptopService.getAll();
        Laptop laptop = laptops.get(0);

        copyService.save(resourceFactory.createCopy(laptop, 4));

        Copy copy = copyService.getAll().get(0);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(copy.getResourceId()).as("Resource ID of the copy persisted to database").isEqualTo(laptop.getId());
        softly.assertThat(copy.getResourceType()).as("Resource type of the copy persisted to database").isEqualTo("Laptop");
    }

    @Test(expected = IllegalArgumentException.class)
    @Transactional
    public void testCreateCopyWithNoResourceID() {
        Book book = resourceFactory.createBook("","","","","","","","");
        resourceFactory.createCopy(book, 4);
    }
}
