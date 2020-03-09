package com.crowvalley.tawelib.controller.user;

import com.crowvalley.tawelib.controller.base.AbstractResourcesController;
import com.crowvalley.tawelib.service.ResourceService;
import com.crowvalley.tawelib.service.ResourceServiceImpl;
import com.crowvalley.tawelib.util.FXMLUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserBrowseResourcesController extends AbstractResourcesController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserBrowseResourcesController.class);

    private static final String USER_HOME_FXML = "/fxml/user/userHome.fxml";

    private static final String VIEW_RESOURCE_FXML = "/fxml/user/userViewResource.fxml";

    @FXML
    private Button btnBack;

    @Override
    public void initialize() {
        populateTable();
        FXMLUtils.makeNodesDisabled(btnViewResource);
        setOnActions();
    }

    private void setOnActions() {
        tblResources.setOnMouseClicked(e -> enableButtonsIfResourceSelected());
        btnViewResource.setOnAction(e -> openViewResourcePage());
        btnBack.setOnAction(e -> FXMLUtils.loadNewScene(USER_HOME_FXML));
    }

    private void enableButtonsIfResourceSelected() {
        if (getSelectedResource() != null) {
            FXMLUtils.makeNodesEnabled(btnViewResource);
        }
    }

    @Override
    protected String getViewResourceFxml() {
        return VIEW_RESOURCE_FXML;
    }

    @Override
    public void setResourceService(ResourceService resourceService) {
        this.resourceService = resourceService;
        LOGGER.info("{} ResourceService set to {}", this.getClass().getSimpleName(), resourceService.getClass().getSimpleName());
    }

}
