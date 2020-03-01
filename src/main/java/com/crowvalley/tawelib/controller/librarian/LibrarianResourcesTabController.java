package com.crowvalley.tawelib.controller.librarian;

import com.crowvalley.tawelib.controller.FXController;
import com.crowvalley.tawelib.controller.SelectionAwareFXController;
import com.crowvalley.tawelib.controller.librarian.resources.EditResourceController;
import com.crowvalley.tawelib.controller.librarian.resources.ViewResourceController;
import com.crowvalley.tawelib.model.resource.*;
import com.crowvalley.tawelib.service.ResourceService;
import com.crowvalley.tawelib.util.FXMLUtils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Comparator;
import java.util.Optional;

public class LibrarianResourcesTabController implements FXController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LibrarianResourcesTabController.class);

    private static final String ADD_NEW_RESOURCE_FXML = "/fxml/librarian/resources/addResource.fxml";

    private static final String VIEW_RESOURCE_FXML = "/fxml/librarian/resources/viewResource.fxml";

    private static final String EDIT_RESOURCE_FXML = "/fxml/librarian/resources/editResource.fxml";

    private ResourceService resourceService;

    @FXML
    private TableView<ResourceDTO> tblResources;

    @FXML
    private TableColumn<ResourceDTO, ResourceType> colType;

    @FXML
    private TableColumn<ResourceDTO, String> colTitle;

    @FXML
    private TableColumn<ResourceDTO, String> colYear;

    @FXML
    private Button btnNewResource;

    @FXML
    private Button btnViewResource;

    @FXML
    private Button btnEditResource;

    @FXML
    private Button btnDeleteResource;

    @Override
    public void initialize() {
        populateTable();
        FXMLUtils.makeNodesDisabled(btnViewResource, btnEditResource, btnDeleteResource);
        setOnActions();
    }

    private void populateTable() {
        colType.setCellValueFactory(this::getResourceType);
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        tblResources.setItems(getResources());
    }

    private ObservableValue<ResourceType> getResourceType(TableColumn.CellDataFeatures<ResourceDTO, ResourceType> resource) {
        return new ReadOnlyObjectWrapper<>(resource.getValue().getResourceType());
    }

    private ObservableList<ResourceDTO> getResources() {
        ObservableList<ResourceDTO> resources = FXCollections.observableArrayList(resourceService.getAllResourceDTOs());
        resources.sort(Comparator.comparing(ResourceDTO::getTitle));
        return resources;
    }

    private void setOnActions() {
        tblResources.setOnMouseClicked(e -> enableButtonsIfResourceSelected());
        btnNewResource.setOnAction(e -> FXMLUtils.loadNewScene(btnNewResource, ADD_NEW_RESOURCE_FXML));
        btnViewResource.setOnAction(e -> openViewResourcePage());
        btnEditResource.setOnAction(e -> openEditResourcePage());
        btnDeleteResource.setOnAction(e -> deleteSelectedResource());
    }

    private void enableButtonsIfResourceSelected() {
        if (getSelectedResource() != null) {
            FXMLUtils.makeNodesEnabled(btnViewResource, btnEditResource, btnDeleteResource);
        }
    }

    private void openViewResourcePage() {
        try {
            openSelectionAwarePage(VIEW_RESOURCE_FXML);
        } catch (ClassCastException e) {
            LOGGER.error("ClassCastException caught when trying to cast controller from FXML to ViewResourceController", e);
            FXMLUtils.displayErrorDialogBox(FXMLUtils.ERROR_LOADING_NEW_SCENE_ERROR_MESSAGE, e.toString());
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

    private void openSelectionAwarePage(String fxml) {
        try {
            ResourceDTO selectedResource = getSelectedResource();
            Optional<? extends Resource> resource = resourceService.get(selectedResource.getId(), selectedResource.getResourceType());
            if (resource.isPresent()) {
                SelectionAwareFXController<Resource> controller = (SelectionAwareFXController<Resource>) FXMLUtils.getController(fxml);
                controller.setSelectedItem(resource.get());
                FXMLUtils.loadNewScene(tblResources, fxml);
            } else {
                LOGGER.error("Error loading Resource from database");
                FXMLUtils.displayErrorDialogBox(FXMLUtils.ERROR_LOADING_NEW_SCENE_ERROR_MESSAGE, "Error loading Resource from database");
            }
        } catch (IOException e) {
            LOGGER.error("IOException caught when loading new scene from FXML", e);
            FXMLUtils.displayErrorDialogBox(FXMLUtils.ERROR_LOADING_NEW_SCENE_ERROR_MESSAGE, e.toString());
        }
    }

    private void deleteSelectedResource() {
        ResourceDTO selectedResource = getSelectedResource();
        String message = String.format("Are you sure you want to delete resource '%s'?", selectedResource.getTitle());
        Optional<ButtonType> result = FXMLUtils.displayConfirmationDialogBox("Delete Resource",  message);

        if (result.isPresent() && result.get() == ButtonType.OK) {
            resourceService.deleteWithId(selectedResource.getId(), selectedResource.getResourceType());
            tblResources.getItems().remove(selectedResource);
        }
    }

    private ResourceDTO getSelectedResource() {
        return tblResources.getSelectionModel().getSelectedItem();
    }

    public void setResourceService(ResourceService resourceService) {
        this.resourceService = resourceService;
        LOGGER.info("{} ResourceService set to {}", this.getClass().getSimpleName(), resourceService.getClass().getSimpleName());
    }

}
