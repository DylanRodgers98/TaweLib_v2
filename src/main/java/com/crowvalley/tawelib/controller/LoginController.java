package com.crowvalley.tawelib.controller;

import com.crowvalley.tawelib.Main;
import com.crowvalley.tawelib.model.user.Librarian;
import com.crowvalley.tawelib.model.user.User;
import com.crowvalley.tawelib.service.LibrarianService;
import com.crowvalley.tawelib.service.UserService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.Optional;

public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    public static final String TAWELIB = "TaweLib";

    public static final String LOGIN_PAGE_FXML = "/fxml/login.fxml";

    public static final String LIBRARIAN_HOME_FXML = "/fxml/librarianHome.fxml";

    public static final String USER_HOME_FXML = "/fxml/userHome.fxml";

    @Autowired
    private UserService userService;

    @Autowired
    private LibrarianService librarianService;

    @FXML
    private Button btnLogin;

    @FXML
    private TextField txtUsername;

    public LoginController() {
    }

    public void showLoginPage(ApplicationContext applicationContext, Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(LOGIN_PAGE_FXML));
        loader.setControllerFactory(applicationContext::getBean);
        primaryStage.setScene(new Scene(loader.load()));
        primaryStage.setTitle(TAWELIB);
        primaryStage.show();
    }

    public void initialize() {
        btnLogin.setOnAction(e -> login());
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
            Stage stage = (Stage) btnLogin.getScene().getWindow();
            stage.close();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource(fxml)));
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            LOGGER.error("IOException caught when loading page after logging in. Stacktrace as follows: ", e);
        }
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
