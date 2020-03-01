package com.crowvalley.tawelib.controller.librarian;

import com.crowvalley.tawelib.Main;
import com.crowvalley.tawelib.UserContextHolder;
import com.crowvalley.tawelib.controller.FXController;
import com.crowvalley.tawelib.util.FXMLUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class LibrarianLogOutController implements FXController {

    @FXML
    private Button btnLogOut;

    @Override
    public void initialize() {
        btnLogOut.setOnAction(e -> logOut());
    }

    private void logOut() {
        UserContextHolder.clear();
        FXMLUtils.loadNewScene(btnLogOut, Main.LOGIN_PAGE_FXML);
    }

}
