package com.crowvalley.tawelib.controller.user;

import com.crowvalley.tawelib.dao.ResourceDAO;
import com.crowvalley.tawelib.model.resource.Book;
import com.crowvalley.tawelib.model.resource.Dvd;
import com.crowvalley.tawelib.model.resource.Laptop;
import com.crowvalley.tawelib.model.resource.Resource;
import com.crowvalley.tawelib.util.FXMLTestUtils;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/spring/applicationContext.xml")
public class UserViewResourceUIT extends ApplicationTest {

    private static final String USER_BROWSE_RESOURCE_FXML = "/fxml/user/userBrowseResources.fxml";

    private static final String BOOK = "Book";

    private static final String DVD = "Dvd";

    private static final String LAPTOP = "Laptop";

    private static final String YEAR = "2020";

    private static final String VIEW_RESOURCE_NODE_QUERY = "#btnViewResource";

    private static final String TITLE_TEXT_FIELD_NODE_QUERY = "#txtTitle";

    private static final String YEAR_TEXT_FIELD_NODE_QUERY = "#txtYear";

    private static final String OPTIONAL_TEXT_FIELD_1_NODE_QUERY = "#txtOptional1";

    private static final String OPTIONAL_TEXT_FIELD_2_NODE_QUERY = "#txtOptional2";

    private static final String OPTIONAL_TEXT_FIELD_3_NODE_QUERY = "#txtOptional3";

    private static final String OPTIONAL_TEXT_FIELD_4_NODE_QUERY = "#txtOptional4";

    private static final String OPTIONAL_TEXT_FIELD_5_NODE_QUERY = "#txtOptional5";

    private static final String OPTIONAL_LABEL_1_NODE_QUERY = "#lblOptional1";

    private static final String OPTIONAL_LABEL_2_NODE_QUERY = "#lblOptional2";

    private static final String OPTIONAL_LABEL_3_NODE_QUERY = "#lblOptional3";

    private static final String OPTIONAL_LABEL_4_NODE_QUERY = "#lblOptional4";

    private static final String OPTIONAL_LABEL_5_NODE_QUERY = "#lblOptional5";

    private static final String AUTHOR_LABEL_TEXT = "Author:";

    private static final String PUBLISHER_LABEL_TEXT = "Publisher:";

    private static final String GENRE_LABEL_TEXT = "Genre:";

    private static final String ISBN_LABEL_TEXT = "ISBN:";

    private static final String LANGUAGE_LABEL_TEXT = "Language:";

    private static final String DIRECTOR_LABEL_TEXT = "Director:";

    private static final String RUNTIME_LABEL_TEXT = "Runtime:";

    private static final String SUBTITLE_LANGUAGE_LABEL_TEXT = "Subtitle Language:";

    private static final String MANUFACTURER_LABEL_TEXT = "Manufacturer:";

    private static final String MODEL_LABEL_TEXT = "Model:";

    private static final String OS_LABEL_TEXT = "Operating System:";

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
    public void testOpenViewResourcePage_Book() {
        openViewResourcePageForResourceWithTitle(BOOK);

        TextField txtOptional1 = lookup(OPTIONAL_TEXT_FIELD_1_NODE_QUERY).queryAs(TextField.class);
        TextField txtOptional2 = lookup(OPTIONAL_TEXT_FIELD_2_NODE_QUERY).queryAs(TextField.class);
        TextField txtOptional3 = lookup(OPTIONAL_TEXT_FIELD_3_NODE_QUERY).queryAs(TextField.class);
        TextField txtOptional4 = lookup(OPTIONAL_TEXT_FIELD_4_NODE_QUERY).queryAs(TextField.class);
        TextField txtOptional5 = lookup(OPTIONAL_TEXT_FIELD_5_NODE_QUERY).queryAs(TextField.class);
        Label lblOptional1 = lookup(OPTIONAL_LABEL_1_NODE_QUERY).queryAs(Label.class);
        Label lblOptional2 = lookup(OPTIONAL_LABEL_2_NODE_QUERY).queryAs(Label.class);
        Label lblOptional3 = lookup(OPTIONAL_LABEL_3_NODE_QUERY).queryAs(Label.class);
        Label lblOptional4 = lookup(OPTIONAL_LABEL_4_NODE_QUERY).queryAs(Label.class);
        Label lblOptional5 = lookup(OPTIONAL_LABEL_5_NODE_QUERY).queryAs(Label.class);

        List<Node> visibleNodes = Arrays.asList(txtOptional1, txtOptional2, txtOptional3, txtOptional4, txtOptional5,
                lblOptional1, lblOptional2, lblOptional3, lblOptional4, lblOptional5);
        List<Node> notVisibleNodes = Collections.emptyList();

        verifyCorrectOptionalNodesAreVisible(BOOK, visibleNodes, notVisibleNodes);
        verifyThat(lblOptional1, hasText(AUTHOR_LABEL_TEXT));
        verifyThat(lblOptional2, hasText(PUBLISHER_LABEL_TEXT));
        verifyThat(lblOptional3, hasText(GENRE_LABEL_TEXT));
        verifyThat(lblOptional4, hasText(ISBN_LABEL_TEXT));
        verifyThat(lblOptional5, hasText(LANGUAGE_LABEL_TEXT));
    }

    @Test
    public void testOpenViewResourcePage_Dvd() {
        openViewResourcePageForResourceWithTitle(DVD);

        TextField txtOptional1 = lookup(OPTIONAL_TEXT_FIELD_1_NODE_QUERY).queryAs(TextField.class);
        TextField txtOptional2 = lookup(OPTIONAL_TEXT_FIELD_2_NODE_QUERY).queryAs(TextField.class);
        TextField txtOptional3 = lookup(OPTIONAL_TEXT_FIELD_3_NODE_QUERY).queryAs(TextField.class);
        TextField txtOptional4 = lookup(OPTIONAL_TEXT_FIELD_4_NODE_QUERY).queryAs(TextField.class);
        TextField txtOptional5 = lookup(OPTIONAL_TEXT_FIELD_5_NODE_QUERY).queryAs(TextField.class);
        Label lblOptional1 = lookup(OPTIONAL_LABEL_1_NODE_QUERY).queryAs(Label.class);
        Label lblOptional2 = lookup(OPTIONAL_LABEL_2_NODE_QUERY).queryAs(Label.class);
        Label lblOptional3 = lookup(OPTIONAL_LABEL_3_NODE_QUERY).queryAs(Label.class);
        Label lblOptional4 = lookup(OPTIONAL_LABEL_4_NODE_QUERY).queryAs(Label.class);
        Label lblOptional5 = lookup(OPTIONAL_LABEL_5_NODE_QUERY).queryAs(Label.class);

        List<Node> visibleNodes = Arrays.asList(txtOptional1, txtOptional2, txtOptional3, txtOptional4,
                lblOptional1, lblOptional2, lblOptional3, lblOptional4);
        List<Node> notVisibleNodes = Arrays.asList(txtOptional5, lblOptional5);

        verifyCorrectOptionalNodesAreVisible(DVD, visibleNodes, notVisibleNodes);
        verifyThat(lblOptional1, hasText(DIRECTOR_LABEL_TEXT));
        verifyThat(lblOptional2, hasText(LANGUAGE_LABEL_TEXT));
        verifyThat(lblOptional3, hasText(RUNTIME_LABEL_TEXT));
        verifyThat(lblOptional4, hasText(SUBTITLE_LANGUAGE_LABEL_TEXT));
    }

    @Test
    public void testOpenViewResourcePage_Laptop() {
        openViewResourcePageForResourceWithTitle(LAPTOP);

        TextField txtOptional1 = lookup(OPTIONAL_TEXT_FIELD_1_NODE_QUERY).queryAs(TextField.class);
        TextField txtOptional2 = lookup(OPTIONAL_TEXT_FIELD_2_NODE_QUERY).queryAs(TextField.class);
        TextField txtOptional3 = lookup(OPTIONAL_TEXT_FIELD_3_NODE_QUERY).queryAs(TextField.class);
        TextField txtOptional4 = lookup(OPTIONAL_TEXT_FIELD_4_NODE_QUERY).queryAs(TextField.class);
        TextField txtOptional5 = lookup(OPTIONAL_TEXT_FIELD_5_NODE_QUERY).queryAs(TextField.class);
        Label lblOptional1 = lookup(OPTIONAL_LABEL_1_NODE_QUERY).queryAs(Label.class);
        Label lblOptional2 = lookup(OPTIONAL_LABEL_2_NODE_QUERY).queryAs(Label.class);
        Label lblOptional3 = lookup(OPTIONAL_LABEL_3_NODE_QUERY).queryAs(Label.class);
        Label lblOptional4 = lookup(OPTIONAL_LABEL_4_NODE_QUERY).queryAs(Label.class);
        Label lblOptional5 = lookup(OPTIONAL_LABEL_5_NODE_QUERY).queryAs(Label.class);

        List<Node> visibleNodes = Arrays.asList(txtOptional1, txtOptional2, txtOptional3,
                lblOptional1, lblOptional2, lblOptional3);
        List<Node> notVisibleNodes = Arrays.asList(txtOptional4, txtOptional5, lblOptional4, lblOptional5);

        verifyCorrectOptionalNodesAreVisible(LAPTOP, visibleNodes, notVisibleNodes);
        verifyThat(lblOptional1, hasText(MANUFACTURER_LABEL_TEXT));
        verifyThat(lblOptional2, hasText(MODEL_LABEL_TEXT));
        verifyThat(lblOptional3, hasText(OS_LABEL_TEXT));
    }

    private void openViewResourcePageForResourceWithTitle(String resourceTitle) {
        verifyThat(VIEW_RESOURCE_NODE_QUERY, NodeMatchers.isDisabled());
        clickOn(resourceTitle);
        verifyThat(VIEW_RESOURCE_NODE_QUERY, NodeMatchers.isEnabled());

        clickOn(VIEW_RESOURCE_NODE_QUERY);
        FXMLTestUtils.verifyWindowShowing(targetWindow(), FXMLTestUtils.USER_VIEW_RESOURCE_WINDOW_ID);

        String titleTextFieldText = lookup(TITLE_TEXT_FIELD_NODE_QUERY).queryAs(TextField.class).getText();
        assertThat(titleTextFieldText)
                .as("View resource page has selected resource's title in title text field")
                .isEqualTo(resourceTitle);

        String yearTextFieldText = lookup(YEAR_TEXT_FIELD_NODE_QUERY).queryAs(TextField.class).getText();
        assertThat(yearTextFieldText)
                .as("View resource page has selected resource's year in year text field")
                .isEqualTo(YEAR);
    }

    private void verifyCorrectOptionalNodesAreVisible(String resourceType, List<Node> visibleTextFields, List<Node> notVisibleTextFields) {
        assertThat(visibleTextFields)
                .as("All required optional text fields are visible (" + resourceType + " requires " + visibleTextFields.size() + " visible text fields)")
                .allMatch(Node::isVisible);

        assertThat(notVisibleTextFields)
                .as("All required optional text fields are not visible (" + resourceType + " requires " + notVisibleTextFields.size() + " non-visible text fields)")
                .allMatch(node -> !node.isVisible());
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
