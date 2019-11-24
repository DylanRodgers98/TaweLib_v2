package com.crowvalley.tawelib.controller.librarian;

import com.crowvalley.tawelib.Main;
import com.crowvalley.tawelib.model.user.Address;
import com.crowvalley.tawelib.model.user.Librarian;
import com.crowvalley.tawelib.service.LibrarianService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;

import java.util.Optional;

public class LibrarianProfileTabController {

    public static final String UPDATE_PROFILE_BUTTON_TEXT = "Update Profile";

    public static final String SAVE_CHANGES_BUTTON_TEXT = "Save Changes";

    private LibrarianService librarianService;

    private Librarian loggedInLibrarian;

    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtFirstName;

    @FXML
    private TextField txtSurname;

    @FXML
    private TextField txtPhoneNum;

    @FXML
    private TextField txtHouseNum;

    @FXML
    private TextField txtStreet;

    @FXML
    private TextField txtTown;

    @FXML
    private TextField txtCounty;

    @FXML
    private TextField txtPostcode;

    @FXML
    private Button btnSaveOrUpdate;

    public LibrarianProfileTabController() {
    }

    public void initialize() {
        loadProfile();
        disableTextFields();
        btnSaveOrUpdate.setOnAction(e -> saveOrUpdateProfile());
    }

    private void loadProfile() {
        String currentUser = Main.currentUser;
        Optional<Librarian> optionalLibrarian = librarianService.getWithUsername(currentUser);
        if (optionalLibrarian.isPresent()) {
            loggedInLibrarian = optionalLibrarian.get();
            populateTextFields(loggedInLibrarian);
        }
    }

    private void populateTextFields(Librarian librarian) {
        txtUsername.setText(librarian.getUsername());
        txtFirstName.setText(librarian.getFirstName());
        txtSurname.setText(librarian.getSurname());
        txtPhoneNum.setText(librarian.getPhoneNum());
        populateAddressFields(librarian.getAddress());
    }

    private void populateAddressFields(Address address) {
        txtHouseNum.setText(address.getHouseNum());
        txtStreet.setText(address.getStreet());
        txtTown.setText(address.getTown());
        txtCounty.setText(address.getCounty());
        txtPostcode.setText(address.getPostcode());
    }

    private void disableTextFields() {
        setDisableTextFields(true);
    }

    private void enableTextFields() {
        setDisableTextFields(false);
    }

    private void setDisableTextFields(boolean disabled) {
        txtUsername.setDisable(disabled);
        txtFirstName.setDisable(disabled);
        txtSurname.setDisable(disabled);
        txtPhoneNum.setDisable(disabled);
        txtHouseNum.setDisable(disabled);
        txtStreet.setDisable(disabled);
        txtTown.setDisable(disabled);
        txtCounty.setDisable(disabled);
        txtPostcode.setDisable(disabled);
    }

    private void saveOrUpdateProfile() {
        if (btnSaveOrUpdate.getText().equals(UPDATE_PROFILE_BUTTON_TEXT)) {
            startUpdateProfile();
        } else if (btnSaveOrUpdate.getText().equals(SAVE_CHANGES_BUTTON_TEXT)) {
            finishUpdateProfile();
        }
    }

    private void startUpdateProfile() {
        enableTextFields();
        btnSaveOrUpdate.setText(SAVE_CHANGES_BUTTON_TEXT);
    }

    private void finishUpdateProfile() {
        disableTextFields();
        btnSaveOrUpdate.setText(UPDATE_PROFILE_BUTTON_TEXT);
        updateProfile();
        librarianService.update(loggedInLibrarian);
    }

    private void updateProfile() {
        loggedInLibrarian.setUsername(txtUsername.getText());
        loggedInLibrarian.setFirstName(txtFirstName.getText());
        loggedInLibrarian.setSurname(txtSurname.getText());
        loggedInLibrarian.setPhoneNum(txtPhoneNum.getText());
        updateAddress(loggedInLibrarian.getAddress());
    }

    private void updateAddress(Address address) {
        address.setHouseNum(txtHouseNum.getText());
        address.setStreet(txtStreet.getText());
        address.setTown(txtTown.getText());
        address.setCounty(txtCounty.getText());
        address.setPostcode(txtPhoneNum.getText());
    }

    public void setLibrarianService(LibrarianService librarianService) {
        this.librarianService = librarianService;
    }

}
