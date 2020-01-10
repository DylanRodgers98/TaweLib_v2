package com.crowvalley.tawelib.controller.librarian.resources;

import com.crowvalley.tawelib.model.resource.*;
import com.crowvalley.tawelib.service.LoanService;
import com.crowvalley.tawelib.util.FXMLUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.Timestamp;
import java.util.Optional;

public class ViewCopyRequestsController {

    private static final String VIEW_RESOURCE_FXML = "/fxml/librarian/resources/viewResource.fxml";

    private LoanService loanService;

    private Copy copy;

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

    public void initialize() {
        copy = ViewResourceController.selectedCopy;
        populateTable();
        setOnActions();
    }

    private void populateTable() {
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colRequestDate.setCellValueFactory(new PropertyValueFactory<>("requestDate"));
        tblCopyRequests.setItems(getCopyRequests());
    }

    private ObservableList<CopyRequest> getCopyRequests() {
        return FXCollections.observableArrayList(copy.getCopyRequests());
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
        Optional<Loan> optionalLoan = loanService.getCurrentLoanForCopy(copy.getId());
        if (optionalLoan.isPresent()) {
            FXMLUtils.displayErrorDialogBox("Cannot Approve Copy Request", "Copy already on loan");
        } else {
            createNewLoan();
        }
    }

    private void createNewLoan() {
        CopyRequest selectedCopyRequest = getSelectedCopyRequest();
        String username = selectedCopyRequest.getUsername();

        Loan loan = ResourceFactory.createLoanForCopy(copy, username);
        loanService.save(loan);
        copy.deleteCopyRequestForUser(username);

        tblCopyRequests.getItems().remove(selectedCopyRequest);
    }

    private CopyRequest getSelectedCopyRequest() {
        return tblCopyRequests.getSelectionModel().getSelectedItem();
    }

    public void setLoanService(LoanService loanService) {
        this.loanService = loanService;
    }

}
