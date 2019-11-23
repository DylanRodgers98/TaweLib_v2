package com.crowvalley.tawelib.controller.librarian;

import com.crowvalley.tawelib.model.resource.*;
import com.crowvalley.tawelib.service.CopyService;
import com.crowvalley.tawelib.service.LoanService;
import com.crowvalley.tawelib.service.ResourceService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableStringValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Date;
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

    public LibrarianLoansTabController() {
    }

    public void initialize() {
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
        if (copy.isPresent()) {
            Long resourceId = copy.get().getResourceId();
            ResourceType resourceType = copy.get().getResourceType();
            if (resourceType.equals(ResourceType.BOOK)) {
                Optional<Book> book = bookService.get(resourceId);
                if (book.isPresent()) {
                    return new SimpleStringProperty(book.get().getTitle());
                }
            } else if (resourceType.equals(ResourceType.DVD)) {
                Optional<Dvd> dvd = dvdService.get(resourceId);
                if (dvd.isPresent()) {
                    return new SimpleStringProperty(dvd.get().getTitle());
                }
            } else if (resourceType.equals(ResourceType.LAPTOP)) {
                Optional<Laptop> laptop = laptopService.get(resourceId);
                if (laptop.isPresent()) {
                    return new SimpleStringProperty(laptop.get().getTitle());
                }
            }
        }
        return null;
    }

    private ObservableList<Loan> getLoans() {
        return FXCollections.observableArrayList(loanService.getAll());
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
