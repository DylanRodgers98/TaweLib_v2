package com.crowvalley.tawelib.controller.user;

import com.crowvalley.tawelib.controller.base.AbstractProfileController;
import com.crowvalley.tawelib.model.user.User;

public class UserProfileController extends AbstractProfileController {

    private static final String USER_HOME_FXML = "/fxml/user/userHome.fxml";

    @Override
    protected User initUpdatedUser() {
        return selectedUser;
    }

    @Override
    protected String getFxmlForBackButton() {
        return USER_HOME_FXML;
    }

}
