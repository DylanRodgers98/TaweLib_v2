package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.dao.CopyDAO;
import com.crowvalley.tawelib.model.resource.Copy;
import com.crowvalley.tawelib.model.resource.CopyRequest;
import org.assertj.core.util.Maps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CopyServiceImplTest {

    private static final Long COPY_ID_1 = 1L;

    private static final Long COPY_ID_2 = 2L;

    private static final Long RESOURCE_ID = 3L;

    private static final String USERNAME = "TEST_USER";

    @Mock
    private Copy mockCopy;

    @Mock
    private Copy mockCopyOnLoan;

    @Spy
    private CopyRequest spyCopyRequest;

    @Mock
    private CopyDAO mockCopyDAO;

    @Mock
    private LoanService mockLoanService;

    @InjectMocks
    private CopyServiceImpl copyService;

    @Test
    public void testGet() {
        when(mockCopyDAO.getWithId(COPY_ID_1, Copy.class)).thenReturn(Optional.of(mockCopy));

        assertThat(copyService.get(COPY_ID_1))
                .as("Copy retrieved from database")
                .isEqualTo(Optional.of(mockCopy));
    }

    @Test
    public void testGet_noCopyFromDatabase() {
        when(mockCopyDAO.getWithId(COPY_ID_1, Copy.class)).thenReturn(Optional.empty());

        assertThat(copyService.get(COPY_ID_1))
                .as("No copy retrieved from database")
                .isEqualTo(Optional.empty());
    }

    @Test
    public void testGetAllCopiesNotOnLoanForResource() {
        when(mockCopyDAO.getAllCopiesForResource(RESOURCE_ID)).thenReturn(Arrays.asList(mockCopy, mockCopyOnLoan));
        when(mockCopy.getId()).thenReturn(COPY_ID_1);
        when(mockCopyOnLoan.getId()).thenReturn(COPY_ID_2);
        when(mockLoanService.isCopyOnLoan(COPY_ID_1)).thenReturn(false);
        when(mockLoanService.isCopyOnLoan(COPY_ID_2)).thenReturn(true);

        assertThat(copyService.getAllCopiesNotOnLoanForResource(RESOURCE_ID))
                .as("Copy not on loan retrieved from database")
                .containsExactly(mockCopy);
    }

    @Test
    public void testSaveOrUpdate() {
        copyService.saveOrUpdate(mockCopy);
        verify(mockCopyDAO).saveOrUpdate(mockCopy);
    }

    @Test
    public void testDelete() {
        copyService.delete(mockCopy);
        verify(mockCopyDAO).delete(mockCopy);
    }

    @Test
    public void testCreateCopyRequest() {
        Map<String, CopyRequest> copyRequests = new HashMap<>();
        when(mockCopy.getCopyRequests()).thenReturn(copyRequests);

        copyService.createCopyRequest(mockCopy, USERNAME);

        verify(mockCopyDAO).saveOrUpdate(mockCopy);

        assertThat(copyRequests)
                .as("Copy only has request from passed in user")
                .containsOnlyKeys(USERNAME);

        assertThat(copyRequests.get(USERNAME))
                .extracting(CopyRequest::getCopy, CopyRequest::getUsername, CopyRequest::getRequestStatus)
                .as("Created CopyRequest has the correct copy, username, and status")
                .containsExactly(mockCopy, USERNAME, CopyRequest.Status.REQUESTED);
    }

    @Test
    public void testRemoveCopyRequest() {
        Map<String, CopyRequest> copyRequests = Maps.newHashMap(USERNAME, spyCopyRequest);
        when(mockCopy.getCopyRequests()).thenReturn(copyRequests);

        copyService.removeCopyRequest(mockCopy, USERNAME);

        verify(mockCopyDAO).saveOrUpdate(mockCopy);

        assertThat(copyRequests)
                .as("CopyRequest removed from Copy")
                .doesNotContain(Map.entry(USERNAME, spyCopyRequest));
    }

    @Test
    public void testSetRequestStatusForUser() {
        Map<String, CopyRequest> copyRequests = Maps.newHashMap(USERNAME, spyCopyRequest);
        when(mockCopy.getCopyRequests()).thenReturn(copyRequests);

        copyService.setRequestStatusForUser(mockCopy, USERNAME, CopyRequest.Status.READY_FOR_COLLECTION);

        verify(mockCopyDAO).saveOrUpdate(mockCopy);

        assertThat(copyRequests.get(USERNAME))
                .extracting(CopyRequest::getRequestStatus)
                .as("Copy request has updated status")
                .isEqualTo(CopyRequest.Status.READY_FOR_COLLECTION);
    }

    @Test
    public void testGetRequestStatusForUser() {
        Map<String, CopyRequest> copyRequests = Maps.newHashMap(USERNAME, spyCopyRequest);
        when(mockCopy.getCopyRequests()).thenReturn(copyRequests);
        when(spyCopyRequest.getRequestStatus()).thenReturn(CopyRequest.Status.COLLECTED);

        Optional<CopyRequest.Status> requestStatus = copyService.getRequestStatusForUser(mockCopy, USERNAME);

        assertThat(requestStatus)
                .as("Request status retrieved from Copy for given user")
                .isEqualTo(Optional.of(CopyRequest.Status.COLLECTED));
    }

    @Test
    public void testGetRequestStatusForUser_NoRequest() {
        when(mockCopy.getCopyRequests()).thenReturn(Collections.emptyMap());

        Optional<CopyRequest.Status> requestStatus = copyService.getRequestStatusForUser(mockCopy, USERNAME);

        assertThat(requestStatus)
                .as("No request status retrieved from Copy for given user")
                .isEqualTo(Optional.empty());
    }

}
