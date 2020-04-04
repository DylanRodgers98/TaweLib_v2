package com.crowvalley.tawelib.controller.user;

import com.crowvalley.tawelib.controller.base.AbstractProfileController;
import com.crowvalley.tawelib.model.user.User;
import com.crowvalley.tawelib.util.FXMLUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class UserProfileController extends AbstractProfileController {

    private static final String USER_HOME_FXML = "/fxml/user/userHome.fxml";

    @FXML
    private void loadUserHome() {
        FXMLUtils.loadNewScene(USER_HOME_FXML);
    }

}
