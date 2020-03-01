package com.crowvalley.tawelib.controller.user;

import com.crowvalley.tawelib.Main;
import com.crowvalley.tawelib.UserContextHolder;
import com.crowvalley.tawelib.controller.FXController;
import com.crowvalley.tawelib.service.TransactionService;
import com.crowvalley.tawelib.util.FXMLUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class UserHomeController implements FXController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserHomeController.class);

    private static final String RESOURCES_PAGE_FXML = "/fxml/user/userBrowseResources.fxml";

    private static final String LOANS_PAGE_FXML = "/fxml/user/userLoans.fxml";

    private static final String PROFILE_PAGE_FXML = "/fxml/user/userProfile.fxml";

    private static final String FINES_AND_PAYMENTS_PAGE_FXML = "/fxml/user/userFinesAndPayments.fxml";

    private static final String WELCOME_TEXT = "Welcome Back, ";

    private static final String BALANCE_TEXT = "Account Balance: ";

    private static final NumberFormat CURRENCY_FORMAT = NumberFormat.getCurrencyInstance(Locale.UK);

    private TransactionService transactionService;

    @FXML
    private Label lblWelcome;

    @FXML
    private Label lblLogOut;

    @FXML
    private Label lblBalance;

    @FXML
    private Button btnResources;

    @FXML
    private Button btnLoans;

    @FXML
    private Button btnProfile;

    @FXML
    private Button btnFinesAndPayments;

    @Override
    public void initialize() {
        lblWelcome.setText(WELCOME_TEXT + UserContextHolder.getLoggedInUser());
        lblBalance.setText(BALANCE_TEXT + getBalance());
        lblLogOut.setOnMouseClicked(e -> logOut());
        btnResources.setOnAction(e -> FXMLUtils.loadNewScene(btnResources, RESOURCES_PAGE_FXML));
        btnLoans.setOnAction(e -> FXMLUtils.loadNewScene(btnLoans, LOANS_PAGE_FXML));
        btnProfile.setOnAction(e -> FXMLUtils.loadNewScene(btnProfile, PROFILE_PAGE_FXML));
        btnFinesAndPayments.setOnAction(e -> FXMLUtils.loadNewScene(btnFinesAndPayments, FINES_AND_PAYMENTS_PAGE_FXML));
    }

    private void logOut() {
        UserContextHolder.clear();
        FXMLUtils.loadNewScene(lblLogOut, Main.LOGIN_PAGE_FXML);
    }

    private String getBalance() {
        String username = UserContextHolder.getLoggedInUser();
        BigDecimal fines = transactionService.getTotalFinesAmountForUser(username);
        BigDecimal payments = transactionService.getTotalPaymentsAmountForUser(username);
        BigDecimal balance = fines.subtract(payments);
        return CURRENCY_FORMAT.format(balance);
    }

    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
        LOGGER.info("{} TransactionService set to {}", this.getClass().getSimpleName(), transactionService.getClass().getSimpleName());
    }

}
