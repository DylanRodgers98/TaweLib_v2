package com.crowvalley.tawelib.controller.librarian;

import com.crowvalley.tawelib.model.resource.*;
import com.crowvalley.tawelib.service.ResourceService;
import com.crowvalley.tawelib.util.FXMLUtils;
import com.crowvalley.tawelib.util.ResourceUtils;
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

import java.util.Comparator;
import java.util.Optional;

public class LibrarianResourcesTabController {

    private static final String ADD_NEW_RESOURCE_FXML = "/fxml/librarian/resources/addResource.fxml";

    private static final String VIEW_RESOURCE_FXML = "/fxml/librarian/resources/viewResource.fxml";

    private static final String EDIT_RESOURCE_FXML = "/fxml/librarian/resources/editResource.fxml";

    public static Resource selectedResource;

    private ResourceService<Book> bookService;

    private ResourceService<Dvd> dvdService;

    private ResourceService<Laptop> laptopService;

    @FXML
    private TableView<Resource> tblResources;

    @FXML
    private TableColumn<Resource, ResourceType> colType;

    @FXML
    private TableColumn<Resource, String> colTitle;

    @FXML
    private TableColumn<Resource, String> colYear;

    @FXML
    private Button btnNewResource;

    @FXML
    private Button btnViewResource;

    @FXML
    private Button btnEditResource;

    @FXML
    private Button btnDeleteResource;

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

    private ObservableValue<ResourceType> getResourceType(TableColumn.CellDataFeatures<Resource, ResourceType> resource) {
        return new ReadOnlyObjectWrapper<>(ResourceUtils.getResourceType(resource.getValue()));
    }

    private ObservableList<Resource> getResources() {
        ObservableList<Resource> resources = FXCollections.observableArrayList();
        resources.addAll(bookService.getAll());
        resources.addAll(dvdService.getAll());
        resources.addAll(laptopService.getAll());
        resources.sort(Comparator.comparingLong(Resource::getId));
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
        selectedResource = getSelectedResource();
        FXMLUtils.loadNewScene(tblResources, VIEW_RESOURCE_FXML);
    }

    private void openEditResourcePage() {
        selectedResource = getSelectedResource();
        FXMLUtils.loadNewScene(tblResources, EDIT_RESOURCE_FXML);
    }

    private void deleteSelectedResource() {
        selectedResource = getSelectedResource();
        String message = String.format("Are you sure you want to delete resource '%s'?", selectedResource.getTitle());
        Optional<ButtonType> result = FXMLUtils.displayConfirmationDialogBox("Delete Resource",  message);

        if (result.isPresent() && result.get() == ButtonType.OK) {
            ResourceType resourceType = ResourceUtils.getResourceType(selectedResource);
            if (resourceType.equals(ResourceType.BOOK)) {
                bookService.delete((Book) selectedResource);
            }
            if (resourceType.equals(ResourceType.DVD)) {
                dvdService.delete((Dvd) selectedResource);
            }
            if (resourceType.equals(ResourceType.LAPTOP)) {
                laptopService.delete((Laptop) selectedResource);
            }
            tblResources.getItems().remove(selectedResource);
        }
    }

    private Resource getSelectedResource() {
        return tblResources.getSelectionModel().getSelectedItem();
    }

    public void setBookService(ResourceService<Book> bookService) {
        this.bookService = bookService;
    }

    public void setDvdService(ResourceService<Dvd> dvdService) {
        this.dvdService = dvdService;
    }

    public void setLaptopService(ResourceService<Laptop> laptopService) {
        this.laptopService = laptopService;
    }

}
