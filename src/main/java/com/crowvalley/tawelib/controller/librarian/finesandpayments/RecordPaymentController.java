package com.crowvalley.tawelib.controller.librarian.finesandpayments;

import com.crowvalley.tawelib.model.fine.Fine;
import com.crowvalley.tawelib.model.fine.Payment;
import com.crowvalley.tawelib.service.FineService;
import com.crowvalley.tawelib.service.PaymentService;
import com.crowvalley.tawelib.service.UserService;
import com.crowvalley.tawelib.util.FXMLUtils;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RecordPaymentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecordPaymentController.class);

    private static final String LIBRARIAN_HOME_FXML = "/fxml/librarian/librarianHome.fxml";

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#,###.##");

    private UserService userService;

    private FineService fineService;

    private PaymentService paymentService;

    @FXML
    private ChoiceBox<String> cmbUsers;

    @FXML
    private TextField txtAmount;

    @FXML
    private Button btnRecord;

    @FXML
    private Button btnBack;

    public void initialize() {
        populateUsers();
        cmbUsers.setOnAction(e -> enableRecordButtonIfUserSelectedAndAmountTyped());
        txtAmount.setOnKeyReleased(e -> enableRecordButtonIfUserSelectedAndAmountTyped());
        btnRecord.setOnAction(e -> recordPayment());
        btnBack.setOnAction(e -> FXMLUtils.loadNewScene(btnBack, LIBRARIAN_HOME_FXML));
    }

    private void populateUsers() {
        List<String> usernamesAndFines = new ArrayList<>();
        for (Map.Entry<String, List<Fine>> entry : userService.getAllUsersWithFines().entrySet()) {
            String username = entry.getKey();
            List<Fine> fines = entry.getValue();
            double totalFineAmount = fines.stream().mapToDouble(Fine::getAmount).sum();
            usernamesAndFines.add(username + " (Outstanding: £" + DECIMAL_FORMAT.format(totalFineAmount) + ")");
        }
        cmbUsers.setItems(FXCollections.observableArrayList(usernamesAndFines));
    }

    private void enableRecordButtonIfUserSelectedAndAmountTyped() {
        if (cmbUsers.getValue() != null && StringUtils.isNotBlank(txtAmount.getText())) {
            FXMLUtils.makeNodesEnabled(btnRecord);
        }
    }

    private void recordPayment() {
        String username = cmbUsers.getValue();
        String amountString = txtAmount.getText();
        try {
            String formattedAmount = DECIMAL_FORMAT.format(amountString);
            Payment payment = new Payment(username, Double.valueOf(formattedAmount));
            paymentService.save(payment);
            FXMLUtils.displayInformationDialogBox("Payment Successful",
                    "Payment of £" + formattedAmount + " made for " + username);
            FXMLUtils.loadNewScene(btnRecord, LIBRARIAN_HOME_FXML);
        } catch (NumberFormatException e) {
            LOGGER.error("Record payment failed, amount entered cannot parse to double", e);
            FXMLUtils.displayErrorDialogBox("Record Payment Failed",
                    amountString + " is not a monetary amount");
        }
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setFineService(FineService fineService) {
        this.fineService = fineService;
    }

    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

}
