package com.crowvalley.tawelib.controller.base;

import com.crowvalley.tawelib.controller.SelectionAwareFXController;
import com.crowvalley.tawelib.model.resource.*;
import com.crowvalley.tawelib.service.CopyService;
import com.crowvalley.tawelib.service.LoanService;
import com.crowvalley.tawelib.util.FXMLUtils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableStringValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public abstract class AbstractViewResourceController implements SelectionAwareFXController<Resource> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractViewResourceController.class);

    protected Resource selectedResource;

    protected CopyService copyService;

    protected LoanService loanService;

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

    @FXML
    protected TableView<Copy> tblCopies;

    @FXML
    private TableColumn<Copy, String> colLoanDuration;

    @FXML
    private TableColumn<Copy, String> colLocation;

    @FXML
    private TableColumn<Copy, Integer> colNumOfRequests;

    @Override
    public void initialize() {
        if (selectedResource != null) {
            populateTable();
            populateFields();
        }
    }

    protected void populateTable() {
        colLoanDuration.setCellValueFactory(this::getLoanDuration);
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colNumOfRequests.setCellValueFactory(this::getNumberOfRequests);
        tblCopies.setItems(FXCollections.observableArrayList(selectedResource.getCopies()));
    }

    private ObservableStringValue getLoanDuration(TableColumn.CellDataFeatures<Copy, String> copy) {
        Integer loanDurationAsDays = copy.getValue().getLoanDurationAsDays();
        return new ReadOnlyStringWrapper(loanDurationAsDays + " days");
    }

    private ObservableValue<Integer> getNumberOfRequests(TableColumn.CellDataFeatures<Copy, Integer> copy) {
        int numberOfCopyRequests = copy.getValue().getCopyRequests().size();
        return new ReadOnlyObjectWrapper<>(numberOfCopyRequests);
    }

    private void populateFields() {
        ResourceType resourceType = selectedResource.getResourceType();

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
        imageUrl.ifPresent(url -> imgResourcePic.setImage(new Image(url)));
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
        lblOptional1.setLayoutX(87.0);
        lblOptional2.setText("Publisher:");
        lblOptional2.setLayoutX(75.0);
        lblOptional3.setText("Genre:");
        lblOptional3.setLayoutX(92.0);
        lblOptional4.setText("ISBN:");
        lblOptional4.setLayoutX(98.0);
        lblOptional5.setText("Language:");
        lblOptional5.setLayoutX(72.0);
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
        lblOptional1.setLayoutX(82.0);
        lblOptional2.setText("Language:");
        lblOptional2.setLayoutX(73.0);
        lblOptional3.setText("Runtime:");
        lblOptional3.setLayoutX(80.0);
        lblOptional4.setText("Subtitle Language:");
        lblOptional4.setLayoutX(29.0);
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
        lblOptional1.setLayoutX(54.0);
        lblOptional2.setText("Model:");
        lblOptional2.setLayoutX(90.0);
        lblOptional3.setText("Operating System:");
        lblOptional3.setLayoutX(30.0);
    }

    @FXML
    protected abstract void enableButtonsIfResourceSelected();

    @FXML
    private void goBack() {
        FXMLUtils.loadNewScene(getBackButtonFxml());
    }

    protected abstract String getBackButtonFxml();

    protected Copy getSelectedCopy() {
        return tblCopies.getSelectionModel().getSelectedItem();
    }

    @Override
    public void setSelectedItem(Resource selectedItem) {
        this.selectedResource = selectedItem;
    }
    
    public void setCopyService(CopyService copyService) {
        this.copyService = copyService;
        LOGGER.info("{} CopyService set to {}", this.getClass().getSimpleName(), copyService.getClass().getSimpleName());
    }

    public void setLoanService(LoanService loanService) {
        this.loanService = loanService;
        LOGGER.info("{} LoanService set to {}", this.getClass().getSimpleName(), loanService.getClass().getSimpleName());
    }

}
