package com.crowvalley.tawelib.controller.librarian;

import com.crowvalley.tawelib.controller.FXController;
import com.crowvalley.tawelib.controller.base.AbstractFinesAndPaymentsController;
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

public class LibrarianFinesAndPaymentsTabController extends AbstractFinesAndPaymentsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LibrarianFinesAndPaymentsTabController.class);

    private static final String RECORD_PAYMENT_FXML = "/fxml/librarian/finesAndPayments/recordPayment.fxml";

    @FXML
    private TableColumn<Transaction, String> colUsername;

    @FXML
    private Button btnRecordPayment;

    @Override
    public void initialize() {
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        btnRecordPayment.setOnAction(e -> FXMLUtils.loadNewScene(RECORD_PAYMENT_FXML));
        super.initialize();
    }

    @Override
    protected ObservableList<Transaction> getFinesAndPayments() {
        ObservableList<Transaction> transactions = FXCollections.observableArrayList(transactionService.getAll());
        transactions.sort(Comparator.comparingLong(Transaction::getId).reversed());
        return transactions;
    }

    @Override
    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
        LOGGER.info("{} FineService set to {}", this.getClass().getSimpleName(), transactionService.getClass().getSimpleName());
    }

    @Override
    public void setResourceService(ResourceService resourceService) {
        this.resourceService = resourceService;
        LOGGER.info("{} ResourceService set to {}", this.getClass().getSimpleName(), resourceService.getClass().getSimpleName());
    }

}
