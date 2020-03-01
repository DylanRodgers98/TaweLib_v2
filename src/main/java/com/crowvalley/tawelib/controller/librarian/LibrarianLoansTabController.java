package com.crowvalley.tawelib.controller.librarian;

import com.crowvalley.tawelib.controller.FXController;
import com.crowvalley.tawelib.model.resource.*;
import com.crowvalley.tawelib.service.CopyService;
import com.crowvalley.tawelib.service.LoanService;
import com.crowvalley.tawelib.service.ResourceService;
import com.crowvalley.tawelib.util.FXMLUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableStringValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.util.Comparator;
import java.util.Optional;

public class LibrarianLoansTabController implements FXController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LibrarianLoansTabController.class);

    private static final String NEW_LOAN_CONTROLLER_FXML = "/fxml/librarian/loans/newLoan.fxml";

    private LoanService loanService;

    private CopyService copyService;

    private ResourceService resourceService;

    @FXML
    private TableView<Loan> tblLoans;

    @FXML
    private TableColumn<Loan, String> colCopy;

    @FXML
    private TableColumn<Loan, String> colBorrower;

    @FXML
    private TableColumn<Loan, Date> colStartDate;

    @FXML
    private TableColumn<Loan, Date> colEndDate;

    @FXML
    private TableColumn<Loan, Date> colReturnDate;

    @FXML
    private Button btnNewLoan;

    @FXML
    private Button btnEndLoan;

    @Override
    public void initialize() {
        populateTable();
        FXMLUtils.makeNodesDisabled(btnEndLoan);
        setOnActions();
    }

    private void populateTable() {
        colCopy.setCellValueFactory(this::getCopyTitle);
        colBorrower.setCellValueFactory(new PropertyValueFactory<>("borrowerUsername"));
        colStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        colEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        colReturnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        tblLoans.setItems(getLoans());
    }

    private ObservableStringValue getCopyTitle(TableColumn.CellDataFeatures<Loan, String> loan) {
        Long copyId = loan.getValue().getCopyId();
        Optional<Copy> copy = copyService.get(copyId);
        return new SimpleStringProperty(copy.map(this::getCopyTitle)
                .orElse("[ERROR RETRIEVING COPY (ID: " + copyId + ")]"));
    }

    private String getCopyTitle(Copy copy) {
        Optional<String> optionalTitle = resourceService.getResourceTitleFromCopy(copy.getResourceId(), copy.getResourceType());
        return optionalTitle.map(title -> title + " (" + copy + ")")
                .orElse("[ERROR RETRIEVING COPY (ID: " + copy.getId() + ")]");
    }

    private ObservableList<Loan> getLoans() {
        ObservableList<Loan> loans = FXCollections.observableArrayList(loanService.getAll());
        loans.sort(Comparator.comparing(Loan::getReturnDate, Comparator.nullsFirst(Comparator.reverseOrder())));
        return loans;
    }

    private void setOnActions() {
        tblLoans.setOnMouseClicked(e -> enableButtonsIfLoanSelected());
        btnNewLoan.setOnAction(e -> FXMLUtils.loadNewScene(btnNewLoan, NEW_LOAN_CONTROLLER_FXML));
        btnEndLoan.setOnAction(e -> endLoan());
    }

    private void enableButtonsIfLoanSelected() {
        Loan loan = getSelectedLoan();
        if (loan != null && loan.getReturnDate() == null) {
            FXMLUtils.makeNodesEnabled(btnEndLoan);
        }
    }

    private void endLoan() {
        Optional<ButtonType> result = FXMLUtils.displayConfirmationDialogBox("End Loan", "Are you sure you want to end the loan?");
        if (result.isPresent() && result.get() == ButtonType.OK) {
            loanService.endLoan(getSelectedLoan());
            initialize();
        }
    }

    private Loan getSelectedLoan() {
        return tblLoans.getSelectionModel().getSelectedItem();
    }

    public void setLoanService(LoanService loanService) {
        this.loanService = loanService;
        LOGGER.info("{} LoanService set to {}", this.getClass().getSimpleName(), loanService.getClass().getSimpleName());
    }

    public void setCopyService(CopyService copyService) {
        this.copyService = copyService;
        LOGGER.info("{} CopyService set to {}", this.getClass().getSimpleName(), copyService.getClass().getSimpleName());
    }

    public void setResourceService(ResourceService resourceService) {
        this.resourceService = resourceService;
        LOGGER.info("{} ResourceService set to {}", this.getClass().getSimpleName(), resourceService.getClass().getSimpleName());
    }

}
