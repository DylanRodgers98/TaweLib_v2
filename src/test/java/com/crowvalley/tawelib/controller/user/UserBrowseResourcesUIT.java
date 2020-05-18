package com.crowvalley.tawelib.controller.user;

import com.crowvalley.tawelib.dao.ResourceDAO;
import com.crowvalley.tawelib.model.resource.*;
import com.crowvalley.tawelib.util.FXMLTestUtils;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.testfx.framework.junit.ApplicationTest;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/spring/applicationContext.xml")
public class UserBrowseResourcesUIT extends ApplicationTest {

    private static final String USER_BROWSE_RESOURCE_FXML = "/fxml/user/userBrowseResources.fxml";

    private static final String BOOK = "Book";

    private static final String DVD = "Dvd";

    private static final String LAPTOP = "Laptop";

    private static final String YEAR = "2020";

    private static final String BACK_BUTTON_NODE_QUERY = "#btnBack";

    private static final String RESOURCES_TABLE_NODE_QUERY = "#tblResources";

    @Autowired
    private ResourceDAO resourceDAO;

    private Resource book;

    private Resource dvd;

    private Resource laptop;

    @After
    public void tearDown() {
        resourceDAO.delete(book);
        resourceDAO.delete(dvd);
        resourceDAO.delete(laptop);
    }

    @Override
    public void start(Stage stage) throws IllegalAccessException, InstantiationException {
        createResources();
        FXMLTestUtils.initTest(stage, USER_BROWSE_RESOURCE_FXML);
    }

    @Test
    public void testTableContainsResources() {
        TableView<ResourceDTO> tableView = lookup(RESOURCES_TABLE_NODE_QUERY).queryTableView();

        assertThat(tableView.getItems())
                .extracting(ResourceDTO::getTitle, ResourceDTO::getYear, ResourceDTO::getResourceType)
                .as("TableView contains ResourceDTOs with titles, years and types matching that of the Resources persisted in the start method")
                .contains(tuple(book.getTitle(), book.getYear(), ResourceType.BOOK),
                          tuple(dvd.getTitle(), dvd.getYear(), ResourceType.DVD),
                          tuple(laptop.getTitle(), laptop.getYear(), ResourceType.LAPTOP));
    }

    @Test
    public void testGoBackToUserHome() {
        clickOn(BACK_BUTTON_NODE_QUERY);
        FXMLTestUtils.verifyWindowShowing(targetWindow(), FXMLTestUtils.USER_HOME_WINDOW_ID);
    }

    @Test
    public void testSearch_Book_EnterKeyPressed() {
        searchForResourceByPressingEnterKey(book);
    }

    @Test
    public void testSearch_Dvd_EnterKeyPressed() {
        searchForResourceByPressingEnterKey(dvd);
    }

    @Test
    public void testSearch_Laptop_EnterKeyPressed() {
        searchForResourceByPressingEnterKey(laptop);
    }

    @Test
    public void testSearch_Book_SearchButtonClicked() {
        searchForResourceByClickingSearchButton(book);
    }

    @Test
    public void testSearch_Dvd_SearchButtonClicked() {
        searchForResourceByClickingSearchButton(dvd);
    }

    @Test
    public void testSearch_Laptop_SearchButtonClicked() {
        searchForResourceByClickingSearchButton(laptop);
    }

    @Test
    public void testExecuteTypeChange_Book() {
        changeTypeToSameTypeOf(book);
    }

    @Test
    public void testExecuteTypeChange_Dvd() {
        changeTypeToSameTypeOf(dvd);
    }

    @Test
    public void testExecuteTypeChange_Laptop() {
        changeTypeToSameTypeOf(laptop);
    }

    private void searchForResourceByPressingEnterKey(Resource resource) {
        clickOn("#txtSearch");
        write(resource.getTitle());
        press(KeyCode.ENTER);
        verifyThatTableViewContainsResourceDTOFor(resource);
    }

    private void searchForResourceByClickingSearchButton(Resource resource) {
        clickOn("#txtSearch");
        write(resource.getTitle());
        clickOn("#btnSearch");
        verifyThatTableViewContainsResourceDTOFor(resource);
    }

    private void verifyThatTableViewContainsResourceDTOFor(Resource resource) {
        TableView<ResourceDTO> tableView = lookup(RESOURCES_TABLE_NODE_QUERY).queryTableView();

        assertThat(tableView.getItems())
                .extracting(ResourceDTO::getTitle, ResourceDTO::getYear, ResourceDTO::getResourceType)
                .as("TableView contains ResourceDTO with titles, years and types matching that of the Resource persisted in the start method")
                .contains(tuple(resource.getTitle(), resource.getYear(), resource.getResourceType()));
    }

    private void changeTypeToSameTypeOf(Resource resource) {
        clickOn("#cmbType");
        int index = lookup("#cmbType").queryAs(ChoiceBox.class).getItems().indexOf(resource.getResourceType());
        for (int i = 0; i < index; i++) {
            push(KeyCode.DOWN);
        }
        push(KeyCode.ENTER);
        verifyThatTableViewContainsResourceDTOFor(resource);
    }

    private void createResources() {
        book = new Book();
        book.setTitle(BOOK);
        book.setYear(YEAR);
        resourceDAO.saveOrUpdate(book);

        dvd = new Dvd();
        dvd.setTitle(DVD);
        dvd.setYear(YEAR);
        resourceDAO.saveOrUpdate(dvd);

        laptop = new Laptop();
        laptop.setTitle(LAPTOP);
        laptop.setYear(YEAR);
        resourceDAO.saveOrUpdate(laptop);
    }

}
