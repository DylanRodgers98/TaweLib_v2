package com.crowvalley.service;

import com.crowvalley.dao.LoanDAOImpl;
import com.crowvalley.model.Loan;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LoanServiceImplTest {

    @Mock
    private LoanDAOImpl DAO;

    @InjectMocks
    private LoanServiceImpl loanService;

    private Loan loan1;

    private Loan loan2;

    private Loan loan3;

    private Optional<Loan> optionalLoan1;

    private Optional<Loan> optionalLoan2;

    private Optional<Loan> optionalLoan3;

    private List<Loan> loans;

    @Before
    public void setup() {
        loan1 = new Loan(Long.valueOf(1), "", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
        loan2 = new Loan(Long.valueOf(2), "", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
        loan3 = new Loan(Long.valueOf(3), "", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
        optionalLoan1 = Optional.of(loan1);
        optionalLoan2 = Optional.of(loan2);
        optionalLoan3 = Optional.of(loan3);
        loans = new ArrayList<>();
        loans.add(loan1);
        loans.add(loan2);
        loans.add(loan3);
    }

    @Test
    public void testGet() {
        Long id = Long.valueOf(1);

        when(DAO.get(id)).thenReturn(optionalLoan1);

        assertThat(loanService.get(id)).as("Retrieve loan from database with ID 1").isEqualTo(optionalLoan1);
    }

    @Test
    public void testGet_noLoanFromDAO() {
        Long id = Long.valueOf(4);

        when(DAO.get(id)).thenReturn(Optional.empty());

        assertThat(loanService.get(id)).as("Retrieve empty Optional from DAO").isEqualTo(Optional.empty());
    }

    @Test
    public void testGetAll() {
        when(DAO.getAll()).thenReturn(loans);

        assertThat(loanService.getAll()).as("Retrieve all loans stored in the database").isEqualTo(loans);
    }

    @Test
    public void testGetAll_noLoansFromDAO() {
        when(DAO.getAll()).thenReturn(new ArrayList<>());

        assertThat(loanService.getAll().isEmpty()).as("Retrieve no loans from DAO").isTrue();
    }

    @Test
    public void test_verifySave() {
        loanService.save(loan1);
        verify(DAO).save(eq(loan1));
    }

    @Test
    public void test_verifyUpdate() {
        loanService.update(loan2);
        verify(DAO).update(eq(loan2));
    }

    @Test
    public void test_verifyDelete() {
        loanService.delete(loan3);
        verify(DAO).delete(eq(loan3));
    }
}