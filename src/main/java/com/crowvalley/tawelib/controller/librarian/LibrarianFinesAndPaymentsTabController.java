package com.crowvalley.tawelib.controller.librarian;

import com.crowvalley.tawelib.controller.base.AbstractFinesAndPaymentsController;
import com.crowvalley.tawelib.model.fine.Transaction;
import com.crowvalley.tawelib.util.FXMLUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

public class LibrarianFinesAndPaymentsTabController extends AbstractFinesAndPaymentsController {

    private static final String RECORD_PAYMENT_FXML = "/fxml/librarian/finesAndPayments/recordPayment.fxml";

    @FXML
    private TableColumn<Transaction, String> colUsername;

    @FXML
    private TextField txtSearch;

    @Override
    public void initialize() {
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        super.initialize();
    }

    @FXML
    private void searchIfEnterPressed(KeyEvent e) {
        if (e.getCode().equals(KeyCode.ENTER)) {
            search();
        }
    }

    @FXML
    private void loadRecordPaymentPage() {
        FXMLUtils.loadNewScene(RECORD_PAYMENT_FXML);
    }

    @Override
    protected ObservableList<Transaction> getFinesAndPayments() {
        ObservableList<Transaction> transactions = FXCollections.observableArrayList(transactionService.getAll());
        transactions.sort(Comparator.comparingLong(Transaction::getId).reversed());
        return transactions;
    }

    @Override
    protected ObservableList<Transaction> searchByDate(LocalDateTime startDate, LocalDateTime endDate) {
        List<Transaction> queryResult;
        if (StringUtils.isNotBlank(txtSearch.getText())) {
            queryResult = transactionService.search(txtSearch.getText(), startDate, endDate);
        } else if (startDate != null && endDate != null) {
            queryResult = transactionService.search(startDate, endDate);
        } else {
            return getFinesAndPayments();
        }

        ObservableList<Transaction> transactions = FXCollections.observableArrayList(queryResult);
        transactions.sort(Comparator.comparingLong(Transaction::getId).reversed());
        return transactions;
    }

}
