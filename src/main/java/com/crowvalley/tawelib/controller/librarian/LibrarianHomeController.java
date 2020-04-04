package com.crowvalley.tawelib.controller.librarian;

import com.crowvalley.tawelib.UserContextHolder;
import com.crowvalley.tawelib.controller.FXController;
import com.crowvalley.tawelib.controller.SelectionAwareFXController;
import com.crowvalley.tawelib.exception.NoSuchUserException;
import com.crowvalley.tawelib.model.user.Librarian;
import com.crowvalley.tawelib.model.user.User;
import com.crowvalley.tawelib.service.UserService;
import com.crowvalley.tawelib.util.FXMLUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class LibrarianHomeController implements FXController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LibrarianHomeController.class);

    private UserService userService;

    private FXController resourcesTabController;

    private FXController loansTabController;

    private FXController usersTabController;

    private FXController finesAndPaymentsTabController;

    private SelectionAwareFXController<User> profileTabController;

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

    @Override
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
                String username = UserContextHolder.getLoggedInUser();
                Optional<User> librarian = userService.getWithUsername(username);
                if (librarian.isPresent()) {
                    profileTabController.setSelectedItem(librarian.get());
                    profileTabController.initialize();
                } else {
                    LOGGER.error("Cannot load profile because could not find Librarian user {}", username);
                    FXMLUtils.displayErrorDialogBox("Error Loading Profile", "Cannot find Librarian user " + username);
                }
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

    public void setUserService(UserService userService) {
        this.userService = userService;
        LOGGER.info("{} UserService set to {}", this.getClass().getSimpleName(), userService.getClass().getSimpleName());
    }

}
