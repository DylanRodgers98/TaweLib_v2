package com.crowvalley.tawelib.controller.librarian.resources;

import com.crowvalley.tawelib.controller.base.AbstractViewResourceController;
import com.crowvalley.tawelib.model.resource.*;
import com.crowvalley.tawelib.util.FXMLUtils;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableStringValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;

public class ViewResourceController extends AbstractViewResourceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ViewResourceController.class);

    private static final String LIBRARIAN_HOME_FXML = "/fxml/librarian/librarianHome.fxml";

    private static final String ADD_COPY_FXML = "/fxml/librarian/resources/addCopy.fxml";

    private static final String VIEW_COPY_REQUESTS_FXML = "/fxml/librarian/resources/viewCopyRequests.fxml";

    @FXML
    private TableColumn<Copy, String> colCurrentBorrower;

    @FXML
    private Button btnAddCopy;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnViewRequests;

    protected void populateTable() {
        colCurrentBorrower.setCellValueFactory(this::getCurrentBorrower);
        super.populateTable();
    }

    private ObservableStringValue getCurrentBorrower(TableColumn.CellDataFeatures<Copy, String> copy) {
        Copy copyFromTable = copy.getValue();
        Optional<String> username = loanService.getUsernameOfCurrentBorrowerForCopy(copyFromTable.getId());
        return new ReadOnlyStringWrapper(username.orElse(StringUtils.EMPTY));
    }

    protected void setOnActions() {
        btnAddCopy.setOnAction(e -> openAddCopyPage());
        btnDelete.setOnAction(e -> deleteCopy());
        btnViewRequests.setOnAction(e -> openViewRequestsPage());
        super.setOnActions();
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

    @Override
    protected void enableButtonsIfResourceSelected() {
        if (getSelectedCopy() != null) {
            FXMLUtils.makeNodesEnabled(btnDelete, btnViewRequests);
        }
    }

    @Override
    protected String getBackButtonFxml() {
        return LIBRARIAN_HOME_FXML;
    }

}
