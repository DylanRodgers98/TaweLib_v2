package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.dao.TransactionDAO;
import com.crowvalley.tawelib.model.fine.Fine;
import com.crowvalley.tawelib.model.fine.OutstandingFinesDTO;
import com.crowvalley.tawelib.model.fine.Payment;
import com.crowvalley.tawelib.model.fine.Transaction;
import com.crowvalley.tawelib.model.resource.Copy;
import com.crowvalley.tawelib.model.resource.Loan;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceImplTest {

    private static final String USERNAME = "TEST_USER";

    private static final Long LOAN_ID = 1L;

    private static final Long COPY_ID = 2L;

    private static final BigDecimal TOTAL_FINES_AMOUNT = new BigDecimal("10");

    private static final BigDecimal TOTAL_PAYMENTS_AMOUNT = new BigDecimal("4");

    @Mock
    private Fine mockFine;

    @Mock
    private Payment mockPayment;

    @Mock
    private Loan mockLoan;

    @Mock
    private Copy mockCopy;

    @Mock
    private TransactionDAO mockTransactionDAO;

    @Mock
    private UserService mockUserService;

    @Mock
    private LoanService mockLoanService;

    @Mock
    private CopyService mockCopyService;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Test
    public void testGetAll() {
        when(mockTransactionDAO.getAll(Transaction.class)).thenReturn(Arrays.asList(mockFine, mockPayment));

        assertThat(transactionService.getAll())
                .as("All transactions retrieved from database")
                .isEqualTo(Arrays.asList(mockFine, mockPayment));
    }

    @Test
    public void testGetAll_NoTransactionsFromDatabase() {
        when(mockTransactionDAO.getAll(Transaction.class)).thenReturn(Collections.emptyList());

        assertThat(transactionService.getAll())
                .as("No transactions retrieved from database")
                .isEmpty();
    }

    @Test
    public void testGetAllTransactionsForUser() {
        when(mockTransactionDAO.getAllTransactionsForUser(USERNAME)).thenReturn(Arrays.asList(mockFine, mockPayment));

        assertThat(transactionService.getAllTransactionsForUser(USERNAME))
                .as("All transactions for user retrieved from database")
                .isEqualTo(Arrays.asList(mockFine, mockPayment));
    }

    @Test
    public void testGetAllTransactionsForUser_NoTransactionsFromDatabase() {
        when(mockTransactionDAO.getAllTransactionsForUser(USERNAME)).thenReturn(Collections.emptyList());

        assertThat(transactionService.getAllTransactionsForUser(USERNAME))
                .as("No transactions for user retrieved from database")
                .isEmpty();
    }

    @Test
    public void testSave() {
        transactionService.save(mockPayment);
        verify(mockTransactionDAO).saveOrUpdate(mockPayment);
    }

    @Test
    public void testGetAllUsersWithOutstandingFines() {
        when(mockUserService.getAllUsernames()).thenReturn(Collections.singletonList(USERNAME));
        when(mockTransactionDAO.getTotalFinesAmountForUser(USERNAME)).thenReturn(TOTAL_FINES_AMOUNT);
        when(mockTransactionDAO.getTotalPaymentsAmountForUser(USERNAME)).thenReturn(TOTAL_PAYMENTS_AMOUNT);

        List<OutstandingFinesDTO> outstandingFines = transactionService.getAllUsersWithOutstandingFines();

        assertThat(outstandingFines)
                .hasSize(1)
                .extracting(OutstandingFinesDTO::getUsername, OutstandingFinesDTO::getOutstandingFines)
                .containsExactly(tuple(USERNAME, TOTAL_FINES_AMOUNT.subtract(TOTAL_PAYMENTS_AMOUNT)));
    }

    @Test
    public void testGetTotalFinesAmountForUser() {
        when(mockTransactionDAO.getTotalFinesAmountForUser(USERNAME)).thenReturn(TOTAL_FINES_AMOUNT);

        assertThat(transactionService.getTotalFinesAmountForUser(USERNAME))
                .as("Total fines amount retrieved for user")
                .isEqualTo(TOTAL_FINES_AMOUNT);
    }

    @Test
    public void testGetTotalFinesAmountForUser_NoFines() {
        when(mockTransactionDAO.getTotalFinesAmountForUser(USERNAME)).thenReturn(null);

        assertThat(transactionService.getTotalFinesAmountForUser(USERNAME))
                .as("No fines amount retrieved for user")
                .isEqualTo(BigDecimal.ZERO);
    }

    @Test
    public void testGetTotalPaymentsAmountForUser() {
        when(mockTransactionDAO.getTotalPaymentsAmountForUser(USERNAME)).thenReturn(TOTAL_PAYMENTS_AMOUNT);

        assertThat(transactionService.getTotalPaymentsAmountForUser(USERNAME))
                .as("Total payments amount retrieved for user")
                .isEqualTo(TOTAL_PAYMENTS_AMOUNT);
    }

    @Test
    public void testGetTotalPaymentsAmountForUser_NoPayments() {
        when(mockTransactionDAO.getTotalPaymentsAmountForUser(USERNAME)).thenReturn(null);

        assertThat(transactionService.getTotalPaymentsAmountForUser(USERNAME))
                .as("No payments amount retrieved for user")
                .isEqualTo(BigDecimal.ZERO);
    }

    @Test
    public void testSearch_DateSearchFields() {
        when(mockTransactionDAO.search(eq(null), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Arrays.asList(mockPayment, mockFine));

        assertThat(transactionService.search(LocalDateTime.now(), LocalDateTime.now()))
                .as("Transactions found using dates in search query")
                .containsExactlyInAnyOrder(mockPayment, mockFine);
    }

    @Test
    public void testSearch_UsernameAndDateSearchFields() {
        when(mockTransactionDAO.search(eq(USERNAME), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Arrays.asList(mockPayment, mockFine));

        assertThat(transactionService.search(USERNAME, LocalDateTime.now(), LocalDateTime.now()))
                .as("Transactions found using username and dates in search query")
                .containsExactlyInAnyOrder(mockPayment, mockFine);
    }

}
