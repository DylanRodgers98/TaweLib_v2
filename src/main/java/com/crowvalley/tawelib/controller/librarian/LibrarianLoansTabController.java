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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Date;
import java.util.Comparator;
import java.util.Optional;

public class LibrarianLoansTabController {

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
    private Button btnViewLoan;

    @FXML
    private Button btnEditLoan;

    @FXML
    private Button btnEndLoan;

    public LibrarianLoansTabController() {
    }

    public void initialize() {
        populateTable();
        FXMLUtils.makeNodesDisabled(btnViewLoan, btnEditLoan, btnEndLoan);
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
        Long resourceId = copy.getResourceId();
        ResourceType resourceType = copy.getResourceType();
        if (resourceType.equals(ResourceType.BOOK)) {
            return getBookTitle(resourceId);
        }
        if (resourceType.equals(ResourceType.DVD)) {
            return getDvdTitle(resourceId);
        }
        if (resourceType.equals(ResourceType.LAPTOP)) {
            return getLaptopTitle(resourceId);
        }
        return null;
    }

    private ObservableStringValue getBookTitle(Long resourceId) {
        Optional<Book> book = bookService.get(resourceId);
        return book.map(e -> new SimpleStringProperty(e.getTitle())).orElse(null);
    }

    private ObservableStringValue getDvdTitle(Long resourceId) {
        Optional<Dvd> dvd = dvdService.get(resourceId);
        return dvd.map(e -> new SimpleStringProperty(e.getTitle())).orElse(null);
    }

    private ObservableStringValue getLaptopTitle(Long resourceId) {
        Optional<Laptop> laptop = laptopService.get(resourceId);
        return laptop.map(e -> new SimpleStringProperty(e.getTitle())).orElse(null);
    }

    private ObservableList<Loan> getLoans() {
        ObservableList<Loan> loans = FXCollections.observableArrayList(loanService.getAll());
        loans.sort(Comparator.comparing(Loan::getReturnDate, Comparator.nullsFirst(Comparator.reverseOrder())));
        return loans;
    }

    private void setOnActions() {
        tblLoans.setOnMouseClicked(e -> enableButtonsIfLoanSelected());
    }

    private void enableButtonsIfLoanSelected() {
        if (getSelectedLoan() != null) {
            FXMLUtils.makeNodesEnabled(btnViewLoan, btnEditLoan, btnEndLoan);
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
