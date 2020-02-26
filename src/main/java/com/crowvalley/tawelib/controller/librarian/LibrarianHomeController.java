package com.crowvalley.tawelib.controller.librarian;

import com.crowvalley.tawelib.controller.FXController;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class LibrarianHomeController implements FXController {

    private FXController resourcesTabController;

    private FXController loansTabController;

    private FXController usersTabController;

    private FXController finesAndPaymentsTabController;

    private FXController profileTabController;

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

}
