package com.crowvalley.tawelib.controller.librarian;

import com.crowvalley.tawelib.controller.base.AbstractFinesAndPaymentsController;
import com.crowvalley.tawelib.model.fine.Transaction;
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

import java.util.Comparator;

public class LibrarianFinesAndPaymentsTabController extends AbstractFinesAndPaymentsController {

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

}
