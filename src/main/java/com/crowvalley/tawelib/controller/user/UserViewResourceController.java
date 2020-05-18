package com.crowvalley.tawelib.controller.user;

import com.crowvalley.tawelib.UserContextHolder;
import com.crowvalley.tawelib.controller.base.AbstractViewResourceController;
import com.crowvalley.tawelib.model.resource.Copy;
import com.crowvalley.tawelib.model.resource.CopyRequest;
import com.crowvalley.tawelib.model.resource.Loan;
import com.crowvalley.tawelib.util.FXMLUtils;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableStringValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;

import java.util.Optional;

public class UserViewResourceController extends AbstractViewResourceController {

    private static final String RESOURCES_PAGE_FXML = "/fxml/user/userBrowseResources.fxml";

    private static final String MAKE_REQUEST_BUTTON_TEXT = "Make Request";

    private static final String CANCEL_REQUEST_BUTTON_TEXT = "Cancel Request";

    @FXML
    private TableColumn<Copy, String> colInfo;

    @FXML
    private Button btnMakeOrCancelRequest;

    @Override
    protected void populateTable() {
        colInfo.setCellValueFactory(this::getAdditionalInfo);
        super.populateTable();
    }

    private ObservableStringValue getAdditionalInfo(TableColumn.CellDataFeatures<Copy, String> copyRow) {
        return new ReadOnlyStringWrapper(getCopyStatus(copyRow.getValue()));
    }

    private String getCopyStatus(Copy copy) {
        String username = UserContextHolder.getLoggedInUser();
        return copyService.getRequestStatusForUser(copy, username)
                .map(CopyRequest.Status::toString)
                .orElse(loanService.getLoanStatusForUser(copy, username).toString());
    }

    @FXML
    private void makeOrCancelRequest() {
        if (btnMakeOrCancelRequest.getText().equals(MAKE_REQUEST_BUTTON_TEXT)) {
            makeRequest();
        } else if (btnMakeOrCancelRequest.getText().equals(CANCEL_REQUEST_BUTTON_TEXT)) {
            cancelRequest();
        }
    }

    private void makeRequest() {
        Copy selectedCopy = getSelectedCopy();
        Optional<ButtonType> result = FXMLUtils.displayConfirmationDialogBox("Request Copy", "Make request for " + selectedCopy + "?");
        if (result.isPresent() && result.get() == ButtonType.OK) {
            copyService.createCopyRequest(selectedCopy, UserContextHolder.getLoggedInUser());
            tblCopies.getItems().remove(selectedCopy);
            tblCopies.getItems().add(selectedCopy);
        }
    }

    private void cancelRequest() {
        Copy selectedCopy = getSelectedCopy();
        Optional<ButtonType> result = FXMLUtils.displayConfirmationDialogBox("Cancel Request", "Cancel request for " + selectedCopy + "?");
        if (result.isPresent() && result.get() == ButtonType.OK) {
            copyService.removeCopyRequest(selectedCopy, UserContextHolder.getLoggedInUser());
            tblCopies.getItems().remove(selectedCopy);
            tblCopies.getItems().add(selectedCopy);
        }
    }

    @Override
    protected void enableButtonsIfCopySelected() {
        Copy copy = getSelectedCopy();
        if (copy != null) {
            String status = getCopyStatus(copy);
            if (status.equals(Loan.Status.ON_LOAN.toString())) {
                FXMLUtils.makeNodesEnabled(btnMakeOrCancelRequest);
                btnMakeOrCancelRequest.setText(MAKE_REQUEST_BUTTON_TEXT);
            } else if (status.equals(CopyRequest.Status.REQUESTED.toString())) {
                FXMLUtils.makeNodesEnabled(btnMakeOrCancelRequest);
                btnMakeOrCancelRequest.setText(CANCEL_REQUEST_BUTTON_TEXT);
            } else {
                FXMLUtils.makeNodesDisabled(btnMakeOrCancelRequest);
                btnMakeOrCancelRequest.setText(MAKE_REQUEST_BUTTON_TEXT);
            }
        }
    }

    @Override
    protected String getBackButtonFxml() {
        return RESOURCES_PAGE_FXML;
    }

}