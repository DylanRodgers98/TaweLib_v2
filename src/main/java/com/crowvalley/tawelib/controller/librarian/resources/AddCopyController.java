package com.crowvalley.tawelib.controller.librarian.resources;

import com.crowvalley.tawelib.controller.SelectionAwareFXController;
import com.crowvalley.tawelib.model.resource.Copy;
import com.crowvalley.tawelib.model.resource.Resource;
import com.crowvalley.tawelib.model.resource.ResourceFactory;
import com.crowvalley.tawelib.service.CopyService;
import com.crowvalley.tawelib.util.FXMLUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddCopyController implements SelectionAwareFXController<Resource> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AddCopyController.class);

    private static final String VIEW_RESOURCE_FXML = "/fxml/librarian/resources/viewResource.fxml";

    private CopyService copyService;

    private Resource selectedResource;

    @FXML
    private Label lblResource;

    @FXML
    private TextField txtLoanDuration;

    @FXML
    private Button btnAddCopy;

    @FXML
    private Button btnBack;

    @Override
    public void initialize() {
        if (selectedResource != null) {
            lblResource.setText("Resource: " + selectedResource);
            btnAddCopy.setOnAction(e -> addCopy());
            btnBack.setOnAction(e -> loadViewResourcePage());
        }
    }

    private void addCopy() {
        if (txtLoanDuration.getText() != null) {
            Copy copy = ResourceFactory.createCopy(selectedResource, Integer.parseInt(txtLoanDuration.getText()));
            copyService.saveOrUpdate(copy);
            FXMLUtils.displayInformationDialogBox("Success!", "Successfully created new copy of " + selectedResource);
            loadViewResourcePage();
        }
    }

    private void loadViewResourcePage() {
        FXMLUtils.loadNewScene(VIEW_RESOURCE_FXML);
    }

    @Override
    public void setSelectedItem(Resource selectedItem) {
        this.selectedResource = selectedItem;
    }

    public void setCopyService(CopyService copyService) {
        this.copyService = copyService;
        LOGGER.info("{} CopyService set to {}", this.getClass().getSimpleName(), copyService.getClass().getSimpleName());
    }

}
