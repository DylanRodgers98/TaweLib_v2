package com.crowvalley.tawelib.controller.librarian;

import com.crowvalley.tawelib.Main;
import com.crowvalley.tawelib.model.user.Address;
import com.crowvalley.tawelib.model.user.Librarian;
import com.crowvalley.tawelib.service.LibrarianService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.util.Optional;

public class LibrarianProfileTabController {

    private LibrarianService librarianService;

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

    public LibrarianProfileTabController() {
    }

    public void initialize() {
        String currentUser = Main.currentUser;
        Optional<Librarian> optionalLibrarian = librarianService.getWithUsername(currentUser);
        if (optionalLibrarian.isPresent()) {
            Librarian librarian = optionalLibrarian.get();
            txtUsername.setText(currentUser);
            txtFirstName.setText(librarian.getFirstName());
            txtSurname.setText(librarian.getSurname());
            txtPhoneNum.setText(librarian.getPhoneNum());
            Address address = librarian.getAddress();
            txtHouseNum.setText(address.getHouseNum());
            txtStreet.setText(address.getStreet());
            txtTown.setText(address.getTown());
            txtCounty.setText(address.getCounty());
            txtPostcode.setText(address.getPostcode());
        }
    }

    public void setLibrarianService(LibrarianService librarianService) {
        this.librarianService = librarianService;
    }
}
