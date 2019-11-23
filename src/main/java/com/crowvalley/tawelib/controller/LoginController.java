package com.crowvalley.tawelib.controller;

import com.crowvalley.tawelib.Main;
import com.crowvalley.tawelib.exception.NoSuchUserException;
import com.crowvalley.tawelib.model.user.Librarian;
import com.crowvalley.tawelib.model.user.User;
import com.crowvalley.tawelib.service.LibrarianService;
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

    private LibrarianService librarianService;

    @FXML
    private Button btnLogin;

    @FXML
    private TextField txtUsername;

    public LoginController() {
    }

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
        Optional<Librarian> librarian = librarianService.getWithStaffNumber(staffNum);
        if (librarian.isPresent()) {
            librarianLogIn(librarian.get());
        } else {
            throw new NoSuchUserException("No librarian user found with staff number '" + staffNum + "'");
        }
    }

    private void logInWithUsername(String username) throws NoSuchUserException {
        Optional<Librarian> librarian = librarianService.getWithUsername(username);
        if (librarian.isPresent()) {
            librarianLogIn(username);
        } else {
            userLogIn(username);
        }
    }

    private void librarianLogIn(Librarian librarian) {
        librarianLogIn(librarian.getUsername());
    }

    private void librarianLogIn(String username) {
        Main.currentUser = username;
        loadNewScene(LIBRARIAN_HOME_FXML);
    }

    private void userLogIn(String username) throws NoSuchUserException {
        Optional<User> user = userService.get(username);
        if (user.isPresent()) {
            Main.currentUser = username;
            loadNewScene(USER_HOME_FXML);
        } else {
            throw new NoSuchUserException("No user found with username '" + username + "'");
        }
    }

    private void loadNewScene(String fxml) {
        FXMLUtils.loadNewScene(btnLogin, fxml);
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
        LOGGER.info("UserService for LoginController set to {}", userService.getClass());
    }

    public void setLibrarianService(LibrarianService librarianService) {
        this.librarianService = librarianService;
        LOGGER.info("LibrarianService for LoginController set to {}", librarianService.getClass());
    }
}
