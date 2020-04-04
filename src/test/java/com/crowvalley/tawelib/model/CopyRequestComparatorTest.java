package com.crowvalley.tawelib.model;

import com.crowvalley.tawelib.model.resource.CopyRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CopyRequestComparatorTest {

    private static final Comparator<CopyRequest> COPY_REQUEST_COMPARATOR = CopyRequest.getComparator();

    private static final LocalDateTime APRIL_1 = LocalDateTime.of(2020, 4, 1, 0, 0);

    private static final LocalDateTime APRIL_4 = LocalDateTime.of(2020, 4, 4, 0, 0);

    @Mock
    private CopyRequest copyRequest1;

    @Mock
    private CopyRequest copyRequest2;

    @Test
    public void testComparator_DifferentStatuses() {
        when(copyRequest1.getRequestStatus()).thenReturn(CopyRequest.Status.REQUESTED);
        when(copyRequest2.getRequestStatus()).thenReturn(CopyRequest.Status.READY_FOR_COLLECTION);

        List<CopyRequest> copyRequests = Arrays.asList(copyRequest1, copyRequest2);
        copyRequests.sort(COPY_REQUEST_COMPARATOR);

        assertThat(copyRequests)
                .as("READY_FOR_COLLECTION request appears before REQUESTED one after sorting")
                .containsExactly(copyRequest2, copyRequest1);
    }

    @Test
    public void testComparator_EqualStatusesDifferentDates() {
        when(copyRequest1.getRequestStatus()).thenReturn(CopyRequest.Status.REQUESTED);
        when(copyRequest1.getRequestDate()).thenReturn(APRIL_4);
        when(copyRequest2.getRequestStatus()).thenReturn(CopyRequest.Status.REQUESTED);
        when(copyRequest2.getRequestDate()).thenReturn(APRIL_1);

        List<CopyRequest> copyRequests = Arrays.asList(copyRequest1, copyRequest2);
        copyRequests.sort(COPY_REQUEST_COMPARATOR);

        assertThat(copyRequests)
                .as("Yesterday's request appears before today's one after sorting")
                .containsExactly(copyRequest2, copyRequest1);
    }

    @Test
    public void testComparator_EqualStatusesEqualDates() {
        when(copyRequest1.getRequestStatus()).thenReturn(CopyRequest.Status.REQUESTED);
        when(copyRequest1.getRequestDate()).thenReturn(APRIL_4);
        when(copyRequest2.getRequestStatus()).thenReturn(CopyRequest.Status.REQUESTED);
        when(copyRequest2.getRequestDate()).thenReturn(APRIL_4);

        List<CopyRequest> copyRequests = Arrays.asList(copyRequest1, copyRequest2);
        copyRequests.sort(COPY_REQUEST_COMPARATOR);

        assertThat(copyRequests)
                .as("List already sorted as statuses and dates are equal")
                .containsExactly(copyRequest1, copyRequest2);
    }

}
