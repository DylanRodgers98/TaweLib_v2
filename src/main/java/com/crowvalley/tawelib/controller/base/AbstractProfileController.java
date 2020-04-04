package com.crowvalley.tawelib.controller.base;

import com.crowvalley.tawelib.controller.SelectionAwareFXController;
import com.crowvalley.tawelib.model.user.Address;
import com.crowvalley.tawelib.model.user.User;
import com.crowvalley.tawelib.service.UserService;
import com.crowvalley.tawelib.util.FXMLUtils;
import com.crowvalley.tawelib.util.ImageUtils;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public abstract class AbstractProfileController implements SelectionAwareFXController<User> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractProfileController.class);

    private static final String UPDATE_PROFILE_BUTTON_TEXT = "Update Profile";

    private static final String SAVE_CHANGES_BUTTON_TEXT = "Save Changes";

    private static final String FILE_CHOOSER_TITLE = "Choose Profile Picture";

    private static final String PROFILE_PICTURE_DIRECTORY_NAME = "profile";

    protected UserService userService;

    protected User selectedUser;

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

    @Override
    public void initialize() {
        if (selectedUser != null) {
            loadProfile();
            disableFields();
        }
    }

    private void loadProfile() {
        populateFields();
        loadProfilePic();
    }

    protected void populateFields() {
        txtUsername.setText(selectedUser.getUsername());
        txtFirstName.setText(selectedUser.getFirstName());
        txtSurname.setText(selectedUser.getSurname());
        txtPhoneNum.setText(selectedUser.getPhoneNum());
        populateAddressFields(selectedUser.getAddress());
    }

    private void populateAddressFields(Address address) {
        txtHouseNum.setText(address.getHouseNum());
        txtStreet.setText(address.getStreet());
        txtTown.setText(address.getTown());
        txtCounty.setText(address.getCounty());
        txtPostcode.setText(address.getPostcode());
    }

    private void loadProfilePic() {
        Optional<String> imageUrl = selectedUser.getProfileImagePath();
        imageUrl.ifPresent(e -> imgProfilePic.setImage(new Image(e)));
    }

    protected void disableFields() {
        FXMLUtils.makeNodesDisabled(txtUsername, txtFirstName, txtSurname, txtPhoneNum,
                txtHouseNum, txtStreet, txtTown, txtCounty, txtPostcode);
    }

    protected void enableFields() {
        FXMLUtils.makeNodesEnabled(txtUsername, txtFirstName, txtSurname, txtPhoneNum,
                txtHouseNum, txtStreet, txtTown, txtCounty, txtPostcode);
    }

    @FXML
    private void saveOrUpdateProfile() {
        if (btnSaveOrUpdate.getText().equals(UPDATE_PROFILE_BUTTON_TEXT)) {
            startUpdateProfile();
        } else if (btnSaveOrUpdate.getText().equals(SAVE_CHANGES_BUTTON_TEXT)) {
            finishUpdateProfile();
        }
    }

    private void startUpdateProfile() {
        enableFields();
        btnSaveOrUpdate.setText(SAVE_CHANGES_BUTTON_TEXT);
    }

    private void finishUpdateProfile() {
        disableFields();
        btnSaveOrUpdate.setText(UPDATE_PROFILE_BUTTON_TEXT);
        updateProfile();
    }

    private void updateProfile() {
        User user = initUpdatedUser();
        updateInfo(user);
        if (user != selectedUser) {
            // If a new object has been created for update, delete the old object
            userService.delete(selectedUser);
        }
        userService.saveOrUpdate(user);
        selectedUser = user;
    }

    protected User initUpdatedUser() {
        return selectedUser;
    }

    private void updateInfo(User user) {
        user.setUsername(txtUsername.getText());
        user.setFirstName(txtFirstName.getText());
        user.setSurname(txtSurname.getText());
        user.setPhoneNum(txtPhoneNum.getText());
        updateAddress(user.getAddress());
    }

    private void updateAddress(Address address) {
        address.setHouseNum(txtHouseNum.getText());
        address.setStreet(txtStreet.getText());
        address.setTown(txtTown.getText());
        address.setCounty(txtCounty.getText());
        address.setPostcode(txtPostcode.getText());
    }

    @FXML
    private void chooseImage() {
        ImageUtils.chooseImage(FILE_CHOOSER_TITLE, PROFILE_PICTURE_DIRECTORY_NAME, imgProfilePic);
        Image image = imgProfilePic.getImage();
        if (image != null) {
            selectedUser.setProfileImagePath(image.getUrl());
            userService.saveOrUpdate(selectedUser);
        }
    }

    @Override
    public void setSelectedItem(User selectedItem) {
        this.selectedUser = selectedItem;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
        LOGGER.info("{} UserService set to {}", this.getClass().getSimpleName(), userService.getClass().getSimpleName());
    }

}
