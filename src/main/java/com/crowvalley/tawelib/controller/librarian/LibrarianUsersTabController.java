package com.crowvalley.tawelib.controller.librarian;

import com.crowvalley.tawelib.UserContext;
import com.crowvalley.tawelib.controller.librarian.users.ViewOrEditUserController;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class LibrarianUsersTabController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LibrarianUsersTabController.class);

    private static final String ADD_NEW_USER_FXML = "/fxml/librarian/users/addNewUser.fxml";

    private static final String VIEW_OR_EDIT_USER_FXML = "/fxml/librarian/users/viewOrEditUser.fxml";

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
    private Button btnViewOrEditUser;

    @FXML
    private Button btnDeleteUser;

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
        btnNewUser.setOnAction(e -> FXMLUtils.loadNewScene(btnNewUser, ADD_NEW_USER_FXML));
        btnViewOrEditUser.setOnAction(e -> openViewOrEditUserPage());
        btnDeleteUser.setOnAction(e -> deleteSelectedUser());
    }

    private void enableButtonsIfUserSelected() {
        if (getSelectedUser() != null) {
            FXMLUtils.makeNodesEnabled(btnViewOrEditUser, btnDeleteUser);
        }
    }

    private void openViewOrEditUserPage() {
        try {
            ViewOrEditUserController controller = (ViewOrEditUserController) FXMLUtils.getController(VIEW_OR_EDIT_USER_FXML);
            controller.setSelectedUser(getSelectedUser());
            FXMLUtils.loadNewScene(tblUsers, VIEW_OR_EDIT_USER_FXML);
        } catch (IOException e) {
            LOGGER.error("IOException caught when loading new scene from FXML", e);
            FXMLUtils.displayErrorDialogBox(FXMLUtils.ERROR_LOADING_NEW_SCENE_ERROR_MESSAGE, e.toString());
        } catch (ClassCastException e) {
            LOGGER.error("ClassCastException caught when trying to cast controller from FXML to ViewOrEditUserController", e);
            FXMLUtils.displayErrorDialogBox(FXMLUtils.ERROR_LOADING_NEW_SCENE_ERROR_MESSAGE, e.toString());
        }
    }

    private void deleteSelectedUser() {
        User selectedUser = getSelectedUser();
        String message = String.format("Are you sure you want to delete user '%s'?", selectedUser.getUsername());
        Optional<ButtonType> result = FXMLUtils.displayConfirmationDialogBox("Delete User",  message);

        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (selectedUser.getUsername().equals(UserContext.getLoggedInUser())) {
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
        LOGGER.info("{} UserService set to {}", this.getClass().getSimpleName(), userService.getClass().getSimpleName());
    }

    public void setFineService(FineService fineService) {
        this.fineService = fineService;
        LOGGER.info("{} FineService set to {}", this.getClass().getSimpleName(), fineService.getClass().getSimpleName());
    }

    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
        LOGGER.info("{} PaymentService set to {}", this.getClass().getSimpleName(), paymentService.getClass().getSimpleName());
    }

}
