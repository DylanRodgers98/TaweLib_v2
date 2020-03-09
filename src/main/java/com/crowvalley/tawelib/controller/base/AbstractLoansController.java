package com.crowvalley.tawelib.controller.base;

import com.crowvalley.tawelib.controller.FXController;
import com.crowvalley.tawelib.model.resource.Copy;
import com.crowvalley.tawelib.model.resource.Loan;
import com.crowvalley.tawelib.model.resource.ResourceDTO;
import com.crowvalley.tawelib.service.CopyService;
import com.crowvalley.tawelib.service.LoanService;
import com.crowvalley.tawelib.service.ResourceService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableStringValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Date;
import java.util.Comparator;
import java.util.Optional;

public abstract class AbstractLoansController implements FXController {

    protected LoanService loanService;

    protected CopyService copyService;

    protected ResourceService resourceService;

    @FXML
    protected TableView<Loan> tblLoans;

    @FXML
    private TableColumn<Loan, String> colCopy;

    @FXML
    private TableColumn<Loan, Date> colStartDate;

    @FXML
    private TableColumn<Loan, Date> colEndDate;

    @FXML
    private TableColumn<Loan, Date> colReturnDate;

    @Override
    public void initialize() {
        colCopy.setCellValueFactory(this::getCopyTitle);
        colStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        colEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        colReturnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        tblLoans.setItems(getLoans());
    }

    private ObservableStringValue getCopyTitle(TableColumn.CellDataFeatures<Loan, String> loan) {
        Long copyId = loan.getValue().getCopyId();
        Optional<Copy> copy = copyService.get(copyId);
        return new SimpleStringProperty(copy.map(this::getCopyTitle)
                .orElse("[ERROR RETRIEVING COPY (ID: " + copyId + ")]"));
    }

    private String getCopyTitle(Copy copy) {
        Optional<ResourceDTO> optionalResourceDTO = resourceService.getResourceDTOFromCopy(copy);
        return optionalResourceDTO.map(resourceDTO -> resourceDTO + " (" + copy + ")")
                .orElse("[ERROR RETRIEVING COPY OF "+ copy.getResourceType() + " (ID: " + copy.getId() + ")]");
    }

    protected abstract ObservableList<Loan> getLoans();

    public abstract void setLoanService(LoanService loanService);

    public abstract void setCopyService(CopyService copyService);

    public abstract void setResourceService(ResourceService resourceService);

}
