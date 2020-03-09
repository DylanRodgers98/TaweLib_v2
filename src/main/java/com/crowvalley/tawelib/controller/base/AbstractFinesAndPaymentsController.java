package com.crowvalley.tawelib.controller.base;

import com.crowvalley.tawelib.controller.FXController;
import com.crowvalley.tawelib.model.fine.Fine;
import com.crowvalley.tawelib.model.fine.Payment;
import com.crowvalley.tawelib.model.fine.Transaction;
import com.crowvalley.tawelib.model.resource.Copy;
import com.crowvalley.tawelib.model.resource.ResourceDTO;
import com.crowvalley.tawelib.service.ResourceService;
import com.crowvalley.tawelib.service.TransactionService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableStringValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Optional;

public abstract class AbstractFinesAndPaymentsController implements FXController {

    private static final NumberFormat CURRENCY_FORMAT = NumberFormat.getCurrencyInstance(Locale.UK);

    protected TransactionService transactionService;

    protected ResourceService resourceService;

    @FXML
    private TableView<Transaction> tblFinesAndPayments;

    @FXML
    private TableColumn<Transaction, String> colAmount;

    @FXML
    private TableColumn<Transaction, String> colType;

    @Override
    public void initialize() {
        colAmount.setCellValueFactory(this::getAmount);
        colType.setCellValueFactory(this::getType);
        tblFinesAndPayments.setItems(getFinesAndPayments());
    }

    private ObservableStringValue getAmount(TableColumn.CellDataFeatures<Transaction, String> transaction) {
        BigDecimal amount = transaction.getValue().getAmount();
        return new SimpleStringProperty(CURRENCY_FORMAT.format(amount));
    }

    private ObservableValue<String> getType(TableColumn.CellDataFeatures<Transaction, String> transaction) {
        Transaction transactionValue = transaction.getValue();
        if (transactionValue instanceof Fine) {
            return new SimpleStringProperty("Fine" + getFineReason(transactionValue));
        }
        if (transactionValue instanceof Payment) {
            return new SimpleStringProperty("Payment");
        }
        return null;
    }

    private String getFineReason(Transaction transaction) {
        Assert.isInstanceOf(Fine.class, transaction, "Transaction is not of type Fine");

        Optional<Copy> optionalCopy = transactionService.getCopyFromFine((Fine) transaction);
        if (optionalCopy.isPresent()) {
            Copy copy = optionalCopy.get();
            Optional<ResourceDTO> optionalResourceDTO = resourceService.getResourceDTOFromCopy(copy);
            return " for late return of " + optionalResourceDTO.map(resourceDTO -> resourceDTO + " (" + copy + ")")
                    .orElseGet(() -> copy.getResourceType() + " (ID: " + copy.getId() + ")");
        }
        return StringUtils.EMPTY;
    }

    protected abstract ObservableList<Transaction> getFinesAndPayments();

    public abstract void setTransactionService(TransactionService transactionService);

    public abstract void setResourceService(ResourceService resourceService);

}
