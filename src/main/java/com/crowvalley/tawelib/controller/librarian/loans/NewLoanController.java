package com.crowvalley.tawelib.controller.librarian.loans;

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
import java.util.List;

public class NewLoanController {

    private static final String LIBRARIAN_HOME_FXML = "/fxml/librarian/librarianHome.fxml";

    private ResourceService<Book> bookService;

    private ResourceService<Dvd> dvdService;

    private ResourceService<Laptop> laptopService;

    private CopyService copyService;

    private UserService userService;

    private LoanService loanService;

    @FXML
    private ChoiceBox<ResourceType> cmbType;

    @FXML
    private ChoiceBox<Resource> cmbResource;

    @FXML
    private ChoiceBox<Copy> cmbCopy;

    @FXML
    private ChoiceBox<String> cmbBorrower;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnCreateLoan;

    public void initialize() {
        cmbType.setOnAction(e -> populateResources());
        cmbResource.setOnAction(e -> populateCopies());
        cmbCopy.setOnAction(e -> setEnabledOnCreateLoanButton());
        cmbBorrower.setOnShowing(e -> populateUsers());
        cmbBorrower.setOnAction(e -> setEnabledOnCreateLoanButton());
        btnCreateLoan.setOnAction(e -> createLoan());
        btnBack.setOnAction(e -> FXMLUtils.loadNewScene(btnBack, LIBRARIAN_HOME_FXML));
    }

    private void populateResources() {
        if (cmbType.getValue().equals(ResourceType.BOOK)) {
            populateResources(bookService);
        }
        if (cmbType.getValue().equals(ResourceType.DVD)) {
            populateResources(dvdService);
        }
        if (cmbType.getValue().equals(ResourceType.LAPTOP)) {
            populateResources(laptopService);
        }
        setEnabledOnCreateLoanButton();
    }

    private void populateResources(ResourceService<? extends Resource> resourceService) {
        cmbResource.setItems(FXCollections.observableArrayList(resourceService.getAll()));
        setEnabledOnCreateLoanButton();
    }

    private void populateCopies() {
        Resource resource = cmbResource.getValue();
        if (resource != null) {
            Long resourceId = resource.getId();
            List<Copy> copiesOfResource = copyService.getAllCopiesNotOnLoanForResource(resourceId);
            cmbCopy.setItems(FXCollections.observableArrayList(copiesOfResource));
            setEnabledOnCreateLoanButton();
        }
    }

    private void populateUsers() {
        cmbBorrower.setItems(FXCollections.observableArrayList(userService.getAllUsernames()));
    }

    private void createLoan() {
        Copy copy = cmbCopy.getValue();
        String username = cmbBorrower.getValue();

        Loan loan = ResourceFactory.createLoanForCopy(copy, username);
        loanService.save(loan);

        FXMLUtils.displayInformationDialogBox("Success!", "Successfully Created New Loan");
        FXMLUtils.loadNewScene(btnCreateLoan, LIBRARIAN_HOME_FXML);
    }

    private void setEnabledOnCreateLoanButton() {
        if (cmbType.getValue() != null && cmbResource.getValue() != null
                && cmbCopy.getValue() != null && cmbBorrower.getValue() != null) {
            btnCreateLoan.setDisable(false);
        } else {
            btnCreateLoan.setDisable(true);
        }
    }

    public void setBookService(ResourceService<Book> bookService) {
        this.bookService = bookService;
    }

    public void setDvdService(ResourceService<Dvd> dvdService) {
        this.dvdService = dvdService;
    }

    public void setLaptopService(ResourceService<Laptop> laptopService) {
        this.laptopService = laptopService;
    }

    public void setCopyService(CopyService copyService) {
        this.copyService = copyService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setLoanService(LoanService loanService) {
        this.loanService = loanService;
    }

}
