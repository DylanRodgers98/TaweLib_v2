package com.crowvalley.tawelib.controller.librarian.finesandpayments;

import com.crowvalley.tawelib.controller.FXController;
import com.crowvalley.tawelib.model.fine.Payment;
import com.crowvalley.tawelib.model.fine.OutstandingFinesDTO;
import com.crowvalley.tawelib.service.TransactionService;
import com.crowvalley.tawelib.service.UserService;
import com.crowvalley.tawelib.util.FXMLUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RecordPaymentController implements FXController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecordPaymentController.class);

    private static final String LIBRARIAN_HOME_FXML = "/fxml/librarian/librarianHome.fxml";

    private TransactionService transactionService;

    @FXML
    private ChoiceBox<OutstandingFinesDTO> cmbUsers;

    @FXML
    private TextField txtAmount;

    @FXML
    private Button btnRecord;

    @Override
    public void initialize() {
        cmbUsers.setItems(FXCollections.observableArrayList(transactionService.getAllUsersWithOutstandingFines()));
    }

    @FXML
    private void loadLibrarianHome() {
        FXMLUtils.loadNewScene(LIBRARIAN_HOME_FXML);
    }

    @FXML
    private void enableRecordButtonIfUserSelectedAndAmountTyped() {
        if (cmbUsers.getValue() != null && StringUtils.isNotBlank(txtAmount.getText())) {
            FXMLUtils.makeNodesEnabled(btnRecord);
        }
    }

    @FXML
    private void recordPayment() {
        OutstandingFinesDTO outstandingFinesDTO = cmbUsers.getValue();
        String username = outstandingFinesDTO.getUsername();
        BigDecimal outstandingFines = outstandingFinesDTO.getOutstandingFines();
        String amountString = txtAmount.getText();
        try {
            BigDecimal amount = new BigDecimal(amountString).setScale(2, RoundingMode.HALF_EVEN);
            if (amount.compareTo(outstandingFines) > 0) {
                amount = outstandingFines;
            }

            Payment payment = new Payment(username, amount, LocalDateTime.now());
            transactionService.save(payment);

            FXMLUtils.displayInformationDialogBox("Payment Successful",
                    "Payment of £" + amount + " made for " + username);
            FXMLUtils.loadNewScene(LIBRARIAN_HOME_FXML);
        } catch (NumberFormatException e) {
            LOGGER.error("Record payment failed, amount entered cannot parse to double", e);
            FXMLUtils.displayErrorDialogBox("Record Payment Failed",
                    amountString + " is not a monetary amount");
        }
    }

    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
        LOGGER.info("{} TransactionService set to {}", this.getClass().getSimpleName(), transactionService.getClass().getSimpleName());
    }

}
