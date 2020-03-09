package com.crowvalley.tawelib.controller.librarian.users;

import com.crowvalley.tawelib.controller.base.AbstractProfileController;
import com.crowvalley.tawelib.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ViewOrEditUserController extends AbstractProfileController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ViewOrEditUserController.class);

    private static final String LIBRARIAN_HOME_FXML = "/fxml/librarian/librarianHome.fxml";

    @Override
    protected String getFxmlForBackButton() {
        return LIBRARIAN_HOME_FXML;
    }

    @Override
    public void setUserService(UserService userService) {
        this.userService = userService;
        LOGGER.info("{} UserService set to {}", this.getClass().getSimpleName(), userService.getClass().getSimpleName());
    }

}
