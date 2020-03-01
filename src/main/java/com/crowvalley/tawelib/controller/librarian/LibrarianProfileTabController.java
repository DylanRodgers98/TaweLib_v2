package com.crowvalley.tawelib.controller.librarian;

import com.crowvalley.tawelib.UserContextHolder;
import com.crowvalley.tawelib.controller.FXController;
import com.crowvalley.tawelib.model.user.Address;
import com.crowvalley.tawelib.model.user.Librarian;
import com.crowvalley.tawelib.model.user.User;
import com.crowvalley.tawelib.service.UserService;
import com.crowvalley.tawelib.util.ImageUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class LibrarianProfileTabController implements FXController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LibrarianProfileTabController.class);

    private static final String UPDATE_PROFILE_BUTTON_TEXT = "Update Profile";

    private static final String SAVE_CHANGES_BUTTON_TEXT = "Save Changes";

    private static final String PROFILE_PICTURE_DIRECTORY_NAME = "profile";

    private UserService userService;

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

    @FXML
    private ImageView imgProfilePic;

    @FXML
    private Button btnChangePic;

    @Override
    public void initialize() {
        loadProfile();
        disableTextFields();
        btnSaveOrUpdate.setOnAction(e -> saveOrUpdateProfile());
        btnChangePic.setOnAction(e -> chooseImage());
    }

    private void loadProfile() {
        String currentUser = UserContextHolder.getLoggedInUser();
        Optional<? extends User> optionalLibrarian = userService.getWithUsername(currentUser);
        if (optionalLibrarian.isPresent()) {
            loggedInLibrarian = (Librarian) optionalLibrarian.get();
            populateTextFields(loggedInLibrarian);
            loadProfilePic(loggedInLibrarian);
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

    private void loadProfilePic(Librarian librarian) {
        Optional<String> imageUrl = librarian.getProfileImagePath();
        imageUrl.ifPresent(e -> imgProfilePic.setImage(new Image(e)));
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
        userService.saveOrUpdate(loggedInLibrarian);
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
        address.setPostcode(txtPostcode.getText());
    }

    private void chooseImage() {
        ImageUtils.chooseImage("Choose Profile Picture", PROFILE_PICTURE_DIRECTORY_NAME, imgProfilePic);
        Image image = imgProfilePic.getImage();
        if (image != null) {
            loggedInLibrarian.setProfileImagePath(image.getUrl());
            userService.saveOrUpdate(loggedInLibrarian);
        }
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
        LOGGER.info("{} LibrarianService set to {}", this.getClass().getSimpleName(), userService.getClass().getSimpleName());
    }

}
