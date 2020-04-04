package com.crowvalley.tawelib.controller.librarian.resources;

import com.crowvalley.tawelib.controller.SelectionAwareFXController;
import com.crowvalley.tawelib.model.resource.*;
import com.crowvalley.tawelib.service.ResourceService;
import com.crowvalley.tawelib.util.FXMLUtils;
import com.crowvalley.tawelib.util.ImageUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class EditResourceController implements SelectionAwareFXController<Resource> {

    private static final Logger LOGGER = LoggerFactory.getLogger(EditResourceController.class);

    private static final String LIBRARIAN_HOME_FXML = "/fxml/librarian/librarianHome.fxml";

    private static final String IMAGE_CHOOSER_TITLE = "Choose Resource Picture";

    private static final String RESOURCES_DIRECTORY_NAME = "resources";

    private ResourceService resourceService;

    private Resource selectedResource;

    @FXML
    private TextField txtType;

    @FXML
    private TextField txtTitle;

    @FXML
    private TextField txtYear;

    @FXML
    private TextField txtOptional1;

    @FXML
    private TextField txtOptional2;

    @FXML
    private TextField txtOptional3;

    @FXML
    private TextField txtOptional4;

    @FXML
    private TextField txtOptional5;

    @FXML
    private Label lblOptional1;

    @FXML
    private Label lblOptional2;

    @FXML
    private Label lblOptional3;

    @FXML
    private Label lblOptional4;

    @FXML
    private Label lblOptional5;

    @FXML
    private ImageView imgResourcePic;

    @Override
    public void initialize() {
        if (selectedResource != null) {
            populateFields();
        }
    }

    private void populateFields() {
        ResourceType resourceType = selectedResource.getResourceType();

        txtType.setText(resourceType.name());
        txtTitle.setText(selectedResource.getTitle());
        txtYear.setText(selectedResource.getYear());
        loadResourcePic(selectedResource);

        if (resourceType.equals(ResourceType.BOOK)) {
            populateBookFields();
        } else if (resourceType.equals(ResourceType.DVD)) {
            populateDvdFields();
        } else if (resourceType.equals(ResourceType.LAPTOP)) {
            populateLaptopFields();
        }
    }

    private void loadResourcePic(Resource resource) {
        Optional<String> imageUrl = resource.getImageUrl();
        imageUrl.ifPresent(e -> imgResourcePic.setImage(new Image(e)));
    }

    private void populateBookFields() {
        showTextFieldsAndLabelsForBook();
        Book book = (Book) selectedResource;
        txtOptional1.setText(book.getAuthor());
        txtOptional2.setText(book.getPublisher());
        txtOptional3.setText(book.getGenre());
        txtOptional4.setText(book.getIsbn());
        txtOptional5.setText(book.getLanguage());
    }

    private void showTextFieldsAndLabelsForBook() {
        FXMLUtils.makeNodesVisible(txtOptional1, txtOptional2, txtOptional3, txtOptional4, txtOptional5,
                lblOptional1, lblOptional2, lblOptional3, lblOptional4, lblOptional5);
        lblOptional1.setText("Author:");
        lblOptional1.setLayoutX(114.0);
        lblOptional2.setText("Publisher:");
        lblOptional2.setLayoutX(102.0);
        lblOptional3.setText("Genre:");
        lblOptional3.setLayoutX(119.0);
        lblOptional4.setText("ISBN:");
        lblOptional4.setLayoutX(125.0);
        lblOptional5.setText("Language:");
        lblOptional5.setLayoutX(99.0);
    }

    private void populateDvdFields() {
        showTextFieldsAndLabelsForDvd();
        Dvd dvd = (Dvd) selectedResource;
        txtOptional1.setText(dvd.getDirector());
        txtOptional2.setText(dvd.getLanguage());
        txtOptional3.setText(String.valueOf(dvd.getRuntime()));
        txtOptional4.setText(dvd.getSubLang());
    }

    private void showTextFieldsAndLabelsForDvd() {
        FXMLUtils.makeNodesVisible(txtOptional1, txtOptional2, txtOptional3, txtOptional4,
                lblOptional1, lblOptional2, lblOptional3, lblOptional4);
        lblOptional1.setText("Director:");
        lblOptional1.setLayoutX(108.0);
        lblOptional2.setText("Language:");
        lblOptional2.setLayoutX(99.0);
        lblOptional3.setText("Runtime:");
        lblOptional3.setLayoutX(107.0);
        lblOptional4.setText("Subtitle Language:");
        lblOptional4.setLayoutX(55.0);
    }

    private void populateLaptopFields() {
        showTextFieldsAndLabelsForLaptop();
        Laptop laptop = (Laptop) selectedResource;
        txtOptional1.setText(laptop.getManufacturer());
        txtOptional2.setText(laptop.getModel());
        txtOptional3.setText(laptop.getOs());
    }

    private void showTextFieldsAndLabelsForLaptop() {
        FXMLUtils.makeNodesVisible(txtOptional1, txtOptional2, txtOptional3,
                lblOptional1, lblOptional2, lblOptional3);
        lblOptional1.setText("Manufacturer:");
        lblOptional1.setLayoutX(80.0);
        lblOptional2.setText("Model:");
        lblOptional2.setLayoutX(117.0);
        lblOptional3.setText("Operating System:");
        lblOptional3.setLayoutX(57.0);
    }

    @FXML
    private void chooseImage() {
        ImageUtils.chooseImage(IMAGE_CHOOSER_TITLE, RESOURCES_DIRECTORY_NAME, imgResourcePic);
    }

    @FXML
    private void loadLibrarianHome() {
        FXMLUtils.loadNewScene(LIBRARIAN_HOME_FXML);
    }

    @FXML
    private void updateResource() {
        selectedResource.setTitle(txtTitle.getText());
        selectedResource.setYear(txtYear.getText());

        Image image = imgResourcePic.getImage();
        String imageUrl = image == null ? StringUtils.EMPTY : image.getUrl(); //TODO: replace empty string with default img
        selectedResource.setImageUrl(imageUrl);

        ResourceType resourceType = ResourceType.valueOf(txtType.getText());
        if (resourceType.equals(ResourceType.BOOK)) {
            updateBook((Book) selectedResource);
        } else if (resourceType.equals(ResourceType.DVD)) {
            updateDvd((Dvd) selectedResource);
        } else if (resourceType.equals(ResourceType.LAPTOP)) {
            updateLaptop((Laptop) selectedResource);
        }
    }

    private void updateBook(Book book) {
        book.setAuthor(txtOptional1.getText());
        book.setPublisher(txtOptional2.getText());
        book.setGenre(txtOptional3.getText());
        book.setIsbn(txtOptional4.getText());
        book.setLanguage(txtOptional5.getText());

        try {
            resourceService.saveOrUpdate(book);
            FXMLUtils.loadNewScene(LIBRARIAN_HOME_FXML);
        } catch (Exception e) {
            FXMLUtils.displayErrorDialogBox("Error Updating Book", e.getMessage());
        }
    }

    private void updateDvd(Dvd dvd) {
        dvd.setDirector(txtOptional1.getText());
        dvd.setLanguage(txtOptional2.getText());
        try {
            dvd.setRuntime(Integer.parseInt(txtOptional3.getText()));
        } catch (NumberFormatException e) {
            FXMLUtils.displayErrorDialogBox("Error Updating DVD", "Runtime must be numeric");
        }
        dvd.setSubLang(txtOptional4.getText());

        try {
            resourceService.saveOrUpdate(dvd);
            FXMLUtils.loadNewScene(LIBRARIAN_HOME_FXML);
        } catch (Exception e) {
            FXMLUtils.displayErrorDialogBox("Error Updating DVD", e.getMessage());
        }
    }

    private void updateLaptop(Laptop laptop) {
        laptop.setManufacturer(txtOptional1.getText());
        laptop.setModel(txtOptional2.getText());
        laptop.setOs(txtOptional3.getText());

        try {
            resourceService.saveOrUpdate(laptop);
            FXMLUtils.loadNewScene(LIBRARIAN_HOME_FXML);
        } catch (Exception e) {
            FXMLUtils.displayErrorDialogBox("Error Updating Laptop", e.getMessage());
        }
    }

    @Override
    public void setSelectedItem(Resource selectedItem) {
        this.selectedResource = selectedItem;
    }

    public void setResourceService(ResourceService resourceService) {
        this.resourceService = resourceService;
        LOGGER.info("{} ResourceService set to {}", this.getClass().getSimpleName(), resourceService.getClass().getSimpleName());
    }

}
