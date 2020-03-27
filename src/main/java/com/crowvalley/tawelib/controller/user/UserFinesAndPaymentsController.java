package com.crowvalley.tawelib.controller.user;

import com.crowvalley.tawelib.UserContextHolder;
import com.crowvalley.tawelib.controller.base.AbstractFinesAndPaymentsController;
import com.crowvalley.tawelib.model.fine.Transaction;
import com.crowvalley.tawelib.service.ResourceService;
import com.crowvalley.tawelib.service.TransactionService;
import com.crowvalley.tawelib.util.FXMLUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.List;

public class UserFinesAndPaymentsController extends AbstractFinesAndPaymentsController {

    private static final String USER_HOME_FXML = "/fxml/user/userHome.fxml";

    @FXML
    private Button btnBack;

    @Override
    public void initialize() {
        btnBack.setOnAction(e -> FXMLUtils.loadNewScene(USER_HOME_FXML));
        super.initialize();
    }

    @Override
    protected ObservableList<Transaction> getFinesAndPayments() {
        String username = UserContextHolder.getLoggedInUser();
        List<? extends Transaction> transactionsForUser = transactionService.getAllTransactionsForUser(username);
        ObservableList<Transaction> transactions = FXCollections.observableArrayList(transactionsForUser);
        transactions.sort(Comparator.comparingLong(Transaction::getId).reversed());
        return transactions;
    }

}
