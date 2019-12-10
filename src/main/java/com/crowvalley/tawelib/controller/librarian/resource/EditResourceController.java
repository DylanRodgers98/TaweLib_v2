package com.crowvalley.tawelib.controller.librarian.resource;

import com.crowvalley.tawelib.controller.librarian.LibrarianResourcesTabController;
import com.crowvalley.tawelib.model.resource.*;
import com.crowvalley.tawelib.model.user.Librarian;
import com.crowvalley.tawelib.service.ResourceService;
import com.crowvalley.tawelib.util.FXMLUtils;
import com.crowvalley.tawelib.util.ImageUtils;
import com.crowvalley.tawelib.util.ResourceUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

public class EditResourceController {

    private static final String LIBRARIAN_HOME_FXML = "/fxml/librarian/librarianHome.fxml";

    private static final String RESOURCES_DIRECTORY_NAME = "resources";

    private ResourceService<Book> bookService;

    private ResourceService<Dvd> dvdService;

    private ResourceService<Laptop> laptopService;

    private Resource resource;

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
    private Button btnUpdate;

    @FXML
    private ImageView imgResourcePic;

    @FXML
    private Button btnChangePic;

    public void initialize() {
        resource = LibrarianResourcesTabController.selectedResource;
        populateFields();

        btnUpdate.setOnAction(e -> updateResource());
        btnChangePic.setOnAction(e -> chooseImage());
    }

    private void populateFields() {
        ResourceType resourceType = ResourceUtils.getResourceType(resource);

        txtType.setText(resourceType.name());
        txtTitle.setText(resource.getTitle());
        txtYear.setText(resource.getYear());

        if (resourceType.equals(ResourceType.BOOK)) {
            populateBookFields();
        }
        if (resourceType.equals(ResourceType.DVD)) {
            populateDvdFields();
        }
        if (resourceType.equals(ResourceType.LAPTOP)) {
            populateLaptopFields();
        }
    }

    private void populateBookFields() {
        showTextFieldsAndLabelsForBook();
        Book book = (Book) resource;
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
        Dvd dvd = (Dvd) resource;
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
        Laptop laptop = (Laptop) resource;
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

    private void updateResource() {
        resource.setTitle(txtTitle.getText());
        resource.setYear(txtYear.getText());

        Image image = imgResourcePic.getImage();
        String imageUrl = image == null ? StringUtils.EMPTY : image.getUrl(); //TODO: replace empty string with default img
        resource.setImageUrl(imageUrl);

        ResourceType resourceType = ResourceType.valueOf(txtType.getText());
        if (resourceType.equals(ResourceType.BOOK)) {
            updateBook((Book) resource);
        }
        if (resourceType.equals(ResourceType.DVD)) {
            updateDvd((Dvd) resource);
        }
        if (resourceType.equals(ResourceType.LAPTOP)) {
            updateLaptop((Laptop) resource);
        }
    }

    private void updateBook(Book book) {
        book.setAuthor(txtOptional1.getText());
        book.setPublisher(txtOptional2.getText());
        book.setGenre(txtOptional3.getText());
        book.setIsbn(txtOptional4.getText());
        book.setLanguage(txtOptional5.getText());

        try {
            bookService.update(book);
            FXMLUtils.loadNewScene(btnUpdate, LIBRARIAN_HOME_FXML);
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
            dvdService.update(dvd);
            FXMLUtils.loadNewScene(btnUpdate, LIBRARIAN_HOME_FXML);
        } catch (Exception e) {
            FXMLUtils.displayErrorDialogBox("Error Updating DVD", e.getMessage());
        }
    }

    private void updateLaptop(Laptop laptop) {
        laptop.setManufacturer(txtOptional1.getText());
        laptop.setModel(txtOptional2.getText());
        laptop.setOs(txtOptional3.getText());

        try {
            laptopService.update(laptop);
            FXMLUtils.loadNewScene(btnUpdate, LIBRARIAN_HOME_FXML);
        } catch (Exception e) {
            FXMLUtils.displayErrorDialogBox("Error Updating Laptop", e.getMessage());
        }
    }

    private void chooseImage() {
        Optional<Image> image = ImageUtils.chooseAndCopyImage("Choose Resource Picture", RESOURCES_DIRECTORY_NAME, btnChangePic);
        if (image.isPresent()) {
            ImageUtils.deleteOldImage(imgResourcePic);
            imgResourcePic.setImage(image.get());
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
