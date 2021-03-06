package com.crowvalley.tawelib.controller.librarian.resources;

import com.crowvalley.tawelib.controller.FXController;
import com.crowvalley.tawelib.model.resource.*;
import com.crowvalley.tawelib.service.ResourceService;
import com.crowvalley.tawelib.util.FXMLUtils;
import com.crowvalley.tawelib.util.ImageUtils;
import com.google.common.collect.Sets;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddResourceController implements FXController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AddResourceController.class);

    private static final String LIBRARIAN_HOME_FXML = "/fxml/librarian/librarianHome.fxml";

    private static final String FILE_CHOOSER_TITLE = "Choose Resource Picture";

    private static final String RESOURCES_DIRECTORY_NAME = "resources";

    private ResourceService resourceService;

    @FXML
    private ChoiceBox<ResourceType> cmbType;

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
    private Label lblTitle;

    @FXML
    private Label lblYear;

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
    private Button btnSave;

    @FXML
    private ImageView imgResourcePic;

    @FXML
    private void chooseImage() {
        ImageUtils.chooseImage(FILE_CHOOSER_TITLE, RESOURCES_DIRECTORY_NAME, imgResourcePic);
    }

    @FXML
    private void loadLibrarianHome() {
        FXMLUtils.loadNewScene(LIBRARIAN_HOME_FXML);
    }

    @FXML
    private void showTextFieldsAndLabels() {
        ResourceType resourceType = cmbType.getValue();
        if (resourceType.equals(ResourceType.BOOK)) {
            showTextFieldsAndLabelsForBook();
        }
        if (resourceType.equals(ResourceType.DVD)) {
            showTextFieldsAndLabelsForDvd();
        }
        if (resourceType.equals(ResourceType.LAPTOP)) {
            showTextFieldsAndLabelsForLaptop();
        }
        btnSave.setVisible(true);
    }

    private void showTextFieldsAndLabelsForBook() {
        FXMLUtils.makeNodesVisible(txtTitle, txtYear, txtOptional1, txtOptional2, txtOptional3, txtOptional4, txtOptional5,
                lblTitle, lblYear, lblOptional1, lblOptional2, lblOptional3, lblOptional4, lblOptional5);
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

    private void showTextFieldsAndLabelsForDvd() {
        FXMLUtils.makeNodesVisible(txtTitle, txtYear, txtOptional1, txtOptional2, txtOptional3, txtOptional4,
                lblTitle, lblYear, lblOptional1, lblOptional2, lblOptional3, lblOptional4);
        FXMLUtils.makeNodesNotVisible(txtOptional5, lblOptional5);
        lblOptional1.setText("Director:");
        lblOptional1.setLayoutX(108.0);
        lblOptional2.setText("Language:");
        lblOptional2.setLayoutX(99.0);
        lblOptional3.setText("Runtime:");
        lblOptional3.setLayoutX(107.0);
        lblOptional4.setText("Subtitle Language:");
        lblOptional4.setLayoutX(55.0);
    }

    private void showTextFieldsAndLabelsForLaptop() {
        FXMLUtils.makeNodesVisible(txtTitle, txtYear, txtOptional1, txtOptional2, txtOptional3,
                lblTitle, lblYear, lblOptional1, lblOptional2, lblOptional3);
        FXMLUtils.makeNodesNotVisible(txtOptional4, txtOptional5, lblOptional4, lblOptional5);
        lblOptional1.setText("Manufacturer:");
        lblOptional1.setLayoutX(80.0);
        lblOptional2.setText("Model:");
        lblOptional2.setLayoutX(117.0);
        lblOptional3.setText("Operating System:");
        lblOptional3.setLayoutX(57.0);
    }

    @FXML
    private void saveResource() {
        String title = txtTitle.getText();
        String year = txtYear.getText();

        Image image = imgResourcePic.getImage();
        String imageUrl = image == null ? StringUtils.EMPTY : image.getUrl(); //TODO: replace empty string with default img

        ResourceType resourceType = cmbType.getValue();
        if (resourceType.equals(ResourceType.BOOK)) {
            saveBook(title, year, imageUrl);
        }
        if (resourceType.equals(ResourceType.DVD)) {
            saveDvd(title, year, imageUrl);
        }
        if (resourceType.equals(ResourceType.LAPTOP)) {
            saveLaptop(title, year, imageUrl);
        }
    }

    private void saveBook(String title, String year, String imageUrl) {
        String author = txtOptional1.getText();
        String publisher = txtOptional2.getText();
        String genre = txtOptional3.getText();
        String isbn = txtOptional4.getText();
        String language = txtOptional5.getText();

        try {
            Book book = ResourceFactory.createBook(title, year, imageUrl, author, publisher, genre, isbn, language);
            resourceService.saveOrUpdate(book);
            FXMLUtils.loadNewScene(LIBRARIAN_HOME_FXML);
        } catch (Exception e) {
            FXMLUtils.displayErrorDialogBox("Error Creating Book", e.getMessage());
        }
    }

    private void saveDvd(String title, String year, String imageUrl) {
        String director = txtOptional1.getText();
        String language = txtOptional2.getText();
        Integer runtime = null;
        try {
            runtime = Integer.parseInt(txtOptional3.getText());
        } catch (NumberFormatException e) {
            FXMLUtils.displayErrorDialogBox("Error Saving DVD", "Runtime must be numeric");
        }
        String[] subtitleLanguages = txtOptional4.getText().split(",\\s*");

        try {
            Dvd dvd = ResourceFactory.createDvd(title, year, imageUrl, director, language, runtime, Sets.newHashSet(subtitleLanguages));
            resourceService.saveOrUpdate(dvd);
            FXMLUtils.loadNewScene(LIBRARIAN_HOME_FXML);
        } catch (Exception e) {
            FXMLUtils.displayErrorDialogBox("Error Creating DVD", e.getMessage());
        }
    }

    private void saveLaptop(String title, String year, String imageUrl) {
        String manufacturer = txtOptional1.getText();
        String model = txtOptional2.getText();
        String os = txtOptional3.getText();

        try {
            Laptop laptop = ResourceFactory.createLaptop(title, year, imageUrl, manufacturer, model, os);
            resourceService.saveOrUpdate(laptop);
            FXMLUtils.loadNewScene(LIBRARIAN_HOME_FXML);
        } catch (Exception e) {
            FXMLUtils.displayErrorDialogBox("Error Creating Laptop", e.getMessage());
        }
    }

    public void setResourceService(ResourceService resourceService) {
        this.resourceService = resourceService;
        LOGGER.info("{} ResourceService set to {}", this.getClass().getSimpleName(), resourceService.getClass().getSimpleName());
    }

}
