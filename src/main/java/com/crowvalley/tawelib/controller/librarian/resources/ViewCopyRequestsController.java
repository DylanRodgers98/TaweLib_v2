package com.crowvalley.tawelib.controller.librarian.resources;

import com.crowvalley.tawelib.controller.SelectionAwareFXController;
import com.crowvalley.tawelib.model.resource.*;
import com.crowvalley.tawelib.service.CopyService;
import com.crowvalley.tawelib.service.LoanService;
import com.crowvalley.tawelib.util.FXMLUtils;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableStringValue;
import javafx.beans.value.ObservableValue;
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

import java.time.LocalDate;
import java.util.*;

public class ViewCopyRequestsController implements SelectionAwareFXController<Copy> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ViewCopyRequestsController.class);

    private static final String VIEW_RESOURCE_FXML = "/fxml/librarian/resources/viewResource.fxml";

    private CopyService copyService;

    private LoanService loanService;

    private Copy selectedCopy;

    @FXML
    private TableView<CopyRequest> tblCopyRequests;

    @FXML
    private TableColumn<CopyRequest, String> colUsername;

    @FXML
    private TableColumn<CopyRequest, LocalDate> colRequestDate;

    @FXML
    private TableColumn<CopyRequest, String> colRequestStatus;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnApproveRequest;

    @FXML
    private Label lblCopyTitle;

    @Override
    public void initialize() {
        if (selectedCopy != null) {
            setCopyTitleLabel();
            populateTable();
            setOnActions();
        }
    }

    private void setCopyTitleLabel() {
        lblCopyTitle.setText("Copy: " + selectedCopy.getResource() + " (" + selectedCopy + ")");
    }

    private void populateTable() {
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colRequestDate.setCellValueFactory(this::getRequestDate);
        colRequestStatus.setCellValueFactory(this::getRequestStatus);
        tblCopyRequests.setItems(getCopyRequests());
    }

    private ObservableValue<LocalDate> getRequestDate(TableColumn.CellDataFeatures<CopyRequest, LocalDate> copyRequest) {
        return new SimpleObjectProperty<>(copyRequest.getValue().getRequestDate().toLocalDate());
    }

    private ObservableStringValue getRequestStatus(TableColumn.CellDataFeatures<CopyRequest, String> copyRequest) {
        return new ReadOnlyStringWrapper(copyRequest.getValue().getRequestStatus().toString());
    }

    private ObservableList<CopyRequest> getCopyRequests() {
        ObservableList<CopyRequest> copyRequests = FXCollections.observableArrayList(selectedCopy.getCopyRequests().values());
        copyRequests.sort(CopyRequest.getComparator());
        return copyRequests;
    }

    private void setOnActions() {
        tblCopyRequests.setOnMouseClicked(e -> enableButtonsIfCopyRequestSelected());
        btnBack.setOnAction(e -> FXMLUtils.loadNewScene(VIEW_RESOURCE_FXML));
        btnApproveRequest.setOnAction(e -> approveCopyRequest());
    }

    private void enableButtonsIfCopyRequestSelected() {
        CopyRequest copyRequest = getSelectedCopyRequest();
        if (copyRequest != null && copyRequest.getRequestStatus() == CopyRequest.Status.REQUESTED) {
            Optional<Loan> loan = loanService.getCurrentLoanForCopy(selectedCopy.getId());
            if (loan.isEmpty()) {
                FXMLUtils.makeNodesEnabled(btnApproveRequest);
            }
        }
    }

    private void approveCopyRequest() {
        CopyRequest selectedCopyRequest = getSelectedCopyRequest();
        selectedCopyRequest.setRequestStatus(CopyRequest.Status.READY_FOR_COLLECTION);
        copyService.saveOrUpdate(selectedCopy);

        FXMLUtils.displayInformationDialogBox("Request Approved", "Copy request approved for user '" + selectedCopyRequest.getUsername() + "'");
        FXMLUtils.loadNewScene(VIEW_RESOURCE_FXML);
    }

    private CopyRequest getSelectedCopyRequest() {
        return tblCopyRequests.getSelectionModel().getSelectedItem();
    }

    @Override
    public void setSelectedItem(Copy selectedItem) {
        this.selectedCopy = selectedItem;
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
