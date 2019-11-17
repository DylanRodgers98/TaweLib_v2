package com.crowvalley.tawelib.model.resource;

import com.crowvalley.tawelib.service.CopyService;
import com.crowvalley.tawelib.service.LoanService;
import com.crowvalley.tawelib.service.ResourceService;
import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/applicationContext.xml "})
public class ResourceFactoryIT {

    @Rule
    public final JUnitSoftAssertions softly = new JUnitSoftAssertions();
    @Autowired
    private ResourceService bookService;
    @Autowired
    private ResourceService dvdService;
    @Autowired
    private ResourceService laptopService;
    @Autowired
    private LoanService loanService;
    @Autowired
    private CopyService copyService;

    @Test
    @Transactional
    public void testAddBookToDatabase_CreateCopyOfBook() {
        bookService.save(ResourceFactory.createBook("", "", "", "", "", "", "", ""));

        List<Book> books = bookService.getAll();
        Book book = books.get(0);

        copyService.save(ResourceFactory.createCopy(book, 4));

        Copy copy = copyService.getAll().get(0);

        softly.assertThat(copy.getResourceId())
                .as("Resource ID of the copy persisted to database")
                .isEqualTo(book.getId());

        softly.assertThat(copy.getResourceType())
                .as("Resource type of the copy persisted to database")
                .isEqualTo("Book");
    }

    @Test
    @Transactional
    public void testAddDvdToDatabase_CreateCopyOfDvd() {
        dvdService.save(ResourceFactory.createDvd("", "", "", "", "", 120, ""));

        List<Dvd> dvds = dvdService.getAll();
        Dvd dvd = dvds.get(0);

        copyService.save(ResourceFactory.createCopy(dvd, 4));

        Copy copy = copyService.getAll().get(0);

        softly.assertThat(copy.getResourceId())
                .as("Resource ID of the copy persisted to database")
                .isEqualTo(dvd.getId());

        softly.assertThat(copy.getResourceType())
                .as("Resource type of the copy persisted to database")
                .isEqualTo("Dvd");
    }

    @Test
    @Transactional
    public void testAddLaptopToDatabase_CreateCopyOfLaptop() {
        laptopService.save(ResourceFactory.createLaptop("", "", "", "", "", ""));

        List<Laptop> laptops = laptopService.getAll();
        Laptop laptop = laptops.get(0);

        copyService.save(ResourceFactory.createCopy(laptop, 4));

        Copy copy = copyService.getAll().get(0);

        softly.assertThat(copy.getResourceId())
                .as("Resource ID of the copy persisted to database")
                .isEqualTo(laptop.getId());

        softly.assertThat(copy.getResourceType())
                .as("Resource type of the copy persisted to database")
                .isEqualTo("Laptop");
    }

    @Test(expected = IllegalArgumentException.class)
    @Transactional
    public void testCreateCopyWithNoResourceID() {
        Book book = ResourceFactory.createBook("", "", "", "", "", "", "", "");
        ResourceFactory.createCopy(book, 4);
    }

    @Test
    @Transactional
    public void testAddCopyToDatabase_CreateLoanOfCopy() {
        laptopService.save(ResourceFactory.createLaptop("", "", "", "", "", ""));

        List<Laptop> laptops = laptopService.getAll();
        Laptop laptop = laptops.get(0);

        copyService.save(ResourceFactory.createCopy(laptop, 4));

        Copy copy = copyService.getAll().get(0);

        loanService.save(ResourceFactory.createLoanForCopy(copy, "DylanRodgers98"));
        Loan loan = loanService.getAll().get(0);

        softly.assertThat(loan.getCopyId())
                .as("Copy ID of the loan persisted to database")
                .isEqualTo(copy.getId());
    }

    @Test(expected = IllegalArgumentException.class)
    @Transactional
    public void testCreateLoanWithNoCopyID() {
        Copy copy = new Copy(1L, ResourceType.BOOK, 4);
        ResourceFactory.createLoanForCopy(copy, "DylanRodgers98");
    }
}
