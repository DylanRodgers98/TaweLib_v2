package com.crowvalley.tawelib.controller.librarian;

import com.crowvalley.tawelib.controller.base.AbstractProfileController;
import com.crowvalley.tawelib.model.user.Librarian;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LibrarianProfileTabController extends AbstractProfileController {

    @FXML
    private TextField txtEmploymentDate;

    @Override
    protected void populateFields() {
        txtEmploymentDate.setText(((Librarian) selectedUser).getEmploymentDate().toString());
        super.populateFields();
    }

}
