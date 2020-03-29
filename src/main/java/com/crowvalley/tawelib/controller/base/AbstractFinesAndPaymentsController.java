package com.crowvalley.tawelib.controller.base;

import com.crowvalley.tawelib.controller.FXController;
import com.crowvalley.tawelib.controller.user.UserFinesAndPaymentsController;
import com.crowvalley.tawelib.model.fine.Fine;
import com.crowvalley.tawelib.model.fine.Payment;
import com.crowvalley.tawelib.model.fine.Transaction;
import com.crowvalley.tawelib.model.resource.Loan;
import com.crowvalley.tawelib.service.ResourceService;
import com.crowvalley.tawelib.service.TransactionService;
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
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Locale;

public abstract class AbstractFinesAndPaymentsController implements FXController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractFinesAndPaymentsController.class);

    private static final NumberFormat CURRENCY_FORMAT = NumberFormat.getCurrencyInstance(Locale.UK);

    protected TransactionService transactionService;

    protected ResourceService resourceService;

    @FXML
    private TableView<Transaction> tblFinesAndPayments;

    @FXML
    private TableColumn<Transaction, String> colAmount;

    @FXML
    private TableColumn<Transaction, LocalDate> colDate;

    @FXML
    private TableColumn<Transaction, String> colType;

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
        colAmount.setCellValueFactory(this::getAmount);
        colDate.setCellValueFactory(this::getDate);
        colType.setCellValueFactory(this::getType);
        tblFinesAndPayments.setItems(getFinesAndPayments());
    }

    protected void setOnActions() {
        dateStart.setOnAction(e -> filterOnDate());
        dateEnd.setOnAction(e -> filterOnDate());
        btnClearDate.setOnAction(e -> clearDate());
    }

    private ObservableStringValue getAmount(TableColumn.CellDataFeatures<Transaction, String> transaction) {
        BigDecimal amount = transaction.getValue().getAmount();
        return new SimpleStringProperty(CURRENCY_FORMAT.format(amount));
    }

    private ObservableValue<LocalDate> getDate(TableColumn.CellDataFeatures<Transaction, LocalDate> transaction) {
        return new SimpleObjectProperty<>(transaction.getValue().getTransactionDate().toLocalDate());
    }

    private ObservableValue<String> getType(TableColumn.CellDataFeatures<Transaction, String> transaction) {
        Transaction transactionValue = transaction.getValue();
        String type = null;
        if (transactionValue instanceof Fine) {
            type = "Fine" + getFineReason((Fine) transactionValue);
        }
        if (transactionValue instanceof Payment) {
            type = "Payment";
        }
        return new SimpleStringProperty(type);
    }

    private String getFineReason(Fine fine) {
        return transactionService.getCopyFromFine(fine)
                .map(copy -> " for late return of " + copy.getResource() + " (" + copy + ")")
                .orElse(StringUtils.EMPTY);
    }

    protected abstract ObservableList<Transaction> getFinesAndPayments();

    private void filterOnDate() {
        LocalDate startDate = dateStart.getValue();
        if (startDate == null) {
            return;
        }

        LocalDate endDate = dateEnd.getValue() != null ? dateEnd.getValue() : LocalDate.now();
        if (endDate.isBefore(startDate)) {
            FXMLUtils.displayErrorDialogBox("Date Error", "End date cannot be before start date");
            return;
        }

        LocalDateTime startDateTime = LocalDateTime.of(dateStart.getValue(), LocalTime.MIDNIGHT);
        LocalDateTime endDateTime = LocalDateTime.of(endDate, LocalTime.of(23, 59, 59));

        tblFinesAndPayments.setItems(search(startDateTime, endDateTime));
    }

    protected abstract ObservableList<Transaction> search(LocalDateTime startDate, LocalDateTime endDate);

    private void clearDate() {
        dateStart.setValue(null);
        dateEnd.setValue(null);
        tblFinesAndPayments.setItems(getFinesAndPayments());
    }

    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
        LOGGER.info("{} FineService set to {}", this.getClass().getSimpleName(), transactionService.getClass().getSimpleName());
    }

    public void setResourceService(ResourceService resourceService) {
        this.resourceService = resourceService;
        LOGGER.info("{} ResourceService set to {}", this.getClass().getSimpleName(), resourceService.getClass().getSimpleName());
    }

}
