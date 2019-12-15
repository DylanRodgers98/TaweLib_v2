package com.crowvalley.tawelib.model.resource;

import com.crowvalley.tawelib.service.CopyService;
import com.crowvalley.tawelib.service.LoanService;
import com.crowvalley.tawelib.service.ResourceService;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/applicationContext.xml "})
public class ResourceFactoryIT {

    private Book book = ResourceFactory.createBook("Book", "2015", StringUtils.EMPTY, "Dylan Rodgers", "Penguin", "Children", "Foo", "English");

    private Dvd dvd = ResourceFactory.createDvd("Dvd", "2018", StringUtils.EMPTY, "Dylan Rodgers", "English", 120, "Welsh");

    private Laptop laptop = ResourceFactory.createLaptop("Laptop", "2014", StringUtils.EMPTY, "Acer", "Aspire", "Windows 10");

    @Autowired
    private ResourceService<Book> bookService;

    @Autowired
    private ResourceService<Dvd> dvdService;

    @Autowired
    private ResourceService<Laptop> laptopService;

    @Autowired
    private LoanService loanService;

    @Autowired
    private CopyService copyService;

    @Rule
    public final JUnitSoftAssertions softly = new JUnitSoftAssertions();

    @Test
    @Transactional
    public void testAddBookToDatabase_CreateCopyOfBook() {
        bookService.save(book);

        Copy copyOfBook = ResourceFactory.createCopy(book, 4);
        copyService.save(copyOfBook);

        softly.assertThat(copyOfBook.getResourceId())
                .as("Resource ID of the copy persisted to database")
                .isEqualTo(book.getId());

        softly.assertThat(copyOfBook.getResourceType())
                .as("Resource type of the copy persisted to database")
                .isEqualTo(ResourceType.BOOK);
    }

    @Test
    @Transactional
    public void testAddDvdToDatabase_CreateCopyOfDvd() {
        dvdService.save(dvd);

        Copy copyOfDvd = ResourceFactory.createCopy(dvd, 4);
        copyService.save(copyOfDvd);

        softly.assertThat(copyOfDvd.getResourceId())
                .as("Resource ID of the copy persisted to database")
                .isEqualTo(dvd.getId());

        softly.assertThat(copyOfDvd.getResourceType())
                .as("Resource type of the copy persisted to database")
                .isEqualTo(ResourceType.DVD);
    }

    @Test
    @Transactional
    public void testAddLaptopToDatabase_CreateCopyOfLaptop() {
        laptopService.save(laptop);

        Copy copyOfLaptop = ResourceFactory.createCopy(laptop, 4);
        copyService.save(copyOfLaptop);

        softly.assertThat(copyOfLaptop.getResourceId())
                .as("Resource ID of the copy persisted to database")
                .isEqualTo(laptop.getId());

        softly.assertThat(copyOfLaptop.getResourceType())
                .as("Resource type of the copy persisted to database")
                .isEqualTo(ResourceType.LAPTOP);
    }

    @Test
    @Transactional
    public void testAddCopyToDatabase_CreateLoanOfCopy() {
        laptopService.save(laptop);

        Copy copyOfLaptop = ResourceFactory.createCopy(laptop, 4);
        copyService.save(copyOfLaptop);

        Loan loanOfLaptop = ResourceFactory.createLoanForCopy(copyOfLaptop, "DylanRodgers98");
        loanService.save(loanOfLaptop);

        softly.assertThat(loanOfLaptop.getCopyId())
                .as("Copy ID of the loan persisted to database")
                .isEqualTo(copyOfLaptop.getId());
    }

    @Test(expected = IllegalArgumentException.class)
    @Transactional
    public void testCreateCopyWithNoResourceID() {
        ResourceFactory.createCopy(book, 4);
    }

    @Test(expected = IllegalArgumentException.class)
    @Transactional
    public void testCreateLoanWithNoCopyID() {
        Copy copy = new Copy(1L, ResourceType.BOOK, 4);
        ResourceFactory.createLoanForCopy(copy, "DylanRodgers98");
    }
}
