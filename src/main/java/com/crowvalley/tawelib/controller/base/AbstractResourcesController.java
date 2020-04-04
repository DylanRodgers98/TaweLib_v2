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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
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

    @FXML
    private ChoiceBox<ResourceType> cmbType;

    @FXML
    private TextField txtSearch;

    @Override
    public void initialize() {
        colType.setCellValueFactory(this::getResourceType);
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        tblResources.setItems(getResources());
    }

    @FXML
    private void searchIfEnterPressed(KeyEvent e) {
        if (e.getCode().equals(KeyCode.ENTER)) {
            search();
        }
    }

    @FXML
    private void executeTypeChange() {
        if (StringUtils.isBlank(txtSearch.getText())) {
            tblResources.setItems(getResources());
        } else {
            search();
        }
    }

    @FXML
    protected abstract void enableButtonsIfResourceSelected();

    private ObservableValue<ResourceType> getResourceType(TableColumn.CellDataFeatures<ResourceDTO, ResourceType> resource) {
        return new ReadOnlyObjectWrapper<>(resource.getValue().getResourceType());
    }

    private ObservableList<ResourceDTO> getResources() {
        List<ResourceDTO> resourceDTOS = resourceService.getAllResourceDTOs(cmbType.getValue());
        return constructObservableList(resourceDTOS);
    }

    private ObservableList<ResourceDTO> constructObservableList(List<ResourceDTO> resourceDTOS) {
        ObservableList<ResourceDTO> resources = FXCollections.observableArrayList(resourceDTOS);
        resources.sort(Comparator.comparing(ResourceDTO::getTitle));
        return resources;
    }

    @FXML
    protected void openViewResourcePage() {
        openSelectionAwarePage(getViewResourceFxml());
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

    @FXML
    private void search() {
        if (StringUtils.isBlank(txtSearch.getText())) {
            tblResources.setItems(getResources());
        } else {
            List<ResourceDTO> searchResult = resourceService.search(txtSearch.getText(), cmbType.getValue());
            tblResources.setItems(constructObservableList(searchResult));
        }
    }

    protected ResourceDTO getSelectedResource() {
        return tblResources.getSelectionModel().getSelectedItem();
    }

    protected abstract String getViewResourceFxml();

    public void setResourceService(ResourceService resourceService) {
        this.resourceService = resourceService;
        LOGGER.info("{} ResourceService set to {}", this.getClass().getSimpleName(), resourceService.getClass().getSimpleName());
    }

}
