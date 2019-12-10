package com.crowvalley.tawelib.controller.librarian;

import com.crowvalley.tawelib.controller.librarian.resource.EditResourceController;
import com.crowvalley.tawelib.model.resource.*;
import com.crowvalley.tawelib.service.ResourceService;
import com.crowvalley.tawelib.util.FXMLUtils;
import com.crowvalley.tawelib.util.ResourceUtils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Comparator;

public class LibrarianResourcesTabController {

    private static final String ADD_NEW_RESOURCE_FXML = "/fxml/librarian/resource/addResource.fxml";

    private static final String EDIT_RESOURCE_FXML = "/fxml/librarian/resource/editResource.fxml";

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

    public LibrarianResourcesTabController() {
    }

    public void initialize() {
        colType.setCellValueFactory(this::getResourceType);
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        tblResources.setItems(getResources());

        btnViewResource.setDisable(true);
        btnEditResource.setDisable(true);
        btnDeleteResource.setDisable(true);

        tblResources.setOnMouseClicked(e -> btnEditResource.setDisable(false));
        btnNewResource.setOnAction(e -> FXMLUtils.loadNewScene(btnNewResource, ADD_NEW_RESOURCE_FXML));
        btnEditResource.setOnAction(e -> openEditResourcePage());
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

    private void openEditResourcePage() {
        selectedResource = tblResources.getSelectionModel().getSelectedItem();
        FXMLUtils.loadNewScene(tblResources, EDIT_RESOURCE_FXML);
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
