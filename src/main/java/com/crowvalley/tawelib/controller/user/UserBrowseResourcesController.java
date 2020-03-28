package com.crowvalley.tawelib.controller.user;

import com.crowvalley.tawelib.controller.base.AbstractResourcesController;
import com.crowvalley.tawelib.model.resource.ResourceDTO;
import com.crowvalley.tawelib.model.resource.ResourceType;
import com.crowvalley.tawelib.service.ResourceService;
import com.crowvalley.tawelib.util.FXMLUtils;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.List;

public class UserBrowseResourcesController extends AbstractResourcesController {

    private static final String USER_HOME_FXML = "/fxml/user/userHome.fxml";

    private static final String VIEW_RESOURCE_FXML = "/fxml/user/userViewResource.fxml";

    @FXML
    private Button btnBack;

    @Override
    public void initialize() {
        FXMLUtils.makeNodesDisabled(btnViewResource);
        super.initialize();
    }

    @Override
    protected void setOnActions() {
        btnBack.setOnAction(e -> FXMLUtils.loadNewScene(USER_HOME_FXML));
        super.setOnActions();
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
