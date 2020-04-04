package com.crowvalley.tawelib.controller.user;

import com.crowvalley.tawelib.UserContextHolder;
import com.crowvalley.tawelib.controller.base.AbstractFinesAndPaymentsController;
import com.crowvalley.tawelib.model.fine.Transaction;
import com.crowvalley.tawelib.util.FXMLUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

public class UserFinesAndPaymentsController extends AbstractFinesAndPaymentsController {

    private static final String USER_HOME_FXML = "/fxml/user/userHome.fxml";

    @FXML
    private void loadUserHome() {
        FXMLUtils.loadNewScene(USER_HOME_FXML);
    }

    @Override
    protected ObservableList<Transaction> getFinesAndPayments() {
        String username = UserContextHolder.getLoggedInUser();
        List<? extends Transaction> transactionsForUser = transactionService.getAllTransactionsForUser(username);
        ObservableList<Transaction> transactions = FXCollections.observableArrayList(transactionsForUser);
        transactions.sort(Comparator.comparingLong(Transaction::getId).reversed());
        return transactions;
    }

    @Override
    protected ObservableList<Transaction> searchInternal(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate == null || endDate == null) {
            return getFinesAndPayments();
        }

        String username = UserContextHolder.getLoggedInUser();
        List<Transaction> queryResult = transactionService.search(username, startDate, endDate);
        ObservableList<Transaction> transactions = FXCollections.observableArrayList(queryResult);
        transactions.sort(Comparator.comparingLong(Transaction::getId).reversed());
        return transactions;
    }

}
