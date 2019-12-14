package com.crowvalley.tawelib.controller.librarian;

import com.crowvalley.tawelib.model.fine.Fine;
import com.crowvalley.tawelib.model.fine.Payment;
import com.crowvalley.tawelib.model.fine.Transaction;
import com.crowvalley.tawelib.model.fine.TransactionType;
import com.crowvalley.tawelib.service.FineService;
import com.crowvalley.tawelib.service.PaymentService;
import javafx.beans.property.SimpleObjectProperty;
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

import java.util.Comparator;
import java.util.List;

public class LibrarianFinesAndPaymentsTabController {

    private FineService fineService;

    private PaymentService paymentService;

    @FXML
    private TableView<Transaction> tblFinesAndPayments;

    @FXML
    private TableColumn<Transaction, String> colUsername;

    @FXML
    private TableColumn<Transaction, String> colAmount;

    @FXML
    private TableColumn<Transaction, TransactionType> colType;

    @FXML
    private Button btnRecordPayment;

    public LibrarianFinesAndPaymentsTabController() {
    }

    public void initialize() {
        populateTable();
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

    private ObservableValue<TransactionType> getType(TableColumn.CellDataFeatures<Transaction, TransactionType> transaction) {
        if (transaction.getValue() instanceof Fine) {
            return new SimpleObjectProperty<>(TransactionType.FINE);
        }
        if (transaction.getValue() instanceof Payment) {
            return new SimpleObjectProperty<>(TransactionType.PAYMENT);
        }
        return null;
    }

    private ObservableList<Transaction> getFinesAndPayments() {
        List<Fine> fines = fineService.getAll();
        List<Payment> payments = paymentService.getAll();

        ObservableList<Transaction> transactions = FXCollections.observableArrayList();
        transactions.addAll(fines);
        transactions.addAll(payments);
        transactions.sort(Comparator.comparingLong(Transaction::getId));
        return transactions;
    }

    public void setFineService(FineService fineService) {
        this.fineService = fineService;
    }

    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
}
