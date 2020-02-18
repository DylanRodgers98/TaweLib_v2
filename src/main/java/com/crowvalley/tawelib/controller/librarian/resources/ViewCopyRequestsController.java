package com.crowvalley.tawelib.controller.librarian.resources;

import com.crowvalley.tawelib.model.resource.*;
import com.crowvalley.tawelib.service.LoanService;
import com.crowvalley.tawelib.service.ResourceService;
import com.crowvalley.tawelib.util.FXMLUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.Optional;

public class ViewCopyRequestsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ViewCopyRequestsController.class);

    private static final String VIEW_RESOURCE_FXML = "/fxml/librarian/resources/viewResource.fxml";

    private ResourceService<Book> bookService;

    private ResourceService<Dvd> dvdService;

    private ResourceService<Laptop> laptopService;

    private LoanService loanService;

    private Copy selectedCopy;

    @FXML
    private TableView<CopyRequest> tblCopyRequests;

    @FXML
    private TableColumn<CopyRequest, String> colUsername;

    @FXML
    private TableColumn<CopyRequest, Timestamp> colRequestDate;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnApproveRequest;

    @FXML
    private Label lblCopyTitle;

    public void initialize() {
        if (selectedCopy != null) {
            setCopyTitleLabel();
            populateTable();
            setOnActions();
        }
    }

    private void setCopyTitleLabel() {
        String resourceTitle = null;
        Long resourceId = selectedCopy.getResourceId();
        ResourceType resourceType = selectedCopy.getResourceType();
        if (resourceType == ResourceType.BOOK) {
            Optional<Book> book = bookService.get(resourceId);
            if (book.isPresent()) {
                resourceTitle = book.get().getTitle();
            }
        }
        if (resourceType == ResourceType.DVD) {
            Optional<Dvd> dvd = dvdService.get(resourceId);
            if (dvd.isPresent()) {
                resourceTitle = dvd.get().getTitle();
            }
        }
        if (resourceType == ResourceType.LAPTOP) {
            Optional<Laptop> laptop = laptopService.get(resourceId);
            if (laptop.isPresent()) {
                resourceTitle = laptop.get().getTitle();
            }
        }
        lblCopyTitle.setText("Copy: " + resourceTitle + " (" + selectedCopy.toString() + ")");
    }

    private void populateTable() {
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colRequestDate.setCellValueFactory(new PropertyValueFactory<>("requestDate"));
        tblCopyRequests.setItems(getCopyRequests());
    }

    private ObservableList<CopyRequest> getCopyRequests() {
        return FXCollections.observableArrayList(selectedCopy.getCopyRequests());
    }

    private void setOnActions() {
        tblCopyRequests.setOnMouseClicked(e -> enableButtonsIfCopyRequestSelected());
        btnBack.setOnAction(e -> FXMLUtils.loadNewScene(btnBack, VIEW_RESOURCE_FXML));
        btnApproveRequest.setOnAction(e -> approveCopyRequest());
    }

    private void enableButtonsIfCopyRequestSelected() {
        if (getSelectedCopyRequest() != null) {
            FXMLUtils.makeNodesEnabled(btnApproveRequest);
        }
    }

    private void approveCopyRequest() {
        Optional<Loan> optionalLoan = loanService.getCurrentLoanForCopy(selectedCopy.getId());
        if (optionalLoan.isPresent()) {
            FXMLUtils.displayErrorDialogBox("Cannot Approve Copy Request", "Copy already on loan");
        } else {
            createNewLoan();
        }
    }

    private void createNewLoan() {
        CopyRequest selectedCopyRequest = getSelectedCopyRequest();
        String username = selectedCopyRequest.getUsername();

        Loan loan = ResourceFactory.createLoanForCopy(selectedCopy, username);
        loanService.save(loan);
        selectedCopy.deleteCopyRequestForUser(username);

        tblCopyRequests.getItems().remove(selectedCopyRequest);
    }

    private CopyRequest getSelectedCopyRequest() {
        return tblCopyRequests.getSelectionModel().getSelectedItem();
    }

    public void setSelectedCopy(Copy copy) {
        this.selectedCopy = copy;
    }

    public void setBookService(ResourceService<Book> bookService) {
        this.bookService = bookService;
        LOGGER.info("{} BookService set to {}", this.getClass().getSimpleName(), bookService.getClass().getSimpleName());
    }

    public void setDvdService(ResourceService<Dvd> dvdService) {
        this.dvdService = dvdService;
        LOGGER.info("{} DvdService set to {}", this.getClass().getSimpleName(), dvdService.getClass().getSimpleName());
    }

    public void setLaptopService(ResourceService<Laptop> laptopService) {
        this.laptopService = laptopService;
        LOGGER.info("{} LaptopService set to {}", this.getClass().getSimpleName(), laptopService.getClass().getSimpleName());
    }

    public void setLoanService(LoanService loanService) {
        this.loanService = loanService;
        LOGGER.info("{} LoanService set to {}", this.getClass().getSimpleName(), loanService.getClass().getSimpleName());
    }

}
