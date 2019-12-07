package com.crowvalley.tawelib.controller.librarian;

import com.crowvalley.tawelib.model.resource.*;
import com.crowvalley.tawelib.service.ResourceService;
import com.crowvalley.tawelib.util.FXMLUtils;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class AddResourceController {

    public static final Logger LOGGER = LoggerFactory.getLogger(AddResourceController.class);

    private static final String LIBRARIAN_HOME_FXML = "/fxml/librarian/librarianHome.fxml";

    private ResourceService<Book> bookService;

    private ResourceService<Dvd> dvdService;

    private ResourceService<Laptop> laptopService;

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
    private Button btnChangePic;

    public void initialize() {
        cmbType.setOnAction(e -> showTextFieldsAndLabels());
        btnSave.setOnAction(e -> saveResource());
    }

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
    }

    private void showTextFieldsAndLabelsForBook() {
        setVisibilityOnNodes(true, txtTitle, txtYear, txtOptional1, txtOptional2, txtOptional3, txtOptional4, txtOptional5);
        setVisibilityOnNodes(true, lblTitle, lblYear, lblOptional1, lblOptional2, lblOptional3, lblOptional4, lblOptional5);
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
        setVisibilityOnNodes(true, txtTitle, txtYear, txtOptional1, txtOptional2, txtOptional3, txtOptional4,
                lblTitle, lblYear, lblOptional1, lblOptional2, lblOptional3, lblOptional4);
        setVisibilityOnNodes(false, txtOptional5, lblOptional5);
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
        setVisibilityOnNodes(true, txtTitle, txtYear, txtOptional1, txtOptional2, txtOptional3,
                lblTitle, lblYear, lblOptional1, lblOptional2, lblOptional3);
        setVisibilityOnNodes(false, txtOptional4, txtOptional5, lblOptional4, lblOptional5);
        lblOptional1.setText("Manufacturer:");
        lblOptional1.setLayoutX(80.0);
        lblOptional2.setText("Model:");
        lblOptional2.setLayoutX(117.0);
        lblOptional3.setText("Operating System:");
        lblOptional3.setLayoutX(57.0);
    }

    private void setVisibilityOnNodes(boolean isVisible, Node... nodes) {
        for (Node node : nodes) {
            node.setVisible(isVisible);
        }
    }

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
            bookService.save(book);
            FXMLUtils.loadNewScene(btnSave, LIBRARIAN_HOME_FXML);
        } catch (Exception e) {
            FXMLUtils.displayErrorDialogBox("Error Creating Book", e.getMessage());
        }
    }

    private void saveDvd(String title, String year, String imageUrl) {
        String director = txtOptional1.getText();
        String language = txtOptional2.getText();
        Integer runtime = Integer.valueOf(txtOptional3.getText());
        String subLang = txtOptional4.getText();

        try {
            Dvd dvd = ResourceFactory.createDvd(title, year, imageUrl, director, language, runtime, subLang);
            dvdService.save(dvd);
            FXMLUtils.loadNewScene(btnSave, LIBRARIAN_HOME_FXML);
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
            laptopService.save(laptop);
            FXMLUtils.loadNewScene(btnSave, LIBRARIAN_HOME_FXML);
        } catch (Exception e) {
            FXMLUtils.displayErrorDialogBox("Error Creating Laptop", e.getMessage());
        }
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
