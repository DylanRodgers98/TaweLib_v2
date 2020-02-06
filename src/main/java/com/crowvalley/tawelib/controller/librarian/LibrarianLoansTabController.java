package com.crowvalley.tawelib.controller.librarian;

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

import java.sql.Date;
import java.util.Comparator;
import java.util.Optional;

public class LibrarianLoansTabController {

    private static final String NEW_LOAN_CONTROLLER_FXML = "/fxml/librarian/loans/newLoan.fxml";

    private LoanService loanService;

    private CopyService copyService;

    private ResourceService<Book> bookService;

    private ResourceService<Dvd> dvdService;

    private ResourceService<Laptop> laptopService;

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
        return copy.map(this::getCopyTitle).orElse(null);
    }

    private ObservableStringValue getCopyTitle(Copy copy) {
        StringBuilder titleBuilder = new StringBuilder();

        Long resourceId = copy.getResourceId();
        ResourceType resourceType = copy.getResourceType();
        if (resourceType.equals(ResourceType.BOOK)) {
            titleBuilder.append(getBookTitle(resourceId));
        }
        if (resourceType.equals(ResourceType.DVD)) {
            titleBuilder.append(getDvdTitle(resourceId));
        }
        if (resourceType.equals(ResourceType.LAPTOP)) {
            titleBuilder.append(getLaptopTitle(resourceId));
        }

        titleBuilder.append(" (")
                .append(copy.getLoanDurationAsDays())
                .append(" day loan)");
        return new SimpleStringProperty(titleBuilder.toString());
    }

    private String getBookTitle(Long resourceId) {
        Optional<Book> book = bookService.get(resourceId);
        return book.map(Book::getTitle).orElse(StringUtils.EMPTY);
    }

    private String getDvdTitle(Long resourceId) {
        Optional<Dvd> dvd = dvdService.get(resourceId);
        return dvd.map(Dvd::getTitle).orElse(StringUtils.EMPTY);
    }

    private String getLaptopTitle(Long resourceId) {
        Optional<Laptop> laptop = laptopService.get(resourceId);
        return laptop.map(Laptop::getTitle).orElse(StringUtils.EMPTY);
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
        if (getSelectedLoan() != null) {
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
    }

    public void setCopyService(CopyService copyService) {
        this.copyService = copyService;
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

}
