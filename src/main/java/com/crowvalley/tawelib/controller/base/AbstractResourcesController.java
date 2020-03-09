package com.crowvalley.tawelib.controller.base;

import com.crowvalley.tawelib.controller.FXController;
import com.crowvalley.tawelib.model.resource.Resource;
import com.crowvalley.tawelib.model.resource.ResourceDTO;
import com.crowvalley.tawelib.model.resource.ResourceType;
import com.crowvalley.tawelib.service.ResourceService;
import com.crowvalley.tawelib.util.FXMLUtils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Comparator;
import java.util.Optional;

public abstract class AbstractResourcesController implements FXController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractResourcesController.class);

    protected ResourceService resourceService;

    @FXML
    protected TableView<ResourceDTO> tblResources;

    @FXML
    private TableColumn<ResourceDTO, ResourceType> colType;

    @FXML
    private TableColumn<ResourceDTO, String> colTitle;

    @FXML
    private TableColumn<ResourceDTO, String> colYear;

    @FXML
    protected Button btnViewResource;

    protected void populateTable() {
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

    protected void openViewResourcePage() {
        try {
            openSelectionAwarePage(getViewResourceFxml());
        } catch (ClassCastException e) {
            LOGGER.error("ClassCastException caught when trying to cast controller from FXML to ViewResourceController", e);
            FXMLUtils.displayErrorDialogBox(FXMLUtils.ERROR_LOADING_NEW_SCENE_ERROR_MESSAGE, e.toString());
        }
    }

    protected void openSelectionAwarePage(String fxml) {
        try {
            ResourceDTO selectedResource = getSelectedResource();
            Optional<? extends Resource> resource = resourceService.get(selectedResource.getId(), selectedResource.getResourceType());
            if (resource.isPresent()) {
                FXMLUtils.loadNewSceneWithSelectedItem(fxml, resource.get());
            } else {
                LOGGER.error("Error loading Resource from database");
                FXMLUtils.displayErrorDialogBox(FXMLUtils.ERROR_LOADING_NEW_SCENE_ERROR_MESSAGE, "Error loading Resource from database");
            }
        } catch (IOException e) {
            LOGGER.error("IOException caught when loading new scene from FXML", e);
            FXMLUtils.displayErrorDialogBox(FXMLUtils.ERROR_LOADING_NEW_SCENE_ERROR_MESSAGE, e.toString());
        }
    }

    protected ResourceDTO getSelectedResource() {
        return tblResources.getSelectionModel().getSelectedItem();
    }

    protected abstract String getViewResourceFxml();

    public abstract void setResourceService(ResourceService resourceService);

}
