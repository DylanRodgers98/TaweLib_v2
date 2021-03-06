package com.crowvalley.tawelib.controller;

import com.crowvalley.tawelib.UserContextHolder;
import com.crowvalley.tawelib.exception.NoSuchUserException;
import com.crowvalley.tawelib.model.user.Librarian;
import com.crowvalley.tawelib.model.user.User;
import com.crowvalley.tawelib.service.UserService;
import com.crowvalley.tawelib.util.FXMLUtils;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class LoginController implements FXController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    public static final String LIBRARIAN_HOME_FXML = "/fxml/librarian/librarianHome.fxml";

    public static final String USER_HOME_FXML = "/fxml/user/userHome.fxml";

    private UserService userService;

    @FXML
    private TextField txtUsername;

    @FXML
    private void loginIfEnterPressed(KeyEvent e) {
        if (e.getCode().equals(KeyCode.ENTER)) {
            tryLogin();
        }
    }

    @FXML
    private void tryLogin() {
        try {
            login();
        } catch (NoSuchUserException ex) {
            LOGGER.error("NoSuchUserException caught when trying to log in", ex);
            FXMLUtils.displayErrorDialogBox("Error Logging In", ex.getMessage());
        }
    }

    private void login() throws NoSuchUserException {
        String username = txtUsername.getText();
        if (StringUtils.isNumeric(username)) {
            Long staffNum = Long.valueOf(username);
            logInWithStaffNumber(staffNum);
        } else {
            logInWithUsername(username);
        }
    }

    private void logInWithStaffNumber(Long staffNum) throws NoSuchUserException {
        Optional<Librarian> librarian = userService.getLibrarianUserWithStaffNumber(staffNum);
        if (librarian.isPresent()) {
            librarianLogIn(librarian.get().getUsername());
        } else {
            throw new NoSuchUserException("No librarian user found with staff number '" + staffNum + "'");
        }
    }

    private void logInWithUsername(String username) throws NoSuchUserException {
        Optional<User> user = userService.getWithUsername(username);
        if (user.isPresent()) {
            if (user.get() instanceof Librarian) {
                librarianLogIn(username);
            } else {
                userLogIn(username);
            }
        } else {
            throw new NoSuchUserException("No user found with username '" + username + "'");
        }
    }

    private void librarianLogIn(String username) {
        UserContextHolder.setLoggedInUser(username);
        FXMLUtils.loadNewScene(LIBRARIAN_HOME_FXML);
    }

    private void userLogIn(String username) {
        UserContextHolder.setLoggedInUser(username);
        FXMLUtils.loadNewScene(USER_HOME_FXML);
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
        LOGGER.info("{} UserService set to {}", this.getClass().getSimpleName(), userService.getClass().getSimpleName());
    }

}
