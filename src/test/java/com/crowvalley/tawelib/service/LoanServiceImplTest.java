package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.dao.LoanDAO;
import com.crowvalley.tawelib.model.resource.Loan;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LoanServiceImplTest {

    private static final Long LOAN_ID = 1L;

    private static final Long COPY_ID = 2L;

    private static final String USERNAME = "DylanRodgers98";

    private static final Loan LOAN_1 = new Loan(1L, "", LocalDateTime.now(), LocalDateTime.now());

    private static final Loan LOAN_2 = new Loan(2L, "", LocalDateTime.now(), LocalDateTime.now());

    private static final List<Loan> LOANS = Arrays.asList(LOAN_1, LOAN_2);

    @Mock
    private LoanDAO DAO;

    @InjectMocks
    private LoanServiceImpl loanService;

    @Test
    public void testGet() {
        when(DAO.getWithId(LOAN_ID, Loan.class)).thenReturn(Optional.of(LOAN_1));

        assertThat(loanService.get(LOAN_ID).get())
                .as("Retrieve loan from database with ID 1")
                .isEqualTo(LOAN_1);
    }

    @Test
    public void testGet_noLoanFromDAO() {
        when(DAO.getWithId(LOAN_ID, Loan.class)).thenReturn(Optional.empty());

        assertThat(loanService.get(LOAN_ID))
                .as("Retrieve empty Optional from DAO")
                .isEqualTo(Optional.empty());
    }

    @Test
    public void testGetAllLoansForCopy() {
        when(DAO.getAllLoansForCopy(COPY_ID)).thenReturn(LOANS);

        assertThat(loanService.getAllLoansForCopy(COPY_ID))
                .as("Retrieve all loans for a certain copy stored in the database")
                .isEqualTo(LOANS);
    }

    @Test
    public void testGetAllLoansForCopy_noLoansFromDAO() {
        when(DAO.getAllLoansForCopy(COPY_ID)).thenReturn(new ArrayList<>());

        assertThat(loanService.getAllLoansForCopy(COPY_ID).isEmpty())
                .as("Retrieve no loans for a certain copy from DAO")
                .isTrue();
    }

    @Test
    public void testGetAllLoansForUser() {
        when(DAO.getAllLoansForUser(USERNAME)).thenReturn(LOANS);

        assertThat(loanService.getAllLoansForUser(USERNAME))
                .as("Retrieve all loans for a certain user stored in the database")
                .isEqualTo(LOANS);
    }

    @Test
    public void testGetAllLoansForUser_noLoansFromDAO() {
        when(DAO.getAllLoansForUser(USERNAME)).thenReturn(new ArrayList<>());

        assertThat(loanService.getAllLoansForUser(USERNAME).isEmpty())
                .as("Retrieve no loans for a certain user from DAO")
                .isTrue();
    }

    @Test
    public void testGetAll() {
        when(DAO.getAll(Loan.class)).thenReturn(LOANS);

        assertThat(loanService.getAll())
                .as("Retrieve all loans stored in the database")
                .isEqualTo(LOANS);
    }

    @Test
    public void testGetAll_noLoansFromDAO() {
        when(DAO.getAll(Loan.class)).thenReturn(new ArrayList<>());

        assertThat(loanService.getAll().isEmpty())
                .as("Retrieve no loans from DAO")
                .isTrue();
    }

    @Test
    public void test_verifySaveOrUpdate() {
        loanService.saveOrUpdate(LOAN_1);
        verify(DAO).saveOrUpdate(LOAN_1);
    }

    @Test
    public void test_verifyDelete() {
        loanService.delete(LOAN_2);
        verify(DAO).delete(LOAN_2);
    }
}
