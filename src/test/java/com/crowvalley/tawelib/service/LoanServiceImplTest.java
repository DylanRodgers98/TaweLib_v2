package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.dao.LoanDAOImpl;
import com.crowvalley.tawelib.model.resource.Loan;
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
        loan1 = new Loan(1L, "", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
        loan2 = new Loan(2L, "", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
        loan3 = new Loan(3L, "", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
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
        Long id = 1L;

        when(DAO.get(id)).thenReturn(optionalLoan1);

        assertThat(loanService.get(id))
                .as("Retrieve loan from database with ID 1")
                .isEqualTo(optionalLoan1);
    }

    @Test
    public void testGet_noLoanFromDAO() {
        Long id = 4L;

        when(DAO.get(id)).thenReturn(Optional.empty());

        assertThat(loanService.get(id))
                .as("Retrieve empty Optional from DAO")
                .isEqualTo(Optional.empty());
    }

    @Test
    public void testGetAllLoansForCopy() {
        Long copyId = 1L;

        when(DAO.getAllLoansForCopy(copyId)).thenReturn(loans);

        assertThat(loanService.getAllLoansForCopy(copyId))
                .as("Retrieve all loans for a certain copy stored in the database")
                .isEqualTo(loans);
    }

    @Test
    public void testGetAllLoansForCopy_noLoansFromDAO() {
        Long copyId = 1L;

        when(DAO.getAllLoansForCopy(copyId)).thenReturn(new ArrayList<>());

        assertThat(loanService.getAllLoansForCopy(copyId).isEmpty())
                .as("Retrieve no loans for a certain copy from DAO")
                .isTrue();
    }

    @Test
    public void testGetAllLoansForUser() {
        String username = "DylanRodgers98";

        when(DAO.getAllLoansForUser(username)).thenReturn(loans);

        assertThat(loanService.getAllLoansForUser(username))
                .as("Retrieve all loans for a certain user stored in the database")
                .isEqualTo(loans);
    }

    @Test
    public void testGetAllLoansForUser_noLoansFromDAO() {
        String username = "DylanRodgers98";

        when(DAO.getAllLoansForUser(username)).thenReturn(new ArrayList<>());

        assertThat(loanService.getAllLoansForUser(username).isEmpty())
                .as("Retrieve no loans for a certain user from DAO")
                .isTrue();
    }

    @Test
    public void testGetAll() {
        when(DAO.getAll()).thenReturn(loans);

        assertThat(loanService.getAll())
                .as("Retrieve all loans stored in the database")
                .isEqualTo(loans);
    }

    @Test
    public void testGetAll_noLoansFromDAO() {
        when(DAO.getAll()).thenReturn(new ArrayList<>());

        assertThat(loanService.getAll().isEmpty())
                .as("Retrieve no loans from DAO")
                .isTrue();
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
