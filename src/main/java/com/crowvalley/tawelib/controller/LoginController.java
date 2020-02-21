package com.crowvalley.tawelib.controller;

import com.crowvalley.tawelib.UserContext;
import com.crowvalley.tawelib.exception.NoSuchUserException;
import com.crowvalley.tawelib.model.user.Librarian;
import com.crowvalley.tawelib.model.user.User;
import com.crowvalley.tawelib.service.UserService;
import com.crowvalley.tawelib.util.FXMLUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    public static final String LIBRARIAN_HOME_FXML = "/fxml/librarian/librarianHome.fxml";

    public static final String USER_HOME_FXML = "/fxml/userHome.fxml";

    private UserService userService;

    @FXML
    private Button btnLogin;

    @FXML
    private TextField txtUsername;

    public void initialize() {
        btnLogin.setOnAction(e -> tryLogin());
        txtUsername.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                tryLogin();
            }
        });
    }

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
        Optional<? extends User> user = userService.getWithUsername(username);
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
        UserContext.setLoggedInUser(username);
        loadNewScene(LIBRARIAN_HOME_FXML);
    }

    private void userLogIn(String username) {
        UserContext.setLoggedInUser(username);
        loadNewScene(USER_HOME_FXML);
    }

    private void loadNewScene(String fxml) {
        FXMLUtils.loadNewScene(btnLogin, fxml);
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
        LOGGER.info("{} UserService set to {}", this.getClass().getSimpleName(), userService.getClass().getSimpleName());
    }

}
