package com.crowvalley.tawelib.controller.user;

import com.crowvalley.tawelib.UserContextHolder;
import com.crowvalley.tawelib.dao.UserDAO;
import com.crowvalley.tawelib.model.user.User;
import com.crowvalley.tawelib.util.FXMLTestUtils;
import javafx.scene.Node;
import javafx.scene.control.DialogPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.testfx.framework.junit.ApplicationTest;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/spring/applicationContext.xml")
public class UserHomeUIT extends ApplicationTest {

    private static final String USER_HOME_FXML = "/fxml/user/userHome.fxml";

    private static final String USER_USERNAME = "TEST_USER";

    private static final String NONEXISTENT_USER_USERNAME = "NONEXISTENT_USER";

    private static final String VIEW_RESOURCES_BUTTON_NODE_QUERY = "#btnViewResources";

    private static final String VIEW_LOANS_BUTTON_NODE_QUERY = "#btnViewLoans";

    private static final String VIEW_PROFILE_BUTTON_NODE_QUERY = "#btnViewProfile";

    private static final String FINES_AND_PAYMENTS_BUTTON_NODE_QUERY = "#btnFinesAndPayments";

    private static final String LOG_OUT_LABEL_NODE_QUERY = "#lblLogOut";

    @Autowired
    private UserDAO userDAO;

    private User user;

    @Before
    public void setUp() {
        user = new User(USER_USERNAME, "", "", "", "", "", "", "", "", "");
        userDAO.saveOrUpdate(user);
        UserContextHolder.setLoggedInUser(USER_USERNAME);
    }

    @After
    public void tearDown() {
        UserContextHolder.clear();
        userDAO.delete(user);
    }

    @Override
    public void start(Stage stage) throws IllegalAccessException, InstantiationException {
        FXMLTestUtils.initTest(stage, USER_HOME_FXML);
    }

    @Test
    public void testOpenBrowseResourcesPage() {
        clickOn(VIEW_RESOURCES_BUTTON_NODE_QUERY);
        verifyWindowShowing(FXMLTestUtils.USER_BROWSE_RESOURCES_WINDOW_ID);
    }

    @Test
    public void testOpenViewLoansPage() {
        clickOn(VIEW_LOANS_BUTTON_NODE_QUERY);
        verifyWindowShowing(FXMLTestUtils.USER_LOANS_WINDOW_ID);
    }

    @Test
    public void testOpenFinesAndPaymentsPage() {
        clickOn(FINES_AND_PAYMENTS_BUTTON_NODE_QUERY);
        verifyWindowShowing(FXMLTestUtils.USER_FINES_AND_PAYMENTS_WINDOW_ID);
    }

    @Test
    public void testLogOut() {
        clickOn(LOG_OUT_LABEL_NODE_QUERY);
        verifyWindowShowing(FXMLTestUtils.LOGIN_PAGE_WINDOW_ID);
    }

    @Test
    public void testOpenViewProfilePage() {
        clickOn(VIEW_PROFILE_BUTTON_NODE_QUERY);
        verifyWindowShowing(FXMLTestUtils.USER_PROFILE_WINDOW_ID);
    }

    @Test
    public void testOpenViewProfilePage_ErrorLoadingUserFromDatabase() {
        UserContextHolder.clear();
        UserContextHolder.setLoggedInUser(NONEXISTENT_USER_USERNAME);

        clickOn(VIEW_PROFILE_BUTTON_NODE_QUERY);

        Object root = listWindows().get(1).getScene().getRoot(); // get second window ([0] = main window, [1] = dialog box)

        assertThat(root)
                .as("Second displayed window is a DialogPane")
                .isInstanceOf(DialogPane.class);

        DialogPane dialogPane = (DialogPane) root;
        Node dialogPaneContent = dialogPane.getContent();

        assertThat(dialogPaneContent)
                .as("DialogPane's content is Text")
                .isInstanceOf(Text.class);

        Text text = (Text) dialogPaneContent;

        assertThat(text.getText())
                .as("DialogPane displays correct error text")
                .isEqualTo("Could not load User '" + NONEXISTENT_USER_USERNAME + "' from database");
    }

    private void verifyWindowShowing(String expectedWindowId) {
        FXMLTestUtils.verifyWindowShowing(targetWindow(), expectedWindowId);
    }

}
