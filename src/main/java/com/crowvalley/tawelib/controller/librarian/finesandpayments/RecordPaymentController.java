package com.crowvalley.tawelib.controller.librarian.finesandpayments;

import com.crowvalley.tawelib.model.fine.Payment;
import com.crowvalley.tawelib.model.fine.OutstandingFinesDTO;
import com.crowvalley.tawelib.service.PaymentService;
import com.crowvalley.tawelib.service.UserService;
import com.crowvalley.tawelib.util.FXMLUtils;
import java.text.DecimalFormat;
import java.text.ParseException;

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

    private PaymentService paymentService;

    @FXML
    private ChoiceBox<OutstandingFinesDTO> cmbUsers;

    @FXML
    private TextField txtAmount;

    @FXML
    private Button btnRecord;

    @FXML
    private Button btnBack;

    public void initialize() {
        populateUsersAndFines();
        cmbUsers.setOnAction(e -> enableRecordButtonIfUserSelectedAndAmountTyped());
        txtAmount.setOnKeyReleased(e -> enableRecordButtonIfUserSelectedAndAmountTyped());
        btnRecord.setOnAction(e -> recordPayment());
        btnBack.setOnAction(e -> FXMLUtils.loadNewScene(btnBack, LIBRARIAN_HOME_FXML));
    }

    private void populateUsersAndFines() {
        cmbUsers.setItems(FXCollections.observableArrayList(userService.getAllUsersWithOutstandingFines()));
    }

    private void enableRecordButtonIfUserSelectedAndAmountTyped() {
        if (cmbUsers.getValue() != null && StringUtils.isNotBlank(txtAmount.getText())) {
            FXMLUtils.makeNodesEnabled(btnRecord);
        }
    }

    private void recordPayment() {
        OutstandingFinesDTO outstandingFinesDTO = cmbUsers.getValue();
        String username = outstandingFinesDTO.getUsername();
        double outstandingFines = outstandingFinesDTO.getOutstandingFines();
        String amountString = txtAmount.getText();
        try {
            double amount = DECIMAL_FORMAT.parse(amountString).doubleValue();
            if (amount > outstandingFines) {
                amount = outstandingFines;
            }

            Payment payment = new Payment(username, amount);
            paymentService.save(payment);

            FXMLUtils.displayInformationDialogBox("Payment Successful",
                    "Payment of £" + amount + " made for " + username);
            FXMLUtils.loadNewScene(btnRecord, LIBRARIAN_HOME_FXML);
        } catch (NumberFormatException | ParseException e) {
            LOGGER.error("Record payment failed, amount entered cannot parse to double", e);
            FXMLUtils.displayErrorDialogBox("Record Payment Failed",
                    amountString + " is not a monetary amount");
        }
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
        LOGGER.info("{} UserService set to {}", this.getClass().getSimpleName(), userService.getClass().getSimpleName());
    }

    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
        LOGGER.info("{} PaymentService set to {}", this.getClass().getSimpleName(), paymentService.getClass().getSimpleName());
    }

}
