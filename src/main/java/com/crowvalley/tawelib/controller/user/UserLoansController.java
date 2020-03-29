package com.crowvalley.tawelib.controller.user;

import com.crowvalley.tawelib.UserContextHolder;
import com.crowvalley.tawelib.controller.base.AbstractLoansController;
import com.crowvalley.tawelib.model.resource.Loan;
import com.crowvalley.tawelib.service.CopyService;
import com.crowvalley.tawelib.service.LoanService;
import com.crowvalley.tawelib.service.ResourceService;
import com.crowvalley.tawelib.util.FXMLUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

public class UserLoansController extends AbstractLoansController {

    private static final String USER_HOME_FXML = "/fxml/user/userHome.fxml";

    @FXML
    private Button btnBack;

    @Override
    public void initialize() {
        btnBack.setOnAction(e -> FXMLUtils.loadNewScene(USER_HOME_FXML));
        super.initialize();
    }

    @Override
    protected ObservableList<Loan> getLoans() {
        String username = UserContextHolder.getLoggedInUser();
        ObservableList<Loan> loans = FXCollections.observableArrayList(loanService.getAllLoansForUser(username));
        loans.sort(Comparator.comparing(Loan::getReturnDate, Comparator.nullsFirst(Comparator.reverseOrder())));
        return loans;
    }

    @Override
    protected ObservableList<Loan> search(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate == null || endDate == null) {
            return getLoans();
        }

        String username = UserContextHolder.getLoggedInUser();
        List<Loan> queryResult = loanService.search(username, startDate, endDate);
        ObservableList<Loan> loans = FXCollections.observableArrayList(queryResult);
        loans.sort(Loan.getComparator());
        return loans;
    }

}
