package com.crowvalley.tawelib.controller.librarian.users;

import com.crowvalley.tawelib.controller.FXController;
import com.crowvalley.tawelib.model.user.User;
import com.crowvalley.tawelib.service.UserService;
import com.crowvalley.tawelib.util.FXMLUtils;
import com.crowvalley.tawelib.util.ImageUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddNewUserController implements FXController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AddNewUserController.class);

    private static final String LIBRARIAN_HOME_FXML = "/fxml/librarian/librarianHome.fxml";

    private static final String FILE_CHOOSER_TITLE = "Choose Profile Picture";

    private static final String PROFILE_PICTURE_DIRECTORY_NAME = "profile";

    private UserService userService;

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
    private ImageView imgProfilePic;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnChoosePic;

    @FXML
    private Button btnBack;

    @Override
    public void initialize() {
        btnAdd.setOnAction(e -> addUser());
        btnChoosePic.setOnAction(e -> ImageUtils.chooseImage(FILE_CHOOSER_TITLE, PROFILE_PICTURE_DIRECTORY_NAME, imgProfilePic));
        btnBack.setOnAction(e -> goBack());
    }

    private void addUser() {
        String username = txtUsername.getText();
        String firstName = txtFirstName.getText();
        String surname = txtSurname.getText();
        String phoneNum = txtPhoneNum.getText();
        String houseNum = txtHouseNum.getText();
        String street = txtStreet.getText();
        String town = txtTown.getText();
        String county = txtCounty.getText();
        String postcode = txtPostcode.getText();

        Image image = imgProfilePic.getImage();
        String imageUrl = image == null ? StringUtils.EMPTY : image.getUrl(); //TODO: replace empty string with default img

        User user = new User(username, firstName, surname, phoneNum, houseNum, street, town, county, postcode, imageUrl);
        userService.saveOrUpdate(user);
        goBack();
    }

    private void goBack() {
        FXMLUtils.loadNewScene(btnBack, LIBRARIAN_HOME_FXML);
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
        LOGGER.info("{} UserService set to {}", this.getClass().getSimpleName(), userService.getClass().getSimpleName());
    }

}
