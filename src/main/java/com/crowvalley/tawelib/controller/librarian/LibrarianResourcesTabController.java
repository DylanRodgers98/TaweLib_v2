package com.crowvalley.tawelib.controller.librarian;

import com.crowvalley.tawelib.model.resource.*;
import com.crowvalley.tawelib.service.ResourceService;
import com.crowvalley.tawelib.util.ResourceUtils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class LibrarianResourcesTabController {

    private ResourceService bookService;

    private ResourceService dvdService;

    private ResourceService laptopService;

    @FXML
    private TableView<Resource> tblResources;

    @FXML
    private TableColumn<Resource, ResourceType> clmType;

    @FXML
    private TableColumn<Resource, String> clmTitle;

    @FXML
    private TableColumn<Resource, String> clmYear;

    public LibrarianResourcesTabController() {
    }

    public void initialize() {
        clmType.setCellValueFactory(this::getResourceType);
        clmTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        clmYear.setCellValueFactory(new PropertyValueFactory<>("year"));
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
        return resources;
    }

    public void setBookService(ResourceService bookService) {
        this.bookService = bookService;
    }

    public void setDvdService(ResourceService dvdService) {
        this.dvdService = dvdService;
    }

    public void setLaptopService(ResourceService laptopService) {
        this.laptopService = laptopService;
    }

}
