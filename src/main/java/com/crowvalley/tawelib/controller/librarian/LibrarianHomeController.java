package com.crowvalley.tawelib.controller.librarian;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class LibrarianHomeController {

    private LibrarianResourcesTabController resourcesTabController;

    private LibrarianLoansTabController loansTabController;

    private LibrarianUsersTabController usersTabController;

    private LibrarianFinesAndPaymentsTabController finesAndPaymentsTabController;

    private LibrarianProfileTabController profileTabController;

    private LibrarianLogOutController logOutController;

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab tabResources;

    @FXML
    private Tab tabLoans;

    @FXML
    private Tab tabUsers;

    @FXML
    private Tab tabFinesAndPayments;

    @FXML
    private Tab tabProfile;

    public void initialize() {
        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            if (newTab == tabResources) {
                resourcesTabController.initialize();
            }
            if (newTab == tabLoans) {
                loansTabController.initialize();
            }
            if (newTab == tabUsers) {
                usersTabController.initialize();
            }
            if (newTab == tabFinesAndPayments) {
                finesAndPaymentsTabController.initialize();
            }
            if (newTab == tabProfile) {
                profileTabController.initialize();
            }
        });
    }

    public void setResourcesTabController(LibrarianResourcesTabController resourcesTabController) {
        this.resourcesTabController = resourcesTabController;
    }

    public void setLoansTabController(LibrarianLoansTabController loansTabController) {
        this.loansTabController = loansTabController;
    }

    public void setUsersTabController(LibrarianUsersTabController usersTabController) {
        this.usersTabController = usersTabController;
    }

    public void setFinesAndPaymentsTabController(LibrarianFinesAndPaymentsTabController finesAndPaymentsTabController) {
        this.finesAndPaymentsTabController = finesAndPaymentsTabController;
    }

    public void setProfileTabController(LibrarianProfileTabController profileTabController) {
        this.profileTabController = profileTabController;
    }

    public void setLogOutController(LibrarianLogOutController logOutController) {
        this.logOutController = logOutController;
    }

}
