package com.crowvalley.tawelib.controller.user;

import com.crowvalley.tawelib.Main;
import com.crowvalley.tawelib.UserContextHolder;
import com.crowvalley.tawelib.controller.InitializableFXController;
import com.crowvalley.tawelib.model.user.User;
import com.crowvalley.tawelib.service.TransactionService;
import com.crowvalley.tawelib.service.UserService;
import com.crowvalley.tawelib.util.FXMLUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Optional;

public class UserHomeController implements InitializableFXController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserHomeController.class);

    private static final String RESOURCES_PAGE_FXML = "/fxml/user/userBrowseResources.fxml";

    private static final String LOANS_PAGE_FXML = "/fxml/user/userLoans.fxml";

    private static final String PROFILE_PAGE_FXML = "/fxml/user/userProfile.fxml";

    private static final String FINES_AND_PAYMENTS_PAGE_FXML = "/fxml/user/userFinesAndPayments.fxml";

    private static final String WELCOME_TEXT = "Welcome Back, ";

    private static final String BALANCE_TEXT = "Account Balance: ";

    private static final NumberFormat CURRENCY_FORMAT = NumberFormat.getCurrencyInstance(Locale.UK);

    private TransactionService transactionService;

    private UserService userService;

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
        btnResources.setOnAction(e -> FXMLUtils.loadNewScene(RESOURCES_PAGE_FXML));
        btnLoans.setOnAction(e -> FXMLUtils.loadNewScene(LOANS_PAGE_FXML));
        btnProfile.setOnAction(e -> openUserProfilePage());
        btnFinesAndPayments.setOnAction(e -> FXMLUtils.loadNewScene(FINES_AND_PAYMENTS_PAGE_FXML));
    }

    private void logOut() {
        UserContextHolder.clear();
        FXMLUtils.loadNewScene(Main.LOGIN_PAGE_FXML);
    }

    private String getBalance() {
        String username = UserContextHolder.getLoggedInUser();
        BigDecimal fines = transactionService.getTotalFinesAmountForUser(username);
        BigDecimal payments = transactionService.getTotalPaymentsAmountForUser(username);
        BigDecimal balance = payments.subtract(fines);
        return CURRENCY_FORMAT.format(balance);
    }

    private void openUserProfilePage() {
        try {
            String username = UserContextHolder.getLoggedInUser();
            Optional<User> user = userService.getWithUsername(username);
            if (user.isPresent()) {
                FXMLUtils.loadNewSceneWithSelectedItem(PROFILE_PAGE_FXML, user.get());
            } else {
                String userNotFoundErrorMessage = String.format("Could not load User '%s' from database", username);
                LOGGER.error(userNotFoundErrorMessage);
                FXMLUtils.displayErrorDialogBox(FXMLUtils.ERROR_LOADING_NEW_SCENE_ERROR_MESSAGE, userNotFoundErrorMessage);
            }
        } catch (IOException e) {
            LOGGER.error("IOException caught when loading new scene from FXML", e);
            FXMLUtils.displayErrorDialogBox(FXMLUtils.ERROR_LOADING_NEW_SCENE_ERROR_MESSAGE, e.toString());
        }
    }

    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
        LOGGER.info("{} TransactionService set to {}", this.getClass().getSimpleName(), transactionService.getClass().getSimpleName());
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
        LOGGER.info("{} UserService set to {}", this.getClass().getSimpleName(), userService.getClass().getSimpleName());
    }

}
