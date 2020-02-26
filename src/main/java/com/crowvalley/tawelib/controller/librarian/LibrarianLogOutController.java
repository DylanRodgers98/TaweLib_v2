package com.crowvalley.tawelib.controller.librarian;

import com.crowvalley.tawelib.Main;
import com.crowvalley.tawelib.UserContext;
import com.crowvalley.tawelib.controller.FXController;
import com.crowvalley.tawelib.util.FXMLUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class LibrarianLogOutController implements FXController {

    @FXML
    private Button btnLogOut;

    public void initialize() {
        btnLogOut.setOnAction(e -> logOut());
    }

    private void logOut() {
        UserContext.clear();
        FXMLUtils.loadNewScene(btnLogOut, Main.LOGIN_PAGE_FXML);
    }

}
