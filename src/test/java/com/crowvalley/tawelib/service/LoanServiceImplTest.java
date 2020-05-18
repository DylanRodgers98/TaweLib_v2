package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.dao.LoanDAO;
import com.crowvalley.tawelib.model.fine.Fine;
import com.crowvalley.tawelib.model.fine.Transaction;
import com.crowvalley.tawelib.model.resource.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LoanServiceImplTest {

    private static final Long LOAN_ID = 1L;

    private static final Long COPY_ID = 2L;

    private static final String USERNAME = "TEST_USER";

    private static final String OTHER_USERNAME = "OTHER_USER";

    private static final long DAYS_LATE = 3;

    private static final long DAYS_EARLY = 1;

    @Spy
    private Loan spyLoan;

    @Mock
    private Copy mockCopy;

    @Mock
    private Resource mockResource;

    @Captor
    private ArgumentCaptor<Loan> loanCaptor;

    @Captor
    private ArgumentCaptor<Transaction> transactionCaptor;

    @Mock
    private LoanDAO mockLoanDAO;

    @Mock
    private CopyService mockCopyService;

    @Mock
    private TransactionService mockTransactionService;

    @InjectMocks
    private LoanServiceImpl loanService;

    @Test
    public void testGet() {
        when(mockLoanDAO.getWithId(LOAN_ID, Loan.class)).thenReturn(Optional.of(spyLoan));

        assertThat(loanService.get(LOAN_ID))
                .as("Loan retrieved from database")
                .isEqualTo(Optional.of(spyLoan));
    }

    @Test
    public void testGet_noLoanFromDatabase() {
        when(mockLoanDAO.getWithId(LOAN_ID, Loan.class)).thenReturn(Optional.empty());

        assertThat(loanService.get(LOAN_ID))
                .as("No Loan retrieved from database")
                .isEqualTo(Optional.empty());
    }

    @Test
    public void testGetAllLoansForUser() {
        when(mockLoanDAO.getAllLoansForUser(USERNAME)).thenReturn(Collections.singletonList(spyLoan));

        assertThat(loanService.getAllLoansForUser(USERNAME))
                .as("All loans retrieved for user")
                .containsExactly(spyLoan);
    }

    @Test
    public void testGetAllLoansForUser_noLoansFromDatabase() {
        when(mockLoanDAO.getAllLoansForUser(USERNAME)).thenReturn(Collections.emptyList());

        assertThat(loanService.getAllLoansForUser(USERNAME))
                .as("No loans retrieved for user")
                .isEmpty();
    }

    @Test
    public void testGetAll() {
        when(mockLoanDAO.getAll(Loan.class)).thenReturn(Collections.singletonList(spyLoan));

        assertThat(loanService.getAll())
                .as("All loans for all users retrieved")
                .containsExactly(spyLoan);
    }

    @Test
    public void testGetAll_noLoansFromDatabase() {
        when(mockLoanDAO.getAll(Loan.class)).thenReturn(Collections.emptyList());

        assertThat(loanService.getAll())
                .as("No loans for any users retrieved")
                .isEmpty();
    }

    @Test
    public void testSaveOrUpdate() {
        loanService.saveOrUpdate(spyLoan);
        verify(mockLoanDAO).saveOrUpdate(spyLoan);
    }

    @Test
    public void testDelete() {
        loanService.delete(spyLoan);
        verify(mockLoanDAO).delete(spyLoan);
    }

    @Test
    public void testGetCurrentLoanForCopy() {
        when(mockLoanDAO.getCurrentLoanForCopy(COPY_ID)).thenReturn(Optional.of(spyLoan));

        assertThat(loanService.getCurrentLoanForCopy(COPY_ID))
                .as("Current loan for given copy retrieved")
                .isEqualTo(Optional.of(spyLoan));
    }

    @Test
    public void testIsCopyOnLoan_CopyIsOnLoan() {
        when(mockLoanDAO.getCurrentLoanForCopy(COPY_ID)).thenReturn(Optional.of(spyLoan));

        assertThat(loanService.isCopyOnLoan(COPY_ID))
                .as("Given copy is on loan")
                .isTrue();
    }

    @Test
    public void testIsCopyOnLoan_CopyNotOnLoan() {
        when(mockLoanDAO.getCurrentLoanForCopy(COPY_ID)).thenReturn(Optional.empty());

        assertThat(loanService.isCopyOnLoan(COPY_ID))
                .as("Given copy is on loan")
                .isFalse();
    }

    @Test
    public void testGetUsernameOfCurrentBorrowerForCopy() {
        when(mockLoanDAO.getCurrentLoanForCopy(COPY_ID)).thenReturn(Optional.of(spyLoan));
        when(spyLoan.getBorrowerUsername()).thenReturn(USERNAME);

        assertThat(loanService.getUsernameOfCurrentBorrowerForCopy(COPY_ID))
                .as("Username retrieved for current borrower of copy")
                .isEqualTo(Optional.of(USERNAME));
    }

    @Test
    public void testGetUsernameOfCurrentBorrowerForCopy_NoCurrentBorrower() {
        when(mockLoanDAO.getCurrentLoanForCopy(COPY_ID)).thenReturn(Optional.of(spyLoan));
        when(spyLoan.getBorrowerUsername()).thenReturn(null);

        assertThat(loanService.getUsernameOfCurrentBorrowerForCopy(COPY_ID))
                .as("Copy has no current borrower so no username retrieved")
                .isEqualTo(Optional.empty());
    }

    @Test
    public void testCreateLoanForCopy() {
        when(mockCopy.getId()).thenReturn(COPY_ID);
        when(mockCopy.getLoanDurationAsDays()).thenReturn(1);
        when(mockCopyService.getRequestStatusForUser(mockCopy, USERNAME))
                .thenReturn(Optional.of(CopyRequest.Status.READY_FOR_COLLECTION));

        loanService.createLoanForCopy(mockCopy, USERNAME);

        verify(mockLoanDAO).saveOrUpdate(loanCaptor.capture());
        verify(mockCopyService).setRequestStatusForUser(mockCopy, USERNAME, CopyRequest.Status.COLLECTED);
        verify(mockCopyService).removeCopyRequest(mockCopy, USERNAME);

        assertThat(loanCaptor.getValue())
                .extracting(Loan::getCopyId, Loan::getBorrowerUsername)
                .as("Created loan has the correct copy ID and borrower username set on it")
                .containsExactly(COPY_ID, USERNAME);
    }

    @Test
    public void testEndLoan() {
        when(spyLoan.getEndDate()).thenReturn(LocalDateTime.now().plusDays(1));

        loanService.endLoan(spyLoan);

        verify(mockLoanDAO).saveOrUpdate(loanCaptor.capture());

        assertThat(loanCaptor.getValue().getReturnDate().toLocalDate())
                .as("Loan end date set to today's date")
                .isEqualTo(LocalDate.now());
    }

    @Test
    public void testEndLoan_CopyReturnedLate() {
        long daysLate = 1;
        ResourceType resourceType = ResourceType.BOOK;

        when(spyLoan.getEndDate()).thenReturn(LocalDateTime.now().minusDays(daysLate));
        when(spyLoan.getId()).thenReturn(LOAN_ID);
        when(spyLoan.getBorrowerUsername()).thenReturn(USERNAME);
        when(spyLoan.getCopyId()).thenReturn(COPY_ID);
        when(mockCopyService.get(COPY_ID)).thenReturn(Optional.of(mockCopy));
        when(mockCopy.getResource()).thenReturn(mockResource);
        when(mockResource.getResourceType()).thenReturn(resourceType);

        loanService.endLoan(spyLoan);

        verify(mockLoanDAO).saveOrUpdate(loanCaptor.capture());

        assertThat(loanCaptor.getValue().getReturnDate().toLocalDate())
                .as("Loan end date set to today's date")
                .isEqualTo(LocalDate.now());

        verify(mockTransactionService).save(transactionCaptor.capture());

        Transaction transaction = transactionCaptor.getValue();
        assertThat(transaction).isInstanceOf(Fine.class);
        Fine actualFine = (Fine) transaction;

        assertThat(actualFine)
                .extracting(Transaction::getUsername, Fine::getLoan)
                .as("Fine created has correct username and loan ID")
                .containsExactly(USERNAME, spyLoan);

        assertThat(actualFine.getAmount())
                .as("Fine amount equal to resource type's daily fine rate multiplied by the number of days the copy was returned late")
                .isEqualTo(resourceType.getFineRate().multiply(BigDecimal.valueOf(daysLate)));

        assertThat(actualFine.getTransactionDate().toLocalDate())
                .as("Fine date set to today's date")
                .isEqualTo(LocalDate.now());
    }

    @Test(expected = IllegalStateException.class)
    public void testEndLoan_CopyReturnedLate_CopyNotFoundInDatabase() {
        long daysLate = 1;
        when(spyLoan.getEndDate()).thenReturn(LocalDateTime.now().minusDays(daysLate));
        when(spyLoan.getId()).thenReturn(LOAN_ID);
        when(spyLoan.getBorrowerUsername()).thenReturn(USERNAME);
        when(spyLoan.getCopyId()).thenReturn(COPY_ID);
        when(mockCopyService.get(COPY_ID)).thenReturn(Optional.empty());

        loanService.endLoan(spyLoan);

        verify(mockLoanDAO).saveOrUpdate(loanCaptor.capture());

        assertThat(loanCaptor.getValue().getReturnDate().toLocalDate())
                .as("Loan end date set to today's date")
                .isEqualTo(LocalDate.now());
    }

    @Test
    public void testSearch_DateSearchFields() {
        when(mockLoanDAO.search(eq(null), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Collections.singletonList(spyLoan));

        assertThat(loanService.search(LocalDateTime.now(), LocalDateTime.now()))
                .as("Loans found using dates in search query")
                .containsExactly(spyLoan);
    }

    @Test
    public void testSearch_UsernameAndDateSearchFields() {
        when(mockLoanDAO.search(eq(USERNAME), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Collections.singletonList(spyLoan));

        assertThat(loanService.search(USERNAME, LocalDateTime.now(), LocalDateTime.now()))
                .as("Loans found using username and dates in search query")
                .containsExactly(spyLoan);
    }

    @Test
    public void testGetLoanStatusForUser_CopyOnLoanToLoggedInUser() {
        when(mockCopy.getId()).thenReturn(COPY_ID);
        when(mockLoanDAO.getCurrentLoanForCopy(COPY_ID)).thenReturn(Optional.of(spyLoan));
        when(spyLoan.getBorrowerUsername()).thenReturn(USERNAME);

        assertThat(loanService.getLoanStatusForUser(mockCopy, USERNAME))
                .as("Loan status for copy on loan to current user is ON_LOAN_TO_YOU")
                .isEqualTo(Loan.Status.ON_LOAN_TO_YOU);
    }

    @Test
    public void testGetLoanStatusForUser_CopyOnLoan() {
        when(mockCopy.getId()).thenReturn(COPY_ID);
        when(mockLoanDAO.getCurrentLoanForCopy(COPY_ID)).thenReturn(Optional.of(spyLoan));
        when(spyLoan.getBorrowerUsername()).thenReturn(OTHER_USERNAME);

        assertThat(loanService.getLoanStatusForUser(mockCopy, USERNAME))
                .as("Loan status for copy on loan to a different user is ON_LOAN")
                .isEqualTo(Loan.Status.ON_LOAN);
    }

    @Test
    public void testGetLoanStatusForUser_CopyNotOnLoan() {
        when(mockCopy.getId()).thenReturn(COPY_ID);
        when(mockLoanDAO.getCurrentLoanForCopy(COPY_ID)).thenReturn(Optional.empty());

        assertThat(loanService.getLoanStatusForUser(mockCopy, USERNAME))
                .as("Loan status for copy not on loan is AVAILABLE")
                .isEqualTo(Loan.Status.AVAILABLE);
    }

    @Test
    public void testGetNumberOfDaysLate_LateLoan() {
        when(mockLoanDAO.getWithId(LOAN_ID, Loan.class)).thenReturn(Optional.of(spyLoan));
        when(spyLoan.getEndDate()).thenReturn(LocalDateTime.now().minusDays(DAYS_LATE));
        when(spyLoan.getReturnDate()).thenReturn(LocalDateTime.now());

        assertThat(loanService.getNumberOfDaysLate(LOAN_ID))
                .as("The number of days between the loan end date and return date is returned")
                .isEqualTo(DAYS_LATE);
    }

    @Test
    public void testGetNumberOfDaysLate_OnTimeLoan() {
        when(mockLoanDAO.getWithId(LOAN_ID, Loan.class)).thenReturn(Optional.of(spyLoan));
        when(spyLoan.getEndDate()).thenReturn(LocalDateTime.now());
        when(spyLoan.getReturnDate()).thenReturn(LocalDateTime.now());

        assertThat(loanService.getNumberOfDaysLate(LOAN_ID))
                .as("Value for number of days late is 0 when loan is ended on time")
                .isEqualTo(0);
    }

    @Test
    public void testGetNumberOfDaysLate_EarlyLoan() {
        when(mockLoanDAO.getWithId(LOAN_ID, Loan.class)).thenReturn(Optional.of(spyLoan));
        when(spyLoan.getEndDate()).thenReturn(LocalDateTime.now());
        when(spyLoan.getReturnDate()).thenReturn(LocalDateTime.now().minusDays(DAYS_EARLY));

        assertThat(loanService.getNumberOfDaysLate(LOAN_ID))
                .as("Value for number of days late is 0 when loan is ended early")
                .isEqualTo(0);
    }

    @Test
    public void testGetNumberOfDaysLate_LoanNotFound() {
        when(mockLoanDAO.getWithId(LOAN_ID, Loan.class)).thenReturn(Optional.empty());

        assertThatCode(() -> loanService.getNumberOfDaysLate(LOAN_ID))
                .as("Exception thrown when loan cannot be found")
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Could not find Loan with ID: " + LOAN_ID);
    }

}
