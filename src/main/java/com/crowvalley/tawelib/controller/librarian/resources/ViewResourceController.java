package com.crowvalley.tawelib.controller.librarian.resources;

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
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class ViewResourceController implements SelectionAwareFXController<Resource> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ViewResourceController.class);

    private static final String LIBRARIAN_HOME_FXML = "/fxml/librarian/librarianHome.fxml";

    private static final String ADD_COPY_FXML = "/fxml/librarian/resources/addCopy.fxml";

    private static final String VIEW_COPY_REQUESTS_FXML = "/fxml/librarian/resources/viewCopyRequests.fxml";

    private Resource selectedResource;

    private CopyService copyService;

    private LoanService loanService;

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
    private TableView<Copy> tblCopies;

    @FXML
    private TableColumn<Copy, String> colLoanDuration;

    @FXML
    private TableColumn<Copy, String> colCurrentBorrower;

    @FXML
    private TableColumn<Copy, Integer> colNumOfRequests;

    @FXML
    private Button btnAddCopy;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnViewRequests;

    @FXML
    private Button btnBack;

    @Override
    public void initialize() {
        if (selectedResource != null) {
            populateTable();
            populateFields();
            setOnActions();
        }
    }

    private void populateTable() {
        colLoanDuration.setCellValueFactory(this::getLoanDuration);
        colCurrentBorrower.setCellValueFactory(this::getCurrentBorrower);
        colNumOfRequests.setCellValueFactory(this::getNumberOfRequests);
        tblCopies.setItems(getCopies());
    }

    private ObservableStringValue getLoanDuration(TableColumn.CellDataFeatures<Copy, String> copy) {
        Integer loanDurationAsDays = copy.getValue().getLoanDurationAsDays();
        return new ReadOnlyStringWrapper(loanDurationAsDays + " days");
    }

    private ObservableStringValue getCurrentBorrower(TableColumn.CellDataFeatures<Copy, String> copy) {
        Copy copyFromTable = copy.getValue();
        String username = loanService.getUsernameOfCurrentBorrowerForCopy(copyFromTable.getId());
        return new ReadOnlyStringWrapper(username);
    }

    private ObservableValue<Integer> getNumberOfRequests(TableColumn.CellDataFeatures<Copy, Integer> copy) {
        int numberOfCopyRequests = copy.getValue().getCopyRequests().size();
        return new ReadOnlyObjectWrapper<>(numberOfCopyRequests);
    }

    private ObservableList<Copy> getCopies() {
        Long resourceId = selectedResource.getId();
        List<Copy> copiesOfResource = copyService.getAllCopiesForResource(resourceId);
        return FXCollections.observableArrayList(copiesOfResource);
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
        lblOptional1.setLayoutX(76.0);
        lblOptional2.setText("Publisher:");
        lblOptional2.setLayoutX(64.0);
        lblOptional3.setText("Genre:");
        lblOptional3.setLayoutX(81.0);
        lblOptional4.setText("ISBN:");
        lblOptional4.setLayoutX(87.0);
        lblOptional5.setText("Language:");
        lblOptional5.setLayoutX(61.0);
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
        lblOptional1.setLayoutX(75.0);
        lblOptional2.setText("Language:");
        lblOptional2.setLayoutX(61.0);
        lblOptional3.setText("Runtime:");
        lblOptional3.setLayoutX(69.0);
        lblOptional4.setText("Subtitle Language:");
        lblOptional4.setLayoutX(17.0);
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
        lblOptional1.setLayoutX(43.0);
        lblOptional2.setText("Model:");
        lblOptional2.setLayoutX(79.0);
        lblOptional3.setText("Operating System:");
        lblOptional3.setLayoutX(19.0);
    }

    private void setOnActions() {
        btnAddCopy.setOnAction(e -> openAddCopyPage());
        btnDelete.setOnAction(e -> deleteCopy());
        btnViewRequests.setOnAction(e -> openViewRequestsPage());
        btnBack.setOnAction(e -> FXMLUtils.loadNewScene(LIBRARIAN_HOME_FXML));
        tblCopies.setOnMouseClicked(e -> enableButtonsIfResourceSelected());
    }

    private void openAddCopyPage() {
        try {
            FXMLUtils.loadNewSceneWithSelectedItem(ADD_COPY_FXML, selectedResource);
        } catch (IOException e) {
            LOGGER.error("IOException caught when loading new scene from FXML", e);
            FXMLUtils.displayErrorDialogBox(FXMLUtils.ERROR_LOADING_NEW_SCENE_ERROR_MESSAGE, e.toString());
        }
    }

    private void deleteCopy() {
        Copy selectedCopy = getSelectedCopy();
        String message = String.format("Are you sure you want to delete copy '%s (%s)'?", selectedResource.getTitle(), selectedCopy.toString());
        Optional<ButtonType> result = FXMLUtils.displayConfirmationDialogBox("Delete Copy",  message);

        if (result.isPresent() && result.get() == ButtonType.OK) {
            copyService.delete(selectedCopy);
            tblCopies.getItems().remove(selectedCopy);
            FXMLUtils.makeNodesDisabled(btnDelete, btnViewRequests);
        }
    }

    private void openViewRequestsPage() {
        try {
            FXMLUtils.loadNewSceneWithSelectedItem(VIEW_COPY_REQUESTS_FXML, getSelectedCopy());
        } catch (IOException e) {
            LOGGER.error("IOException caught when loading new scene from FXML", e);
            FXMLUtils.displayErrorDialogBox(FXMLUtils.ERROR_LOADING_NEW_SCENE_ERROR_MESSAGE, e.toString());
        }
    }

    private void enableButtonsIfResourceSelected() {
        if (getSelectedCopy() != null) {
            FXMLUtils.makeNodesEnabled(btnDelete, btnViewRequests);
        }
    }

    private Copy getSelectedCopy() {
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
