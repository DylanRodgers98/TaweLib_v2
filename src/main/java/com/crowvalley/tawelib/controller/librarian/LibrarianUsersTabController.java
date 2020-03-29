package com.crowvalley.tawelib.controller.librarian;

import com.crowvalley.tawelib.UserContextHolder;
import com.crowvalley.tawelib.controller.FXController;
import com.crowvalley.tawelib.model.resource.ResourceDTO;
import com.crowvalley.tawelib.model.user.Address;
import com.crowvalley.tawelib.model.user.User;
import com.crowvalley.tawelib.service.TransactionService;
import com.crowvalley.tawelib.service.UserService;
import com.crowvalley.tawelib.util.FXMLUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableStringValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class LibrarianUsersTabController implements FXController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LibrarianUsersTabController.class);

    private static final String ADD_NEW_USER_FXML = "/fxml/librarian/users/addNewUser.fxml";

    private static final String VIEW_OR_EDIT_USER_FXML = "/fxml/librarian/users/viewOrEditUser.fxml";

    private static final NumberFormat CURRENCY_FORMAT = NumberFormat.getCurrencyInstance(Locale.UK);

    private UserService userService;

    private TransactionService transactionService;

    @FXML
    private TableView<User> tblUsers;

    @FXML
    private TableColumn<User, String> colUsername;

    @FXML
    private TableColumn<User, String> colName;

    @FXML
    private TableColumn<User, String> colPhone;

    @FXML
    private TableColumn<User, Address> colAddress;

    @FXML
    private TableColumn<User, String> colBalance;

    @FXML
    private Button btnNewUser;

    @FXML
    private Button btnViewOrEditUser;

    @FXML
    private Button btnDeleteUser;

    @FXML
    private TextField txtSearch;

    @FXML
    private Button btnSearch;

    @Override
    public void initialize() {
        populateTable();
        FXMLUtils.makeNodesDisabled(btnViewOrEditUser, btnDeleteUser);
        setOnActions();
    }

    private void populateTable() {
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colName.setCellValueFactory(this::getFullName);
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phoneNum"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colBalance.setCellValueFactory(this::getBalance);
        tblUsers.setItems(getUsers());
    }

    private ObservableStringValue getFullName(TableColumn.CellDataFeatures<User, String> userRow) {
        User user = userRow.getValue();
        return new SimpleStringProperty(user.getFirstName() + " " + user.getSurname());
    }

    private ObservableStringValue getBalance(TableColumn.CellDataFeatures<User, String> user) {
        String username = user.getValue().getUsername();
        BigDecimal fines = transactionService.getTotalFinesAmountForUser(username);
        BigDecimal payments = transactionService.getTotalPaymentsAmountForUser(username);
        BigDecimal balance = payments.subtract(fines);
        return new SimpleStringProperty(CURRENCY_FORMAT.format(balance));
    }

    private ObservableList<User> getUsers() {
        return constructObservableList(userService.getAll());
    }

    private ObservableList<User> constructObservableList(List<? extends User> userList) {
        ObservableList<User> users = FXCollections.observableArrayList(userList);
        users.sort(Comparator.comparing(User::getUsername));
        return users;
    }

    private void setOnActions() {
        tblUsers.setOnMouseClicked(e -> enableButtonsIfUserSelected());
        btnNewUser.setOnAction(e -> FXMLUtils.loadNewScene(ADD_NEW_USER_FXML));
        btnViewOrEditUser.setOnAction(e -> openViewOrEditUserPage());
        btnDeleteUser.setOnAction(e -> deleteSelectedUser());
        btnSearch.setOnAction(e -> search());
        txtSearch.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                search();
            }
        });

    }

    private void enableButtonsIfUserSelected() {
        if (getSelectedUser() != null) {
            if (!getSelectedUser().getUsername().equals(UserContextHolder.getLoggedInUser())) {
                FXMLUtils.makeNodesEnabled(btnDeleteUser);
            } else {
                FXMLUtils.makeNodesDisabled(btnDeleteUser);
            }
            FXMLUtils.makeNodesEnabled(btnViewOrEditUser);
        }
    }

    private void openViewOrEditUserPage() {
        try {
            FXMLUtils.loadNewSceneWithSelectedItem(VIEW_OR_EDIT_USER_FXML, getSelectedUser());
        } catch (IOException e) {
            LOGGER.error("IOException caught when loading new scene from FXML", e);
            FXMLUtils.displayErrorDialogBox(FXMLUtils.ERROR_LOADING_NEW_SCENE_ERROR_MESSAGE, e.toString());
        }
    }

    private void deleteSelectedUser() {
        User selectedUser = getSelectedUser();
        String message = String.format("Are you sure you want to delete user '%s'?", selectedUser.getUsername());
        Optional<ButtonType> result = FXMLUtils.displayConfirmationDialogBox("Delete User",  message);

        if (result.isPresent() && result.get() == ButtonType.OK) {
            userService.delete(selectedUser);
            tblUsers.getItems().remove(selectedUser);
        }
    }

    private User getSelectedUser() {
        return tblUsers.getSelectionModel().getSelectedItem();
    }

    private void search() {
        if (StringUtils.isBlank(txtSearch.getText())) {
            tblUsers.setItems(getUsers());
        } else {
            List<User> searchResult = userService.search(txtSearch.getText());
            tblUsers.setItems(constructObservableList(searchResult));
        }
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
        LOGGER.info("{} UserService set to {}", this.getClass().getSimpleName(), userService.getClass().getSimpleName());
    }

    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
        LOGGER.info("{} FineService set to {}", this.getClass().getSimpleName(), transactionService.getClass().getSimpleName());
    }

}
