package com.crowvalley.tawelib.controller;

import com.crowvalley.tawelib.Main;
import com.crowvalley.tawelib.util.FXMLUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.apache.commons.lang3.StringUtils;

public class LibrarianHomeController {

    @FXML
    private Button btnLogOut;

    public LibrarianHomeController() {
    }

    public void initialize() {
        btnLogOut.setOnAction(e -> logOut());
    }

    private void logOut() {
        Main.currentUser = StringUtils.EMPTY;
        FXMLUtils.loadNewScene(btnLogOut, Main.LOGIN_PAGE_FXML);
    }
}
