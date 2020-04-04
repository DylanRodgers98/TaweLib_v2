package com.crowvalley.tawelib.controller.librarian;

import com.crowvalley.tawelib.Main;
import com.crowvalley.tawelib.UserContextHolder;
import com.crowvalley.tawelib.controller.FXController;
import com.crowvalley.tawelib.util.FXMLUtils;
import javafx.fxml.FXML;

public class LibrarianLogOutController implements FXController {

    @FXML
    private void logOut() {
        UserContextHolder.clear();
        FXMLUtils.loadNewScene(Main.LOGIN_PAGE_FXML);
    }

}
