package com.crowvalley.tawelib.controller;

import com.crowvalley.tawelib.UserContextHolder;
import com.crowvalley.tawelib.dao.UserDAO;
import com.crowvalley.tawelib.model.user.Librarian;
import com.crowvalley.tawelib.model.user.User;
import com.crowvalley.tawelib.util.FXMLUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
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
public class LoginControllerUIT extends ApplicationTest {

    private static final String LOGIN_PAGE_FXML = "/fxml/login.fxml";

    private static final String USER_USERNAME = "TEST_USER";

    private static final String LIBRARIAN_USERNAME = "TEST_LIBRARIAN";

    private static final String NONEXISTENT_USER_USERNAME = "NONEXISTENT_USER";

    private static final String NONEXISTENT_LIBRARIAN_STAFF_NUMBER = "0";

    @Autowired
    private UserDAO userDAO;

    private User user;

    private Librarian librarian;

    @Before
    public void setUp() {
        user = new User();
        user.setUsername(USER_USERNAME);
        userDAO.saveOrUpdate(user);

        librarian = new Librarian();
        librarian.setUsername(LIBRARIAN_USERNAME);
        userDAO.saveOrUpdate(librarian);
    }

    @After
    public void tearDown() {
        UserContextHolder.clear();
        userDAO.delete(user);
        userDAO.delete(librarian);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = FXMLUtils.prepareFXMLLoader(LOGIN_PAGE_FXML);
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void testLogIn_User_ClickLogInButton() {
        logInByClickingButton(USER_USERNAME);

        String username = UserContextHolder.getLoggedInUser();
        assertThat(username).isEqualTo(USER_USERNAME);
    }

    @Test
    public void testLogIn_User_PressEnter() {
        logInByPressingEnter(USER_USERNAME);

        String username = UserContextHolder.getLoggedInUser();
        assertThat(username).isEqualTo(USER_USERNAME);
    }

    @Test
    public void testLogIn_LibrarianUsername_ClickLogInButton() {
        logInByClickingButton(LIBRARIAN_USERNAME);

        String username = UserContextHolder.getLoggedInUser();
        assertThat(username).isEqualTo(LIBRARIAN_USERNAME);
    }

    @Test
    public void testLogIn_LibrarianUsername_PressEnter() {
        logInByPressingEnter(LIBRARIAN_USERNAME);

        String username = UserContextHolder.getLoggedInUser();
        assertThat(username).isEqualTo(LIBRARIAN_USERNAME);
    }

    @Test
    public void testLogIn_LibrarianStaffNumber_ClickLogInButton() {
        logInByClickingButton(String.valueOf(librarian.getStaffNum()));

        String username = UserContextHolder.getLoggedInUser();
        assertThat(username).isEqualTo(LIBRARIAN_USERNAME);
    }

    @Test
    public void testLogIn_LibrarianStaffNumber_PressEnter() {
        logInByPressingEnter(String.valueOf(librarian.getStaffNum()));

        String username = UserContextHolder.getLoggedInUser();
        assertThat(username).isEqualTo(LIBRARIAN_USERNAME);
    }

    @Test
    public void testLogIn_NonexistentUser_ClickLogInButton() {
        logInByClickingButton(NONEXISTENT_USER_USERNAME);

        String username = UserContextHolder.getLoggedInUser();
        assertThat(username).isNull();
    }

    @Test
    public void testLogIn_NonexistentUser_PressEnter() {
        logInByPressingEnter(NONEXISTENT_USER_USERNAME);

        String username = UserContextHolder.getLoggedInUser();
        assertThat(username).isNull();
    }

    @Test
    public void testLogIn_NonexistentLibrarian_ClickLogInButton() {
        logInByClickingButton(NONEXISTENT_LIBRARIAN_STAFF_NUMBER);

        String username = UserContextHolder.getLoggedInUser();
        assertThat(username).isNull();
    }

    @Test
    public void testLogIn_NonexistentLibrarian_PressEnter() {
        logInByPressingEnter(NONEXISTENT_LIBRARIAN_STAFF_NUMBER);

        String username = UserContextHolder.getLoggedInUser();
        assertThat(username).isNull();
    }

    private void logInByClickingButton(String text) {
        typeIntoUsernameTextField(text);
        clickOn("#btnLogIn");
    }

    private void logInByPressingEnter(String text) {
        typeIntoUsernameTextField(text);
        push(KeyCode.ENTER);
    }

    private void typeIntoUsernameTextField(String text) {
        clickOn("#txtUsername");
        write(text);
    }

}
