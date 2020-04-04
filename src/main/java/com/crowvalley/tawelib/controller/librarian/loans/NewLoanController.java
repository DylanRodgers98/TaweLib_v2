package com.crowvalley.tawelib.controller.librarian.loans;

import com.crowvalley.tawelib.controller.FXController;
import com.crowvalley.tawelib.model.resource.*;
import com.crowvalley.tawelib.service.CopyService;
import com.crowvalley.tawelib.service.LoanService;
import com.crowvalley.tawelib.service.ResourceService;
import com.crowvalley.tawelib.service.UserService;
import com.crowvalley.tawelib.util.FXMLUtils;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class NewLoanController implements FXController {

    private static final Logger LOGGER = LoggerFactory.getLogger(NewLoanController.class);

    private static final String LIBRARIAN_HOME_FXML = "/fxml/librarian/librarianHome.fxml";

    private ResourceService resourceService;

    private CopyService copyService;

    private UserService userService;

    private LoanService loanService;

    @FXML
    private ChoiceBox<ResourceType> cmbType;

    @FXML
    private ChoiceBox<ResourceDTO> cmbResource;

    @FXML
    private ChoiceBox<Copy> cmbCopy;

    @FXML
    private ChoiceBox<String> cmbUsers;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnCreateLoan;

    @Override
    public void initialize() {
        cmbUsers.setItems(FXCollections.observableArrayList(userService.getAllUsernames()));
    }

    @FXML
    private void loadLibrarianHome() {
        FXMLUtils.loadNewScene(LIBRARIAN_HOME_FXML);
    }

    @FXML
    private void populateResources() {
        ResourceType resourceType = cmbType.getValue();
        if (resourceType != null) {
            List<ResourceDTO> resources = resourceService.getAllResourceDTOs(resourceType);
            cmbResource.setItems(FXCollections.observableArrayList(resources));
            cmbCopy.setItems(FXCollections.emptyObservableList());
            setEnabledOnCreateLoanButton();
        }
    }

    @FXML
    private void populateCopies() {
        ResourceDTO resource = cmbResource.getValue();
        if (resource != null) {
            Long resourceId = resource.getId();
            List<Copy> copiesOfResource = copyService.getAllCopiesNotOnLoanForResource(resourceId);
            cmbCopy.setItems(FXCollections.observableArrayList(copiesOfResource));
            setEnabledOnCreateLoanButton();
        }
    }

    @FXML
    private void createLoan() {
        Copy copy = cmbCopy.getValue();
        String username = cmbUsers.getValue();

        loanService.createLoanForCopy(copy, username);

        FXMLUtils.displayInformationDialogBox("Success!", "Successfully Created New Loan");
        FXMLUtils.loadNewScene(LIBRARIAN_HOME_FXML);
    }

    @FXML
    private void setEnabledOnCreateLoanButton() {
        if (cmbType.getValue() != null && cmbResource.getValue() != null
                && cmbCopy.getValue() != null && cmbUsers.getValue() != null) {
            btnCreateLoan.setDisable(false);
        } else {
            btnCreateLoan.setDisable(true);
        }
    }

    public void setResourceService(ResourceService resourceService) {
        this.resourceService = resourceService;
        LOGGER.info("{} ResourceService set to {}", this.getClass().getSimpleName(), resourceService.getClass().getSimpleName());
    }

    public void setCopyService(CopyService copyService) {
        this.copyService = copyService;
        LOGGER.info("{} CopyService set to {}", this.getClass().getSimpleName(), copyService.getClass().getSimpleName());
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
        LOGGER.info("{} UserService set to {}", this.getClass().getSimpleName(), userService.getClass().getSimpleName());
    }

    public void setLoanService(LoanService loanService) {
        this.loanService = loanService;
        LOGGER.info("{} LoanService set to {}", this.getClass().getSimpleName(), loanService.getClass().getSimpleName());
    }

}
