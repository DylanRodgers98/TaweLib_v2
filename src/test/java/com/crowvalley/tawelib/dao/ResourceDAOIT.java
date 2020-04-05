package com.crowvalley.tawelib.dao;

import com.crowvalley.tawelib.model.resource.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/spring/applicationContext.xml")
@Transactional
public class ResourceDAOIT {

    private static final String BOOK_TITLE = "Book";

    private static final String DVD_TITLE = "DVD";

    private static final String LAPTOP_TITLE = "Laptop";

    private static final String YEAR = "2020";

    @Autowired
    private ResourceDAO resourceDAO;

    @Test
    public void testGetAllResouceDTOs() {
        createResources();

        List<ResourceDTO> resourceDTOs = resourceDAO.getAllResourceDTOs(Resource.class);
        assertThat(resourceDTOs)
                .extracting(ResourceDTO::getTitle, ResourceDTO::getYear, ResourceDTO::getResourceType)
                .as("ResourceDTOs for all Resource entities have correct title, year and ResourceType from corresponding Resource entity")
                .contains(tuple(BOOK_TITLE, YEAR, ResourceType.BOOK),
                          tuple(DVD_TITLE, YEAR, ResourceType.DVD),
                          tuple(LAPTOP_TITLE, YEAR, ResourceType.LAPTOP));

        List<ResourceDTO> bookDTOs = resourceDAO.getAllResourceDTOs(Book.class);
        assertThat(bookDTOs)
                .extracting(ResourceDTO::getTitle, ResourceDTO::getYear, ResourceDTO::getResourceType)
                .as("ResourceDTOs for all Book entities have correct title, year and ResourceType from corresponding Book entity")
                .contains(tuple(BOOK_TITLE, YEAR, ResourceType.BOOK));

        List<ResourceDTO> dvdDTOs = resourceDAO.getAllResourceDTOs(Dvd.class);
        assertThat(dvdDTOs)
                .extracting(ResourceDTO::getTitle, ResourceDTO::getYear, ResourceDTO::getResourceType)
                .as("ResourceDTOs for all DVD entities have correct title, year and ResourceType from corresponding DVD entity")
                .contains(tuple(DVD_TITLE, YEAR, ResourceType.DVD));

        List<ResourceDTO> laptopDTOs = resourceDAO.getAllResourceDTOs(Laptop.class);
        assertThat(laptopDTOs)
                .extracting(ResourceDTO::getTitle, ResourceDTO::getYear, ResourceDTO::getResourceType)
                .as("ResourceDTOs for all Laptop entities have correct title, year and ResourceType from corresponding Laptop entity")
                .contains(tuple(LAPTOP_TITLE, YEAR, ResourceType.LAPTOP));
    }

    @Test
    public void testSearch() {
        createResources();

        List<ResourceDTO> resourceDTOsUsingYear = resourceDAO.search(YEAR, Resource.class);
        assertThat(resourceDTOsUsingYear)
                .extracting(ResourceDTO::getTitle, ResourceDTO::getYear, ResourceDTO::getResourceType)
                .as("ResourceDTOs searched for by year have correct title, year and ResourceType from corresponding Book entity")
                .contains(tuple(BOOK_TITLE, YEAR, ResourceType.BOOK),
                          tuple(DVD_TITLE, YEAR, ResourceType.DVD),
                          tuple(LAPTOP_TITLE, YEAR, ResourceType.LAPTOP));

        List<ResourceDTO> bookDTOsUsingTitle = resourceDAO.search(BOOK_TITLE, Book.class);
        assertThat(bookDTOsUsingTitle)
                .extracting(ResourceDTO::getTitle, ResourceDTO::getYear, ResourceDTO::getResourceType)
                .as("ResourceDTOs for Book searched using title has correct title, year and ResourceType from corresponding Book entity")
                .contains(tuple(BOOK_TITLE, YEAR, ResourceType.BOOK));

        List<ResourceDTO> bookDTOsUsingYear = resourceDAO.search(YEAR, Book.class);
        assertThat(bookDTOsUsingYear)
                .extracting(ResourceDTO::getTitle, ResourceDTO::getYear, ResourceDTO::getResourceType)
                .as("ResourceDTOs for Book searched using year has correct title, year and ResourceType from corresponding Book entity")
                .contains(tuple(BOOK_TITLE, YEAR, ResourceType.BOOK));

        List<ResourceDTO> dvdDTOsUsingTitle = resourceDAO.search(DVD_TITLE, Dvd.class);
        assertThat(dvdDTOsUsingTitle)
                .extracting(ResourceDTO::getTitle, ResourceDTO::getYear, ResourceDTO::getResourceType)
                .as("ResourceDTOs for DVD searched using title has correct title, year and ResourceType from corresponding Book entity")
                .contains(tuple(DVD_TITLE, YEAR, ResourceType.DVD));

        List<ResourceDTO> dvdDTOsUsingYear = resourceDAO.search(YEAR, Dvd.class);
        assertThat(dvdDTOsUsingYear)
                .extracting(ResourceDTO::getTitle, ResourceDTO::getYear, ResourceDTO::getResourceType)
                .as("ResourceDTOs for DVD searched using year has correct title, year and ResourceType from corresponding Book entity")
                .contains(tuple(DVD_TITLE, YEAR, ResourceType.DVD));

        List<ResourceDTO> laptopDTOsUsingTitle = resourceDAO.search(LAPTOP_TITLE, Laptop.class);
        assertThat(laptopDTOsUsingTitle)
                .extracting(ResourceDTO::getTitle, ResourceDTO::getYear, ResourceDTO::getResourceType)
                .as("ResourceDTOs for Laptop searched using title has correct title, year and ResourceType from corresponding Book entity")
                .contains(tuple(LAPTOP_TITLE, YEAR, ResourceType.LAPTOP));

        List<ResourceDTO> laptopDTOsUsingYear = resourceDAO.search(YEAR, Laptop.class);
        assertThat(laptopDTOsUsingYear)
                .extracting(ResourceDTO::getTitle, ResourceDTO::getYear, ResourceDTO::getResourceType)
                .as("ResourceDTOs for Laptop searched using year has correct title, year and ResourceType from corresponding Book entity")
                .contains(tuple(LAPTOP_TITLE, YEAR, ResourceType.LAPTOP));
    }

    private void createResources() {
        Resource book = new Book();
        book.setTitle(BOOK_TITLE);
        book.setYear(YEAR);
        resourceDAO.saveOrUpdate(book);

        Resource dvd = new Dvd();
        dvd.setTitle(DVD_TITLE);
        dvd.setYear(YEAR);
        resourceDAO.saveOrUpdate(dvd);

        Resource laptop = new Laptop();
        laptop.setTitle(LAPTOP_TITLE);
        laptop.setYear(YEAR);
        resourceDAO.saveOrUpdate(laptop);
    }

}
