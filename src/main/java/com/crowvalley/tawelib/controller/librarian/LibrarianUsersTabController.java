package com.crowvalley.tawelib.controller.librarian;

import com.crowvalley.tawelib.Main;
import com.crowvalley.tawelib.model.fine.Fine;
import com.crowvalley.tawelib.model.fine.Payment;
import com.crowvalley.tawelib.model.user.Address;
import com.crowvalley.tawelib.model.user.User;
import com.crowvalley.tawelib.service.FineService;
import com.crowvalley.tawelib.service.PaymentService;
import com.crowvalley.tawelib.service.UserService;
import com.crowvalley.tawelib.util.FXMLUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableStringValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.commons.lang3.StringUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class LibrarianUsersTabController {

    public static User selectedUser;

    private UserService userService;

    private FineService fineService;

    private PaymentService paymentService;

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
    private Button btnViewUser;

    @FXML
    private Button btnEditUser;

    @FXML
    private Button btnDeleteUser;

    public LibrarianUsersTabController() {
    }

    public void initialize() {
        populateTable();
        FXMLUtils.makeNodesDisabled(btnViewUser, btnEditUser, btnDeleteUser);
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

    private ObservableStringValue getFullName(TableColumn.CellDataFeatures<User, String> user) {
        String fullName = new StringBuilder(user.getValue().getFirstName())
                .append(StringUtils.SPACE)
                .append(user.getValue().getSurname())
                .toString();
        return new SimpleStringProperty(fullName);
    }

    private ObservableStringValue getBalance(TableColumn.CellDataFeatures<User, String> user) {
        String username = user.getValue().getUsername();
        List<Fine> fines = fineService.getAllFinesForUser(username);
        List<Payment> payments = paymentService.getAllPaymentsForUser(username);

        AtomicReference<Double> balance = new AtomicReference<>(0.0);
        fines.forEach(fine -> balance.updateAndGet(v -> v - fine.getAmount()));
        payments.forEach(payment -> balance.updateAndGet(v -> v + payment.getAmount()));
        return new SimpleStringProperty(String.format("£%.2f", balance.get()));
    }

    private ObservableList<User> getUsers() {
        ObservableList<User> users = FXCollections.observableArrayList(userService.getAll());
        users.sort(Comparator.comparing(User::getUsername));
        return users;
    }

    private void setOnActions() {
        tblUsers.setOnMouseClicked(e -> enableButtonsIfUserSelected());
        btnDeleteUser.setOnAction(e -> deleteSelectedUser());
    }

    private void enableButtonsIfUserSelected() {
        if (getSelectedUser() != null) {
            FXMLUtils.makeNodesEnabled(btnViewUser, btnEditUser, btnDeleteUser);
        }
    }

    private void deleteSelectedUser() {
        selectedUser = getSelectedUser();
        String message = String.format("Are you sure you want to delete user '%s'?", selectedUser.getUsername());
        Optional<ButtonType> result = FXMLUtils.displayConfirmationDialogBox("Delete User",  message);

        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (selectedUser.getUsername().equals(Main.currentUser)) {
                FXMLUtils.displayErrorDialogBox("Error Deleting User", "Cannot delete currently logged in user");
            } else {
                userService.delete(selectedUser);
                tblUsers.getItems().remove(selectedUser);
            }
        }
    }

    private User getSelectedUser() {
        return tblUsers.getSelectionModel().getSelectedItem();
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
