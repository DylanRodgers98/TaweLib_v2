package com.crowvalley.tawelib.controller.librarian.users;

import com.crowvalley.tawelib.model.user.Address;
import com.crowvalley.tawelib.model.user.User;
import com.crowvalley.tawelib.service.UserService;
import com.crowvalley.tawelib.util.FXMLUtils;
import com.crowvalley.tawelib.util.ImageUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class ViewOrEditUserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ViewOrEditUserController.class);

    private static final String LIBRARIAN_HOME_FXML = "/fxml/librarian/librarianHome.fxml";

    private static final String UPDATE_PROFILE_BUTTON_TEXT = "Update Profile";

    private static final String SAVE_CHANGES_BUTTON_TEXT = "Save Changes";

    private static final String FILE_CHOOSER_TITLE = "Choose Profile Picture";

    private static final String PROFILE_PICTURE_DIRECTORY_NAME = "profile";

    private UserService userService;

    private User selectedUser;

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

    @FXML
    private Button btnBack;

    public void initialize() {
        if (selectedUser != null) {
            loadProfile();
            disableTextFields();
            btnSaveOrUpdate.setOnAction(e -> saveOrUpdateProfile());
            btnChangePic.setOnAction(e -> chooseImage());
            btnBack.setOnAction(e -> FXMLUtils.loadNewScene(btnBack, LIBRARIAN_HOME_FXML));
        }
    }

    private void loadProfile() {
        populateTextFields(selectedUser);
        loadProfilePic(selectedUser);
    }

    private void populateTextFields(User user) {
        txtUsername.setText(user.getUsername());
        txtFirstName.setText(user.getFirstName());
        txtSurname.setText(user.getSurname());
        txtPhoneNum.setText(user.getPhoneNum());
        populateAddressFields(user.getAddress());
    }

    private void populateAddressFields(Address address) {
        txtHouseNum.setText(address.getHouseNum());
        txtStreet.setText(address.getStreet());
        txtTown.setText(address.getTown());
        txtCounty.setText(address.getCounty());
        txtPostcode.setText(address.getPostcode());
    }

    private void loadProfilePic(User user) {
        Optional<String> imageUrl = user.getProfileImagePath();
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
        userService.saveOrUpdate(selectedUser);
    }

    private void updateProfile() {
        selectedUser.setUsername(txtUsername.getText());
        selectedUser.setFirstName(txtFirstName.getText());
        selectedUser.setSurname(txtSurname.getText());
        selectedUser.setPhoneNum(txtPhoneNum.getText());
        updateAddress(selectedUser.getAddress());
    }

    private void updateAddress(Address address) {
        address.setHouseNum(txtHouseNum.getText());
        address.setStreet(txtStreet.getText());
        address.setTown(txtTown.getText());
        address.setCounty(txtCounty.getText());
        address.setPostcode(txtPostcode.getText());
    }

    private void chooseImage() {
        ImageUtils.chooseImage(FILE_CHOOSER_TITLE, PROFILE_PICTURE_DIRECTORY_NAME, imgProfilePic);
        Image image = imgProfilePic.getImage();
        if (image != null) {
            selectedUser.setProfileImagePath(image.getUrl());
            userService.saveOrUpdate(selectedUser);
        }
    }

    public void setSelectedUser(User user) {
        this.selectedUser = user;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
        LOGGER.info("{} UserService set to {}", this.getClass().getSimpleName(), userService.getClass().getSimpleName());
    }

}
