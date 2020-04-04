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

    private static final String AVAILABLE = "Available";

    private static final String ON_LOAN = "On loan";

    private static final String ON_LOAN_TO_YOU = "On loan to you";

    @FXML
    private TableColumn<Copy, String> colInfo;

    @FXML
    private Button btnMakeRequest;

    @Override
    protected void populateTable() {
        colInfo.setCellValueFactory(this::getAdditionalInfo);
        super.populateTable();
    }

    private ObservableStringValue getAdditionalInfo(TableColumn.CellDataFeatures<Copy, String> copyRow) {
        String username = UserContextHolder.getLoggedInUser();
        Copy copy = copyRow.getValue();
        Optional<CopyRequest.Status> copyRequestStatus = copyService.getRequestStatusForUser(copy, username);
        String additionalInfo = copyRequestStatus.map(CopyRequest.Status::toString).orElse(getLoanStatus(copy));
        return new ReadOnlyStringWrapper(additionalInfo);
    }

    private String getLoanStatus(Copy copy) {
        Optional<Loan> loan = loanService.getCurrentLoanForCopy(copy.getId());
        if (loan.isPresent()) {
            String username = UserContextHolder.getLoggedInUser();
            if (loan.get().getBorrowerUsername().equals(username)) {
                return ON_LOAN_TO_YOU;
            } else {
                return ON_LOAN;
            }
        } else {
            return AVAILABLE;
        }
    }

    @FXML
    private void makeRequest() {
        Copy selectedCopy = getSelectedCopy();
        Optional<ButtonType> result = FXMLUtils.displayConfirmationDialogBox("Request Copy", "Make request for " + selectedCopy + "?");
        if (result.isPresent() && result.get() == ButtonType.OK) {
            copyService.createCopyRequest(selectedCopy, UserContextHolder.getLoggedInUser());
            tblCopies.getItems().remove(selectedCopy);
            tblCopies.getItems().add(selectedCopy);
        }
    }

    @Override
    protected void enableButtonsIfResourceSelected() {
        Copy copy = getSelectedCopy();
        if (copy != null && getLoanStatus(copy).equals(ON_LOAN)) {
            FXMLUtils.makeNodesEnabled(btnMakeRequest);
        }
    }

    @Override
    protected String getBackButtonFxml() {
        return RESOURCES_PAGE_FXML;
    }

}