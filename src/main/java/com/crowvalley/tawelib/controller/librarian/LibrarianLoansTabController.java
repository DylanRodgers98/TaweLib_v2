package com.crowvalley.tawelib.controller.librarian;

import com.crowvalley.tawelib.controller.base.AbstractLoansController;
import com.crowvalley.tawelib.model.resource.*;
import com.crowvalley.tawelib.util.FXMLUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class LibrarianLoansTabController extends AbstractLoansController {

    private static final String NEW_LOAN_FXML = "/fxml/librarian/loans/newLoan.fxml";

    @FXML
    private TableColumn<Loan, String> colBorrower;

    @FXML
    private Button btnEndLoan;

    @FXML
    private TextField txtSearch;

    @Override
    public void initialize() {
        colBorrower.setCellValueFactory(new PropertyValueFactory<>("borrowerUsername"));
        txtSearch.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                search();
            }
        });
        super.initialize();
    }

    @FXML
    private void loanNewLoanPage() {
        FXMLUtils.loadNewScene(NEW_LOAN_FXML);
    }

    @FXML
    private void enableButtonsIfLoanSelected() {
        Loan loan = getSelectedLoan();
        if (loan != null && loan.getReturnDate() == null) {
            FXMLUtils.makeNodesEnabled(btnEndLoan);
        }
    }

    @FXML
    private void endLoan() {
        Optional<ButtonType> result = FXMLUtils.displayConfirmationDialogBox("End Loan", "Are you sure you want to end the loan?");
        if (result.isPresent() && result.get() == ButtonType.OK) {
            loanService.endLoan(getSelectedLoan());
            initialize();
        }
    }

    private Loan getSelectedLoan() {
        return tblLoans.getSelectionModel().getSelectedItem();
    }

    @Override
    protected ObservableList<Loan> getLoans() {
        ObservableList<Loan> loans = FXCollections.observableArrayList(loanService.getAll());
        loans.sort(Loan.getComparator());
        return loans;
    }

    @Override
    protected ObservableList<Loan> searchInternal(LocalDateTime startDate, LocalDateTime endDate) {
        List<Loan> queryResult;
        if (StringUtils.isNotBlank(txtSearch.getText())) {
            queryResult = loanService.search(txtSearch.getText(), startDate, endDate);
        } else if (startDate != null && endDate != null) {
            queryResult = loanService.search(startDate, endDate);
        } else {
            return getLoans();
        }

        ObservableList<Loan> loans = FXCollections.observableArrayList(queryResult);
        loans.sort(Loan.getComparator());
        return loans;
    }

}
