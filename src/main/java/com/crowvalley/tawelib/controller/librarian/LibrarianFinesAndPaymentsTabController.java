package com.crowvalley.tawelib.controller.librarian;

import com.crowvalley.tawelib.controller.FXController;
import com.crowvalley.tawelib.model.fine.Fine;
import com.crowvalley.tawelib.model.fine.Payment;
import com.crowvalley.tawelib.model.fine.Transaction;
import com.crowvalley.tawelib.model.resource.*;
import com.crowvalley.tawelib.service.*;
import com.crowvalley.tawelib.util.FXMLUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableStringValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Comparator;
import java.util.Locale;
import java.util.Optional;

public class LibrarianFinesAndPaymentsTabController implements FXController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LibrarianFinesAndPaymentsTabController.class);

    private static final String RECORD_PAYMENT_FXML = "/fxml/librarian/finesAndPayments/recordPayment.fxml";

    private static final NumberFormat CURRENCY_FORMAT = NumberFormat.getCurrencyInstance(Locale.UK);

    private TransactionService transactionService;

    private ResourceService resourceService;

    @FXML
    private TableView<Transaction> tblFinesAndPayments;

    @FXML
    private TableColumn<Transaction, String> colUsername;

    @FXML
    private TableColumn<Transaction, String> colAmount;

    @FXML
    private TableColumn<Transaction, String> colType;

    @FXML
    private Button btnRecordPayment;

    @Override
    public void initialize() {
        populateTable();
        btnRecordPayment.setOnAction(e -> FXMLUtils.loadNewScene(btnRecordPayment, RECORD_PAYMENT_FXML));
    }

    private void populateTable() {
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colAmount.setCellValueFactory(this::getAmount);
        colType.setCellValueFactory(this::getType);
        tblFinesAndPayments.setItems(getFinesAndPayments());
    }

    private ObservableStringValue getAmount(TableColumn.CellDataFeatures<Transaction, String> transaction) {
        BigDecimal amount = transaction.getValue().getAmount();
        return new SimpleStringProperty(CURRENCY_FORMAT.format(amount));
    }

    private ObservableValue<String> getType(TableColumn.CellDataFeatures<Transaction, String> transaction) {
        Transaction transactionValue = transaction.getValue();
        if (transactionValue instanceof Fine) {
            return new SimpleStringProperty("Fine" + getFineReason(transactionValue));
        }
        if (transactionValue instanceof Payment) {
            return new SimpleStringProperty("Payment");
        }
        return null;
    }

    private String getFineReason(Transaction transaction) {
        Assert.isInstanceOf(Fine.class, transaction, "Transaction is not of type Fine");

        Optional<Copy> optionalCopy = transactionService.getCopyFromFine((Fine) transaction);
        if (optionalCopy.isPresent()) {
            Copy copy = optionalCopy.get();
            Optional<String> optionalTitle = resourceService.getResourceTitleFromCopy(copy.getResourceId(), copy.getResourceType());
            if (optionalTitle.isPresent()) {
                return " for late return of " + optionalTitle.get() + " (" + copy.toString() + ")";
            }
        }
        return StringUtils.EMPTY;
    }

    private ObservableList<Transaction> getFinesAndPayments() {
        ObservableList<Transaction> transactions = FXCollections.observableArrayList(transactionService.getAll());
        transactions.sort(Comparator.comparingLong(Transaction::getId).reversed());
        return transactions;
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
