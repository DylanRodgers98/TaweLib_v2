package com.crowvalley.tawelib.controller.user;

import com.crowvalley.tawelib.dao.BaseDAO;
import com.crowvalley.tawelib.model.resource.*;
import com.crowvalley.tawelib.service.LoanService;
import com.crowvalley.tawelib.util.FXMLTestUtils;
import com.google.common.collect.Sets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.ResourceUtils;
import org.testfx.framework.junit.ApplicationTest;

import java.io.IOException;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.control.TableViewMatchers.containsRow;
import static org.testfx.util.NodeQueryUtils.hasText;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/spring/applicationContext.xml")
public class UserViewResourceUIT extends ApplicationTest {

    private static final String USER_BROWSE_RESOURCE_FXML = "/fxml/user/userBrowseResources.fxml";

    private static final String BOOK_TITLE = "Book";

    private static final String BOOK_YEAR = "2020";

    private static final String BOOK_IMAGE_URL = "book.png";

    private static final String BOOK_AUTHOR = "Some Author";

    private static final String BOOK_PUBLISHER = "Some Publisher";

    private static final String BOOK_GENRE = "Some Genre";

    private static final String BOOK_ISBN = "12345678";

    private static final String ENGLISH_LANGUAGE = "English";

    private static final String BOOK_LANGUAGE = ENGLISH_LANGUAGE;

    private static final String DVD_TITLE = "Dvd";

    private static final String DVD_YEAR = "2019";

    private static final String DVD_IMAGE_URL = "dvd.jpg";

    private static final String DVD_DIRECTOR = "Some Director";

    private static final String SPANISH_LANGUAGE = "Spanish";

    private static final String DVD_LANGUAGE = SPANISH_LANGUAGE;

    private static final Integer DVD_RUNTIME = 120;

    private static final Set<String> DVD_SUBTITLE_LANGUAGES = Sets.newHashSet(ENGLISH_LANGUAGE, SPANISH_LANGUAGE);

    private static final String LAPTOP_TITLE = "Laptop";

    private static final String LAPTOP_YEAR = "2018";

    private static final String LAPTOP_IMAGE_URL = "laptop.jpg";

    private static final String LAPTOP_MANUFACTURER = "Some Manufacturer";

    private static final String LAPTOP_MODEL = "Some Model";

    private static final String LAPTOP_OS = "Windows 10";

    private static final String VIEW_RESOURCE_NODE_QUERY = "#btnViewResource";

    private static final String TITLE_TEXT_FIELD_NODE_QUERY = "#txtTitle";

    private static final String YEAR_TEXT_FIELD_NODE_QUERY = "#txtYear";

    private static final String OPTIONAL_TEXT_FIELD_1_NODE_QUERY = "#txtOptional1";

    private static final String OPTIONAL_TEXT_FIELD_2_NODE_QUERY = "#txtOptional2";

    private static final String OPTIONAL_TEXT_FIELD_3_NODE_QUERY = "#txtOptional3";

    private static final String OPTIONAL_TEXT_FIELD_4_NODE_QUERY = "#txtOptional4";

    private static final String OPTIONAL_TEXT_FIELD_5_NODE_QUERY = "#txtOptional5";

    private static final String IMAGE_VIEW_NODE_QUERY = "#imgResourcePic";

    private static final String TITLE_LABEL_NODE_QUERY = "#lblTitle";

    private static final String YEAR_LABEL_NODE_QUERY = "#lblYear";

    private static final String OPTIONAL_LABEL_1_NODE_QUERY = "#lblOptional1";

    private static final String OPTIONAL_LABEL_2_NODE_QUERY = "#lblOptional2";

    private static final String OPTIONAL_LABEL_3_NODE_QUERY = "#lblOptional3";

    private static final String OPTIONAL_LABEL_4_NODE_QUERY = "#lblOptional4";

    private static final String OPTIONAL_LABEL_5_NODE_QUERY = "#lblOptional5";

    private static final String COPIES_TABLE_NODE_QUERY = "#tblCopies";

    private static final String BACK_BUTTON_NODE_QUERY = "#btnBack";

    private static final String TITLE_LABEL_TEXT = "Title:";

    private static final String YEAR_LABEL_TEXT = "Year:";

    private static final String AUTHOR_LABEL_TEXT = "Author:";

    private static final String PUBLISHER_LABEL_TEXT = "Publisher:";

    private static final String GENRE_LABEL_TEXT = "Genre:";

    private static final String ISBN_LABEL_TEXT = "ISBN:";

    private static final String LANGUAGE_LABEL_TEXT = "Language:";

    private static final String DIRECTOR_LABEL_TEXT = "Director:";

    private static final String RUNTIME_LABEL_TEXT = "Runtime:";

    private static final String SUBTITLE_LANGUAGES_LABEL_TEXT = "Subtitle Languages:";

    private static final String MANUFACTURER_LABEL_TEXT = "Manufacturer:";

    private static final String MODEL_LABEL_TEXT = "Model:";

    private static final String OS_LABEL_TEXT = "Operating System:";

    private static final Integer LOAN_DURATION_FOR_COPY_OF_BOOK = 2;

    private static final String LOCATION_OF_COPY_OF_BOOK = "Shelf A";

    private static final Integer LOAN_DURATION_FOR_COPY_OF_DVD = 4;

    private static final String LOCATION_OF_COPY_OF_DVD = "Shelf B";

    private static final Integer LOAN_DURATION_FOR_COPY_OF_LAPTOP = 7;

    private static final String LOCATION_OF_COPY_OF_LAPTOP = "Shelf C";

    @Autowired
    private BaseDAO baseDAO;

    private Book book;

    private Dvd dvd;

    private Laptop laptop;

    private Copy copyOfBook;

    private Copy copyOfDvd;

    private Copy copyOfLaptop;

    @After
    public void tearDown() {
        baseDAO.delete(book);
        baseDAO.delete(dvd);
        baseDAO.delete(laptop);
    }

    @Override
    public void start(Stage stage) throws IllegalAccessException, InstantiationException {
        createResources();
        FXMLTestUtils.initTest(stage, USER_BROWSE_RESOURCE_FXML);
    }

    @Test
    public void testOpenViewResourcePage_Book() throws IOException {
        openViewResourcePageForResource(book);

        Label lblTitle = lookup(TITLE_LABEL_NODE_QUERY).queryAs(Label.class);
        Label lblYear = lookup(YEAR_LABEL_NODE_QUERY).queryAs(Label.class);
        Label lblOptional1 = lookup(OPTIONAL_LABEL_1_NODE_QUERY).queryAs(Label.class);
        Label lblOptional2 = lookup(OPTIONAL_LABEL_2_NODE_QUERY).queryAs(Label.class);
        Label lblOptional3 = lookup(OPTIONAL_LABEL_3_NODE_QUERY).queryAs(Label.class);
        Label lblOptional4 = lookup(OPTIONAL_LABEL_4_NODE_QUERY).queryAs(Label.class);
        Label lblOptional5 = lookup(OPTIONAL_LABEL_5_NODE_QUERY).queryAs(Label.class);
        TextField txtTitle = lookup(TITLE_TEXT_FIELD_NODE_QUERY).queryAs(TextField.class);
        TextField txtYear = lookup(YEAR_TEXT_FIELD_NODE_QUERY).queryAs(TextField.class);
        TextField txtOptional1 = lookup(OPTIONAL_TEXT_FIELD_1_NODE_QUERY).queryAs(TextField.class);
        TextField txtOptional2 = lookup(OPTIONAL_TEXT_FIELD_2_NODE_QUERY).queryAs(TextField.class);
        TextField txtOptional3 = lookup(OPTIONAL_TEXT_FIELD_3_NODE_QUERY).queryAs(TextField.class);
        TextField txtOptional4 = lookup(OPTIONAL_TEXT_FIELD_4_NODE_QUERY).queryAs(TextField.class);
        TextField txtOptional5 = lookup(OPTIONAL_TEXT_FIELD_5_NODE_QUERY).queryAs(TextField.class);

        List<Node> visibleNodes = Arrays.asList(txtOptional1, txtOptional2, txtOptional3, txtOptional4,
                txtOptional5, lblOptional1, lblOptional2, lblOptional3, lblOptional4, lblOptional5);
        List<Node> notVisibleNodes = Collections.emptyList();

        verifyCorrectOptionalNodesAreVisible(book.getClass().getSimpleName(), visibleNodes, notVisibleNodes);

        verifyThat(lblTitle, hasText(TITLE_LABEL_TEXT));
        verifyThat(txtTitle, hasText(BOOK_TITLE));
        verifyThat(lblYear, hasText(YEAR_LABEL_TEXT));
        verifyThat(txtYear, hasText(BOOK_YEAR));
        verifyThat(lblOptional1, hasText(AUTHOR_LABEL_TEXT));
        verifyThat(txtOptional1, hasText(BOOK_AUTHOR));
        verifyThat(lblOptional2, hasText(PUBLISHER_LABEL_TEXT));
        verifyThat(txtOptional2, hasText(BOOK_PUBLISHER));
        verifyThat(lblOptional3, hasText(GENRE_LABEL_TEXT));
        verifyThat(txtOptional3, hasText(BOOK_GENRE));
        verifyThat(lblOptional4, hasText(ISBN_LABEL_TEXT));
        verifyThat(txtOptional4, hasText(BOOK_ISBN));
        verifyThat(lblOptional5, hasText(LANGUAGE_LABEL_TEXT));
        verifyThat(txtOptional5, hasText(BOOK_LANGUAGE));
        verifyThatImageIsDisplayed(BOOK_IMAGE_URL, book.getClass().getSimpleName());
        verifyThatCopiesAreDisplayedInTable(copyOfBook);
    }

    @Test
    public void testOpenViewResourcePage_Dvd() throws IOException {
        openViewResourcePageForResource(dvd);

        Label lblTitle = lookup(TITLE_LABEL_NODE_QUERY).queryAs(Label.class);
        Label lblYear = lookup(YEAR_LABEL_NODE_QUERY).queryAs(Label.class);
        Label lblOptional1 = lookup(OPTIONAL_LABEL_1_NODE_QUERY).queryAs(Label.class);
        Label lblOptional2 = lookup(OPTIONAL_LABEL_2_NODE_QUERY).queryAs(Label.class);
        Label lblOptional3 = lookup(OPTIONAL_LABEL_3_NODE_QUERY).queryAs(Label.class);
        Label lblOptional4 = lookup(OPTIONAL_LABEL_4_NODE_QUERY).queryAs(Label.class);
        Label lblOptional5 = lookup(OPTIONAL_LABEL_5_NODE_QUERY).queryAs(Label.class);
        TextField txtTitle = lookup(TITLE_TEXT_FIELD_NODE_QUERY).queryAs(TextField.class);
        TextField txtYear = lookup(YEAR_TEXT_FIELD_NODE_QUERY).queryAs(TextField.class);
        TextField txtOptional1 = lookup(OPTIONAL_TEXT_FIELD_1_NODE_QUERY).queryAs(TextField.class);
        TextField txtOptional2 = lookup(OPTIONAL_TEXT_FIELD_2_NODE_QUERY).queryAs(TextField.class);
        TextField txtOptional3 = lookup(OPTIONAL_TEXT_FIELD_3_NODE_QUERY).queryAs(TextField.class);
        TextField txtOptional4 = lookup(OPTIONAL_TEXT_FIELD_4_NODE_QUERY).queryAs(TextField.class);
        TextField txtOptional5 = lookup(OPTIONAL_TEXT_FIELD_5_NODE_QUERY).queryAs(TextField.class);

        List<Node> visibleNodes = Arrays.asList(txtOptional1, txtOptional2, txtOptional3, txtOptional4,
                lblOptional1, lblOptional2, lblOptional3, lblOptional4);
        List<Node> notVisibleNodes = Arrays.asList(txtOptional5, lblOptional5);

        verifyCorrectOptionalNodesAreVisible(dvd.getClass().getSimpleName(), visibleNodes, notVisibleNodes);

        verifyThat(lblTitle, hasText(TITLE_LABEL_TEXT));
        verifyThat(txtTitle, hasText(DVD_TITLE));
        verifyThat(lblYear, hasText(YEAR_LABEL_TEXT));
        verifyThat(txtYear, hasText(DVD_YEAR));
        verifyThat(lblOptional1, hasText(DIRECTOR_LABEL_TEXT));
        verifyThat(txtOptional1, hasText(DVD_DIRECTOR));
        verifyThat(lblOptional2, hasText(LANGUAGE_LABEL_TEXT));
        verifyThat(txtOptional2, hasText(DVD_LANGUAGE));
        verifyThat(lblOptional3, hasText(RUNTIME_LABEL_TEXT));
        verifyThat(txtOptional3, hasText(DVD_RUNTIME.toString()));
        verifyThat(lblOptional4, hasText(SUBTITLE_LANGUAGES_LABEL_TEXT));
        verifyThat(txtOptional4, hasText(dvd.getSubtitleLanguageString()));
        verifyThatImageIsDisplayed(DVD_IMAGE_URL, dvd.getClass().getSimpleName());
        verifyThatCopiesAreDisplayedInTable(copyOfDvd);
    }

    @Test
    public void testOpenViewResourcePage_Laptop() throws IOException {
        openViewResourcePageForResource(laptop);

        Label lblTitle = lookup(TITLE_LABEL_NODE_QUERY).queryAs(Label.class);
        Label lblYear = lookup(YEAR_LABEL_NODE_QUERY).queryAs(Label.class);
        Label lblOptional1 = lookup(OPTIONAL_LABEL_1_NODE_QUERY).queryAs(Label.class);
        Label lblOptional2 = lookup(OPTIONAL_LABEL_2_NODE_QUERY).queryAs(Label.class);
        Label lblOptional3 = lookup(OPTIONAL_LABEL_3_NODE_QUERY).queryAs(Label.class);
        Label lblOptional4 = lookup(OPTIONAL_LABEL_4_NODE_QUERY).queryAs(Label.class);
        Label lblOptional5 = lookup(OPTIONAL_LABEL_5_NODE_QUERY).queryAs(Label.class);
        TextField txtTitle = lookup(TITLE_TEXT_FIELD_NODE_QUERY).queryAs(TextField.class);
        TextField txtYear = lookup(YEAR_TEXT_FIELD_NODE_QUERY).queryAs(TextField.class);
        TextField txtOptional1 = lookup(OPTIONAL_TEXT_FIELD_1_NODE_QUERY).queryAs(TextField.class);
        TextField txtOptional2 = lookup(OPTIONAL_TEXT_FIELD_2_NODE_QUERY).queryAs(TextField.class);
        TextField txtOptional3 = lookup(OPTIONAL_TEXT_FIELD_3_NODE_QUERY).queryAs(TextField.class);
        TextField txtOptional4 = lookup(OPTIONAL_TEXT_FIELD_4_NODE_QUERY).queryAs(TextField.class);
        TextField txtOptional5 = lookup(OPTIONAL_TEXT_FIELD_5_NODE_QUERY).queryAs(TextField.class);

        List<Node> visibleNodes = Arrays.asList(txtOptional1, txtOptional2, txtOptional3,
                lblOptional1, lblOptional2, lblOptional3);
        List<Node> notVisibleNodes = Arrays.asList(txtOptional4, txtOptional5, lblOptional4, lblOptional5);

        verifyCorrectOptionalNodesAreVisible(laptop.getClass().getSimpleName(), visibleNodes, notVisibleNodes);

        verifyThat(lblTitle, hasText(TITLE_LABEL_TEXT));
        verifyThat(txtTitle, hasText(LAPTOP_TITLE));
        verifyThat(lblYear, hasText(YEAR_LABEL_TEXT));
        verifyThat(txtYear, hasText(LAPTOP_YEAR));
        verifyThat(lblOptional1, hasText(MANUFACTURER_LABEL_TEXT));
        verifyThat(txtOptional1, hasText(LAPTOP_MANUFACTURER));
        verifyThat(lblOptional2, hasText(MODEL_LABEL_TEXT));
        verifyThat(txtOptional2, hasText(LAPTOP_MODEL));
        verifyThat(lblOptional3, hasText(OS_LABEL_TEXT));
        verifyThat(txtOptional3, hasText(LAPTOP_OS));
        verifyThatImageIsDisplayed(LAPTOP_IMAGE_URL, laptop.getClass().getSimpleName());
        verifyThatCopiesAreDisplayedInTable(copyOfLaptop);
    }

    @Test
    public void testGoBack() {
        openViewResourcePageForResource(book);
        clickOn(BACK_BUTTON_NODE_QUERY);
        FXMLTestUtils.verifyWindowShowing(targetWindow(), FXMLTestUtils.USER_BROWSE_RESOURCES_WINDOW_ID);
    }

    private void openViewResourcePageForResource(Resource resource) {
        verifyThat(VIEW_RESOURCE_NODE_QUERY, isDisabled());
        clickOn(resource.getTitle());
        verifyThat(VIEW_RESOURCE_NODE_QUERY, isEnabled());

        clickOn(VIEW_RESOURCE_NODE_QUERY);
        FXMLTestUtils.verifyWindowShowing(targetWindow(), FXMLTestUtils.USER_VIEW_RESOURCE_WINDOW_ID);

        String titleTextFieldText = lookup(TITLE_TEXT_FIELD_NODE_QUERY).queryAs(TextField.class).getText();
        assertThat(titleTextFieldText)
                .as("View resource page has selected resource's title in title text field")
                .isEqualTo(resource.getTitle());

        String yearTextFieldText = lookup(YEAR_TEXT_FIELD_NODE_QUERY).queryAs(TextField.class).getText();
        assertThat(yearTextFieldText)
                .as("View resource page has selected resource's year in year text field")
                .isEqualTo(resource.getYear());
    }

    private void verifyCorrectOptionalNodesAreVisible(String resourceTypeName, List<Node> visibleTextFields, List<Node> notVisibleTextFields) {
        assertThat(visibleTextFields)
                .as("All required optional text fields are visible (" + resourceTypeName + " requires " + visibleTextFields.size() + " visible text fields)")
                .allMatch(Node::isVisible);

        assertThat(notVisibleTextFields)
                .as("All required optional text fields are not visible (" + resourceTypeName + " requires " + notVisibleTextFields.size() + " non-visible text fields)")
                .allMatch(node -> !node.isVisible());
    }

    private void verifyThatImageIsDisplayed(String expectedImageUrl, String entityName) throws IOException {
        ImageView imgResourcePic = lookup(IMAGE_VIEW_NODE_QUERY).queryAs(ImageView.class);
        Image image = imgResourcePic.getImage();

        assertThat(image)
                .as("Image for " + entityName + " to be displayed in image view is not null")
                .isNotNull();

        String expectedPath = new ClassPathResource(expectedImageUrl).getFile().getCanonicalPath();
        String actualPath = image.getUrl()
                .substring(ResourceUtils.FILE_URL_PREFIX.length() + 1)
                .replace('/', '\\')
                .replace("%20", StringUtils.SPACE);

        assertThat(actualPath)
                .as("Resource ImageView image URL is equal to the one set on " + entityName + " entity")
                .isEqualTo(expectedPath);
    }

    private void verifyThatCopiesAreDisplayedInTable(Copy... copies) {
        TableView<Copy> tblCopies = lookup(COPIES_TABLE_NODE_QUERY).queryTableView();
        for (Copy copy : copies) {
            verifyThat(tblCopies, containsRow(
                    copy.getLoanDurationAsDays() + " days",
                    copy.getLocation(),
                    copy.getCopyRequests().size(),
                    Loan.Status.AVAILABLE.toString()));
        }
    }

    private void createResources() {
        book = new Book(BOOK_TITLE, BOOK_YEAR, BOOK_IMAGE_URL, BOOK_AUTHOR,
                BOOK_PUBLISHER, BOOK_GENRE, BOOK_ISBN, BOOK_LANGUAGE);
        copyOfBook = new Copy(book, LOAN_DURATION_FOR_COPY_OF_BOOK, LOCATION_OF_COPY_OF_BOOK);
        book.getCopies().add(copyOfBook);
        baseDAO.saveOrUpdate(book);

        dvd = new Dvd(DVD_TITLE, DVD_YEAR, DVD_IMAGE_URL, DVD_DIRECTOR,
                DVD_LANGUAGE, DVD_RUNTIME, DVD_SUBTITLE_LANGUAGES);
        copyOfDvd = new Copy(dvd, LOAN_DURATION_FOR_COPY_OF_DVD, LOCATION_OF_COPY_OF_DVD);
        dvd.getCopies().add(copyOfDvd);
        baseDAO.saveOrUpdate(dvd);

        laptop = new Laptop(LAPTOP_TITLE, LAPTOP_YEAR, LAPTOP_IMAGE_URL,
                LAPTOP_MANUFACTURER, LAPTOP_MODEL, LAPTOP_OS);
        copyOfLaptop = new Copy(laptop, LOAN_DURATION_FOR_COPY_OF_LAPTOP, LOCATION_OF_COPY_OF_LAPTOP);
        laptop.getCopies().add(copyOfLaptop);
        baseDAO.saveOrUpdate(laptop);
    }

}
