package com.crowvalley.tawelib.controller.base;

import com.crowvalley.tawelib.controller.FXController;
import com.crowvalley.tawelib.model.resource.Loan;
import com.crowvalley.tawelib.service.CopyService;
import com.crowvalley.tawelib.service.LoanService;
import com.crowvalley.tawelib.service.ResourceService;
import com.crowvalley.tawelib.util.FXMLUtils;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableStringValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public abstract class AbstractLoansController implements FXController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractLoansController.class);

    protected LoanService loanService;

    protected CopyService copyService;

    protected ResourceService resourceService;

    @FXML
    protected TableView<Loan> tblLoans;

    @FXML
    private TableColumn<Loan, String> colCopy;

    @FXML
    private TableColumn<Loan, LocalDate> colStartDate;

    @FXML
    private TableColumn<Loan, LocalDate> colEndDate;

    @FXML
    private TableColumn<Loan, LocalDate> colReturnDate;

    @FXML
    private DatePicker dateStart;

    @FXML
    private DatePicker dateEnd;

    @FXML
    private Button btnClearDate;

    @Override
    public void initialize() {
        populateTable();
        setOnActions();
    }

    protected void populateTable() {
        colCopy.setCellValueFactory(this::getCopyTitle);
        colStartDate.setCellValueFactory(this::getStartDate);
        colEndDate.setCellValueFactory(this::getEndDate);
        colReturnDate.setCellValueFactory(this::getReturnDate);
        tblLoans.setItems(getLoans());
    }

    protected void setOnActions() {
        dateStart.setOnAction(e -> search());
        dateEnd.setOnAction(e -> search());
        btnClearDate.setOnAction(e -> clearDate());
    }

    private ObservableStringValue getCopyTitle(TableColumn.CellDataFeatures<Loan, String> loan) {
        Long copyId = loan.getValue().getCopyId();
        String copyTitle = copyService.get(copyId)
                .map(copy -> copy.getResource() + " [" + copy + "]")
                .orElse("[ERROR RETRIEVING COPY (ID: " + copyId + ")]");
        return new SimpleStringProperty(copyTitle);
    }

    private ObservableValue<LocalDate> getStartDate(TableColumn.CellDataFeatures<Loan, LocalDate> loan) {
        return new SimpleObjectProperty<>(loan.getValue().getStartDate().toLocalDate());
    }

    private ObservableValue<LocalDate> getEndDate(TableColumn.CellDataFeatures<Loan, LocalDate> loan) {
        return new SimpleObjectProperty<>(loan.getValue().getEndDate().toLocalDate());
    }

    private ObservableValue<LocalDate> getReturnDate(TableColumn.CellDataFeatures<Loan, LocalDate> loan) {
        LocalDateTime returnDate = loan.getValue().getReturnDate();
        return returnDate == null ? null : new SimpleObjectProperty<>(returnDate.toLocalDate());
    }

    protected abstract ObservableList<Loan> getLoans();

    protected void search() {
        tblLoans.setItems(searchInternal());
    }

    private ObservableList<Loan> searchInternal() {
        LocalDate startDate = dateStart.getValue();
        if (startDate == null) {
            return search(null, null);
        }

        LocalDate endDate = dateEnd.getValue() != null ? dateEnd.getValue() : LocalDate.now();
        if (endDate.isBefore(startDate)) {
            FXMLUtils.displayErrorDialogBox("Date Error", "End date cannot be before start date");
            return getLoans();
        }

        LocalDateTime startDateTime = LocalDateTime.of(dateStart.getValue(), LocalTime.MIDNIGHT);
        LocalDateTime endDateTime = LocalDateTime.of(endDate, LocalTime.of(23, 59, 59));

        return search(startDateTime, endDateTime);
    }

    protected abstract ObservableList<Loan> search(LocalDateTime startDate, LocalDateTime endDate);

    private void clearDate() {
        dateStart.setValue(null);
        dateEnd.setValue(null);
        tblLoans.setItems(getLoans());
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
