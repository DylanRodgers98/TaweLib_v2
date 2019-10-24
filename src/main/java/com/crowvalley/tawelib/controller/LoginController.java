package com.crowvalley.tawelib.controller;

import com.crowvalley.tawelib.Main;
import com.crowvalley.tawelib.model.user.Librarian;
import com.crowvalley.tawelib.model.user.User;
import com.crowvalley.tawelib.service.LibrarianService;
import com.crowvalley.tawelib.service.UserService;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Optional;

public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    private static final String LIBRARIAN_HOME_FXML = "/fxml/librarianHome.fxml";

    private static final String USER_HOME_FXML = "/fxml/userHome.fxml";

    @Autowired
    private UserService userService;

    @Autowired
    private LibrarianService librarianService;

    @FXML
    private JFXButton loginButton;

    @FXML
    private TextField txtUsername;

    public LoginController() {
    }

    public void initialize() {
        loginButton.setOnAction(e -> login());
    }

    public void login() {
        String username = txtUsername.getText();
        if (StringUtils.isNumeric(username)) {
            Long staffNum = Long.valueOf(username);
            librarianLogInWithStaffNumber(staffNum);
        } else {
            logInWithUsername(username);
        }
    }

    private void librarianLogInWithStaffNumber(Long staffNum) {
        Optional<Librarian> librarian = librarianService.getWithStaffNumber(staffNum);
        if (librarian.isPresent()) {
            librarianLogIn(librarian.get());
        }
    }

    private void librarianLogInWithUsername(String username) {
        Optional<Librarian> librarian = librarianService.getWithUsername(username);
        if (librarian.isPresent()) {
            librarianLogIn(librarian.get());
        }
    }

    private void logInWithUsername(String username) {
        Optional<Librarian> librarian = librarianService.getWithUsername(username);
        if (librarian.isPresent()) {
            librarianLogInWithUsername(username);
        } else {
            userLogIn(username);
        }
    }

    private void librarianLogIn(Librarian librarian) {
        Main.currentUser = librarian.getUsername();
        loadNewScene(LIBRARIAN_HOME_FXML);
    }

    private void userLogIn(String username) {
        Optional<User> user = userService.get(username);
        if (user.isPresent()) {
            Main.currentUser = username;
            loadNewScene(USER_HOME_FXML);
        }
    }

    private void loadNewScene(String fxml) {
        try {
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.close();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource(fxml)));
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            LOGGER.error("IOException caught when loading page after logging in. Stacktrace as follows: ", e);
        }
    }
}