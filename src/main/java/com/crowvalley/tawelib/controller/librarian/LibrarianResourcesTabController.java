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

    public LibrarianResourcesTabController() {
    }

    public void initialize() {
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
        return resources;
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
