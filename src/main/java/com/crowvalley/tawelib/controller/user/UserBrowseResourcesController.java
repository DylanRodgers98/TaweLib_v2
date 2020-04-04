package com.crowvalley.tawelib.controller.user;

import com.crowvalley.tawelib.controller.base.AbstractResourcesController;
import com.crowvalley.tawelib.util.FXMLUtils;
import javafx.fxml.FXML;

public class UserBrowseResourcesController extends AbstractResourcesController {

    private static final String USER_HOME_FXML = "/fxml/user/userHome.fxml";

    private static final String VIEW_RESOURCE_FXML = "/fxml/user/userViewResource.fxml";

    @FXML
    private void loadUserHome() {
        FXMLUtils.loadNewScene(USER_HOME_FXML);
    }

    @Override
    protected void enableButtonsIfResourceSelected() {
        if (getSelectedResource() != null) {
            FXMLUtils.makeNodesEnabled(btnViewResource);
        }
    }

    @Override
    protected String getViewResourceFxml() {
        return VIEW_RESOURCE_FXML;
    }

}
