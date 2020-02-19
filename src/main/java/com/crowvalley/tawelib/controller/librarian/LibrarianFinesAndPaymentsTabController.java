package com.crowvalley.tawelib.controller.librarian;

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

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class LibrarianFinesAndPaymentsTabController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LibrarianFinesAndPaymentsTabController.class);

    private static final String RECORD_PAYMENT_FXML = "/fxml/librarian/finesAndPayments/recordPayment.fxml";

    private TransactionService transactionService;

    private CopyService copyService;

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
        Double amount = transaction.getValue().getAmount();
        return new SimpleStringProperty(String.format("£%.2f", amount));
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
            Optional<? extends Resource> optionalResource = copyService.getResourceFromCopy(copy);
            if (optionalResource.isPresent()) {
                Resource resource = optionalResource.get();
                return " for late return of " + resource.getTitle() + " (" + copy.toString() + ")";
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

    public void setCopyService(CopyService copyService) {
        this.copyService = copyService;
        LOGGER.info("{} CopyService set to {}", this.getClass().getSimpleName(), copyService.getClass().getSimpleName());
    }

}
