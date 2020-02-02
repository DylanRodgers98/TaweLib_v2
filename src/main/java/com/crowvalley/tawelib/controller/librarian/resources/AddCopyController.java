package com.crowvalley.tawelib.controller.librarian.resources;

import com.crowvalley.tawelib.model.resource.Copy;
import com.crowvalley.tawelib.model.resource.Resource;
import com.crowvalley.tawelib.model.resource.ResourceFactory;
import com.crowvalley.tawelib.service.CopyService;
import com.crowvalley.tawelib.util.FXMLUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AddCopyController {

    private static final String VIEW_RESOURCE_FXML = "/fxml/librarian/resources/viewResource.fxml";

    private CopyService copyService;

    private Resource resource;

    @FXML
    private Label lblResource;

    @FXML
    private TextField txtLoanDuration;

    @FXML
    private Button btnAddCopy;

    @FXML
    private Button btnBack;

    public void initialize() {
        resource = ViewResourceController.selectedResource;
        lblResource.setText("Resource: " + resource);
        btnAddCopy.setOnAction(e -> addCopy());
        btnBack.setOnAction(e -> loadViewResourcePage());
    }

    private void addCopy() {
        if (txtLoanDuration.getText() != null) {
            Copy copy = ResourceFactory.createCopy(resource, Integer.parseInt(txtLoanDuration.getText()));
            copyService.save(copy);
            FXMLUtils.displayInformationDialogBox("Success!", "Successfully created new copy of resource");
            loadViewResourcePage();
        }
    }

    private void loadViewResourcePage() {
        FXMLUtils.loadNewScene(btnAddCopy, VIEW_RESOURCE_FXML);
    }

    public void setCopyService(CopyService copyService) {
        this.copyService = copyService;
    }

}
