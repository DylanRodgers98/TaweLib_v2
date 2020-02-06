package com.crowvalley.tawelib.controller.librarian.resources;

import com.crowvalley.tawelib.controller.librarian.LibrarianResourcesTabController;
import com.crowvalley.tawelib.model.resource.*;
import com.crowvalley.tawelib.service.CopyService;
import com.crowvalley.tawelib.service.LoanService;
import com.crowvalley.tawelib.util.FXMLUtils;
import com.crowvalley.tawelib.util.ResourceUtils;
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

import java.util.List;
import java.util.Optional;

public class ViewResourceController {

    private static final String LIBRARIAN_HOME_FXML = "/fxml/librarian/librarianHome.fxml";

    private static final String ADD_COPY_FXML = "/fxml/librarian/resources/addCopy.fxml";

    private static final String VIEW_COPY_REQUESTS_FXML = "/fxml/librarian/resources/viewCopyRequests.fxml";

    public static Resource selectedResource;

    public static Copy selectedCopy;

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
    private Button btnBack;

    @FXML
    private Button btnViewRequests;

    public void initialize() {
        selectedResource = LibrarianResourcesTabController.selectedResource;
        populateTable();
        populateFields();
        setOnActions();
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
        ResourceType resourceType = ResourceUtils.getResourceType(selectedResource);

        txtTitle.setText(selectedResource.getTitle());
        txtYear.setText(selectedResource.getYear());
        loadResourcePic(selectedResource);

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
        btnViewRequests.setOnAction(e -> openViewRequestsPage());
        btnBack.setOnAction(e -> FXMLUtils.loadNewScene(btnBack, LIBRARIAN_HOME_FXML));
        tblCopies.setOnMouseClicked(e -> enableViewRequestsButtonIfResourceSelected());
    }

    private void openAddCopyPage() {
        selectedCopy = getSelectedCopy();
        FXMLUtils.loadNewScene(tblCopies, ADD_COPY_FXML);
    }

    private void openViewRequestsPage() {
        selectedCopy = getSelectedCopy();
        FXMLUtils.loadNewScene(tblCopies, VIEW_COPY_REQUESTS_FXML);
    }

    private void enableViewRequestsButtonIfResourceSelected() {
        if (getSelectedCopy() != null) {
            FXMLUtils.makeNodesEnabled(btnViewRequests);
        }
    }

    private Copy getSelectedCopy() {
        return tblCopies.getSelectionModel().getSelectedItem();
    }

    public void setCopyService(CopyService copyService) {
        this.copyService = copyService;
    }

    public void setLoanService(LoanService loanService) {
        this.loanService = loanService;
    }
}
