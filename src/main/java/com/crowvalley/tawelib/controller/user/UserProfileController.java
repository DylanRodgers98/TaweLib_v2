package com.crowvalley.tawelib.controller.user;

import com.crowvalley.tawelib.controller.base.AbstractProfileController;
import com.crowvalley.tawelib.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserProfileController extends AbstractProfileController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserProfileController.class);

    private static final String USER_HOME_FXML = "/fxml/user/userHome.fxml";

    @Override
    protected String getFxmlForBackButton() {
        return USER_HOME_FXML;
    }

    @Override
    public void setUserService(UserService userService) {
        this.userService = userService;
        LOGGER.info("{} UserService set to {}", this.getClass().getSimpleName(), userService.getClass().getSimpleName());
    }

}
