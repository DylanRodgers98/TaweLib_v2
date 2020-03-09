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

import java.util.Comparator;

public class UserLoansController extends AbstractLoansController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserLoansController.class);

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
    public void setLoanService(LoanService loanService) {
        this.loanService = loanService;
        LOGGER.info("{} LoanService set to {}", this.getClass().getSimpleName(), loanService.getClass().getSimpleName());
    }

    @Override
    public void setCopyService(CopyService copyService) {
        this.copyService = copyService;
        LOGGER.info("{} CopyService set to {}", this.getClass().getSimpleName(), copyService.getClass().getSimpleName());
    }

    @Override
    public void setResourceService(ResourceService resourceService) {
        this.resourceService = resourceService;
        LOGGER.info("{} ResourceService set to {}", this.getClass().getSimpleName(), resourceService.getClass().getSimpleName());
    }

}
