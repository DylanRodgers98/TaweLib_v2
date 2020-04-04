package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.dao.UserDAO;
import com.crowvalley.tawelib.model.user.Librarian;
import com.crowvalley.tawelib.model.user.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    private static final String USERNAME = "TEST_USER";

    private static final Long STAFF_NUM = 1L;

    @Mock
    private User mockUser;

    @Mock
    private Librarian mockLibrarian;

    @Mock
    private UserDAO mockUserDAO;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void testGetWithUsername() {
        when(mockUserDAO.getWithUsername(USERNAME)).thenReturn(Optional.of(mockUser));

        assertThat(userService.getWithUsername(USERNAME))
                .as("User retrieved from database")
                .isEqualTo(Optional.of(mockUser));
    }

    @Test
    public void testGetWithUsername_NoUserReturned() {
        when(mockUserDAO.getWithUsername(USERNAME)).thenReturn(Optional.empty());

        assertThat(userService.getWithUsername(USERNAME))
                .as("No user retrieved from database")
                .isEqualTo(Optional.empty());
    }

    @Test
    public void testGetLibrarianUserWithStaffNumber() {
        when(mockUserDAO.getLibrarianUserWithStaffNumber(STAFF_NUM)).thenReturn(Optional.of(mockLibrarian));

        assertThat(userService.getLibrarianUserWithStaffNumber(STAFF_NUM))
                .as("Librarian retrieved from database using staff number")
                .isEqualTo(Optional.of(mockLibrarian));
    }

    @Test
    public void testGetLibrarianUserWithStaffNumber_NoLibrarianReturned() {
        when(mockUserDAO.getLibrarianUserWithStaffNumber(STAFF_NUM)).thenReturn(Optional.empty());

        assertThat(userService.getLibrarianUserWithStaffNumber(STAFF_NUM))
                .as("No librarian retrieved from database")
                .isEqualTo(Optional.empty());
    }

    @Test
    public void testGetAll() {
        when(mockUserDAO.getAll(User.class)).thenReturn(Arrays.asList(mockUser, mockLibrarian));

        List<? extends User> expectedUsers = Arrays.asList(mockUser, mockLibrarian);

        assertThat(userService.getAll())
                .as("All Users and Librarians retrieved from database")
                .isEqualTo(expectedUsers);
    }

    @Test
    public void testGetAllUsernames() {
        when(mockUserDAO.getAllUsernames()).thenReturn(Collections.singletonList(USERNAME));

        assertThat(userService.getAllUsernames())
                .as("All usernames retrieved from database")
                .containsExactly(USERNAME);
    }

    @Test
    public void testSearch() {
        when(mockUserDAO.search(USERNAME)).thenReturn(Collections.singletonList(mockUser));

        assertThat(userService.search(USERNAME))
                .as("One user found when searched with username")
                .containsExactly(mockUser);
    }

    @Test
    public void testSaveOrUpdate() {
        userService.saveOrUpdate(mockUser);
        verify(mockUserDAO).saveOrUpdate(mockUser);
    }

    @Test
    public void testDelete() {
        userService.delete(mockUser);
        verify(mockUserDAO).delete(mockUser);
    }

}
