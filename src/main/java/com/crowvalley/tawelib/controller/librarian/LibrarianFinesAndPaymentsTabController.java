package com.crowvalley.tawelib.controller.librarian;

import com.crowvalley.tawelib.controller.base.AbstractFinesAndPaymentsController;
import com.crowvalley.tawelib.model.fine.Transaction;
import com.crowvalley.tawelib.model.resource.Loan;
import com.crowvalley.tawelib.service.*;
import com.crowvalley.tawelib.util.FXMLUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

public class LibrarianFinesAndPaymentsTabController extends AbstractFinesAndPaymentsController {

    private static final String RECORD_PAYMENT_FXML = "/fxml/librarian/finesAndPayments/recordPayment.fxml";

    @FXML
    private TableColumn<Transaction, String> colUsername;

    @FXML
    private Button btnRecordPayment;

    @Override
    protected void populateTable() {
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        super.populateTable();
    }

    @Override
    protected void setOnActions() {
        btnRecordPayment.setOnAction(e -> FXMLUtils.loadNewScene(RECORD_PAYMENT_FXML));
        super.setOnActions();
    }

    @Override
    protected ObservableList<Transaction> getFinesAndPayments() {
        ObservableList<Transaction> transactions = FXCollections.observableArrayList(transactionService.getAll());
        transactions.sort(Comparator.comparingLong(Transaction::getId).reversed());
        return transactions;
    }

    @Override
    protected ObservableList<Transaction> search(LocalDateTime startDate, LocalDateTime endDate) {
        List<Transaction> queryResult = transactionService.search(startDate, endDate);
        ObservableList<Transaction> transactions = FXCollections.observableArrayList(queryResult);
        transactions.sort(Comparator.comparingLong(Transaction::getId).reversed());
        return transactions;
    }

}
