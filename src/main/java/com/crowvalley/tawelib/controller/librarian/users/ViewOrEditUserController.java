package com.crowvalley.tawelib.controller.librarian.users;

import com.crowvalley.tawelib.UserContextHolder;
import com.crowvalley.tawelib.controller.base.AbstractProfileController;
import com.crowvalley.tawelib.model.user.Librarian;
import com.crowvalley.tawelib.model.user.User;
import com.crowvalley.tawelib.util.FXMLUtils;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

public class ViewOrEditUserController extends AbstractProfileController {

    private static final String LIBRARIAN_HOME_FXML = "/fxml/librarian/librarianHome.fxml";

    @FXML
    private CheckBox chkLibrarian;

    @FXML
    private Label lblEmploymentDate;

    @FXML
    private DatePicker datePicker;

    @Override
    public void initialize() {
        super.initialize();
        setDatePickerVisibility();
    }

    @Override
    protected void populateFields() {
        if (selectedUser instanceof Librarian) {
            Librarian librarian = (Librarian) selectedUser;
            datePicker.setValue(librarian.getEmploymentDate());
            chkLibrarian.setSelected(true);
        }
        super.populateFields();
    }

    @Override
    protected void disableFields() {
        FXMLUtils.makeNodesDisabled(chkLibrarian, datePicker);
        super.disableFields();
    }

    @Override
    protected void enableFields() {
        // Don't enable checkbox or datepicker if selected user is currently logged in user
        // This is to prevent them revoking their own Librarian rights or changing their employment date
        if (!UserContextHolder.getLoggedInUser().equals(selectedUser.getUsername())) {
            FXMLUtils.makeNodesEnabled(chkLibrarian, datePicker);
        }
        super.enableFields();
    }

    @FXML
    private void setDatePickerVisibility() {
        if (chkLibrarian.isSelected()) {
            FXMLUtils.makeNodesVisible(lblEmploymentDate, datePicker);
        } else {
            FXMLUtils.makeNodesNotVisible(lblEmploymentDate, datePicker);
        }
    }

    @Override
    protected User initUpdatedUser() {
        if (chkLibrarian.isSelected()) {
            if (!(selectedUser instanceof Librarian)) {
                Librarian librarian = new Librarian();
                librarian.setEmploymentDate(datePicker.getValue());
                return librarian;
            }
        } else if (selectedUser instanceof Librarian) {
            return new User();
        }
        return selectedUser;
    }

    @FXML
    private void loadLibrarianHome() {
        FXMLUtils.loadNewScene(LIBRARIAN_HOME_FXML);
    }

}
