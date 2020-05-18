package com.crowvalley.tawelib.model;

import com.crowvalley.tawelib.model.resource.Loan;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoanComparatorTest {

    private static final Comparator<Loan> LOAN_COMPARATOR = Loan.getComparator();

    private static final LocalDateTime APRIL_1 = LocalDateTime.of(2020, 4, 1, 0, 0);

    private static final LocalDateTime APRIL_2 = LocalDateTime.of(2020, 4, 2, 0, 0);

    private static final LocalDateTime APRIL_4 = LocalDateTime.of(2020, 4, 4, 0, 0);

    @Mock
    private Loan mockLoan1;

    @Mock
    private Loan mockLoan2;

    @Test
    public void testComparator_DifferentReturnDate() {
        when(mockLoan1.getReturnDate()).thenReturn(APRIL_1);
        when(mockLoan2.getReturnDate()).thenReturn(APRIL_4);

        List<Loan> loans = Arrays.asList(mockLoan1, mockLoan2);
        loans.sort(LOAN_COMPARATOR);

        assertThat(loans)
                .as("Loan returned today appears before loan returned yesterday after sorting")
                .containsExactly(mockLoan2, mockLoan1);
    }

    @Test
    public void testComparator_OneNullReturnDate() {
        when(mockLoan1.getReturnDate()).thenReturn(APRIL_4);
        when(mockLoan2.getReturnDate()).thenReturn(null);

        List<Loan> loans = Arrays.asList(mockLoan1, mockLoan2);
        loans.sort(LOAN_COMPARATOR);

        assertThat(loans)
                .as("Loan with null return date appears before loan returned yesterday after sorting")
                .containsExactly(mockLoan2, mockLoan1);
    }

    @Test
    public void testComparator_EqualReturnDates_DifferentEndDates() {
        when(mockLoan1.getReturnDate()).thenReturn(APRIL_4);
        when(mockLoan2.getReturnDate()).thenReturn(APRIL_4);
        when(mockLoan1.getEndDate()).thenReturn(APRIL_1);
        when(mockLoan2.getEndDate()).thenReturn(APRIL_2);

        List<Loan> loans = Arrays.asList(mockLoan1, mockLoan2);
        loans.sort(LOAN_COMPARATOR);

        assertThat(loans)
                .as("Loan with null return date appears before loan returned yesterday after sorting")
                .containsExactly(mockLoan2, mockLoan1);
    }

    @Test
    public void testComparator_EqualReturnAndEndDates_DifferentStartDates() {
        when(mockLoan1.getReturnDate()).thenReturn(APRIL_4);
        when(mockLoan2.getReturnDate()).thenReturn(APRIL_4);
        when(mockLoan1.getEndDate()).thenReturn(APRIL_4);
        when(mockLoan2.getEndDate()).thenReturn(APRIL_4);
        when(mockLoan1.getStartDate()).thenReturn(APRIL_1);
        when(mockLoan2.getStartDate()).thenReturn(APRIL_2);

        List<Loan> loans = Arrays.asList(mockLoan1, mockLoan2);
        loans.sort(LOAN_COMPARATOR);

        assertThat(loans)
                .as("Loan with null return date appears before loan returned yesterday after sorting")
                .containsExactly(mockLoan2, mockLoan1);
    }

    @Test
    public void testComparator_AllDatesEqual() {
        when(mockLoan1.getReturnDate()).thenReturn(APRIL_4);
        when(mockLoan2.getReturnDate()).thenReturn(APRIL_4);
        when(mockLoan1.getEndDate()).thenReturn(APRIL_4);
        when(mockLoan2.getEndDate()).thenReturn(APRIL_4);
        when(mockLoan1.getStartDate()).thenReturn(APRIL_4);
        when(mockLoan2.getStartDate()).thenReturn(APRIL_4);

        List<Loan> loans = Arrays.asList(mockLoan1, mockLoan2);
        loans.sort(LOAN_COMPARATOR);

        assertThat(loans)
                .as("List already sorted as dates are all equal")
                .containsExactly(mockLoan1, mockLoan2);
    }

}
