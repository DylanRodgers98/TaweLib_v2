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

    private ResourceService resourceService;

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
        StringBuilder titleBuilder = new StringBuilder();

        Optional<? extends Resource> resource = resourceService.get(selectedCopy.getResourceId(), selectedCopy.getResourceType().getClazz());
        resource.ifPresent(value -> titleBuilder.append("Copy: ")
                .append(value.getTitle())
                .append(" (")
                .append(selectedCopy.toString())
                .append(")"));

        lblCopyTitle.setText(titleBuilder.toString());
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
        loanService.saveOrUpdate(loan);
        selectedCopy.deleteCopyRequestForUser(username);

        tblCopyRequests.getItems().remove(selectedCopyRequest);
    }

    private CopyRequest getSelectedCopyRequest() {
        return tblCopyRequests.getSelectionModel().getSelectedItem();
    }

    public void setSelectedCopy(Copy copy) {
        this.selectedCopy = copy;
    }

    public void setResourceService(ResourceService resourceService) {
        this.resourceService = resourceService;
        LOGGER.info("{} ResourceService set to {}", this.getClass().getSimpleName(), resourceService.getClass().getSimpleName());
    }

    public void setLoanService(LoanService loanService) {
        this.loanService = loanService;
        LOGGER.info("{} LoanService set to {}", this.getClass().getSimpleName(), loanService.getClass().getSimpleName());
    }

}
