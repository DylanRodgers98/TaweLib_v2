package com.crowvalley.tawelib.controller.librarian;

import com.crowvalley.tawelib.controller.base.AbstractResourcesController;
import com.crowvalley.tawelib.model.resource.*;
import com.crowvalley.tawelib.util.FXMLUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Optional;

public class LibrarianResourcesTabController extends AbstractResourcesController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LibrarianResourcesTabController.class);

    private static final String ADD_NEW_RESOURCE_FXML = "/fxml/librarian/resources/addResource.fxml";

    private static final String VIEW_RESOURCE_FXML = "/fxml/librarian/resources/viewResource.fxml";

    private static final String EDIT_RESOURCE_FXML = "/fxml/librarian/resources/editResource.fxml";

    @FXML
    private Button btnNewResource;

    @FXML
    private Button btnEditResource;

    @FXML
    private Button btnDeleteResource;

    @Override
    public void initialize() {
        FXMLUtils.makeNodesDisabled(btnViewResource, btnEditResource, btnDeleteResource);
        super.initialize();
    }

    @Override
    protected void setOnActions() {
        btnNewResource.setOnAction(e -> FXMLUtils.loadNewScene(ADD_NEW_RESOURCE_FXML));
        btnEditResource.setOnAction(e -> openEditResourcePage());
        btnDeleteResource.setOnAction(e -> deleteSelectedResource());
        super.setOnActions();
    }

    @Override
    protected void enableButtonsIfResourceSelected() {
        if (getSelectedResource() != null) {
            FXMLUtils.makeNodesEnabled(btnViewResource, btnEditResource, btnDeleteResource);
        }
    }

    private void openEditResourcePage() {
        try {
            openSelectionAwarePage(EDIT_RESOURCE_FXML);
        } catch (ClassCastException e) {
            LOGGER.error("ClassCastException caught when trying to cast controller from FXML to EditResourceController", e);
            FXMLUtils.displayErrorDialogBox(FXMLUtils.ERROR_LOADING_NEW_SCENE_ERROR_MESSAGE, e.toString());
        }
    }

    private void deleteSelectedResource() {
        ResourceDTO selectedResource = getSelectedResource();
        String message = String.format("Are you sure you want to delete resource '%s'?", selectedResource.getTitle());
        Optional<ButtonType> result = FXMLUtils.displayConfirmationDialogBox("Delete Resource",  message);

        if (result.isPresent() && result.get() == ButtonType.OK) {
            resourceService.deleteWithId(selectedResource.getId());
            tblResources.getItems().remove(selectedResource);
        }
    }

    @Override
    protected String getViewResourceFxml() {
        return VIEW_RESOURCE_FXML;
    }

}
