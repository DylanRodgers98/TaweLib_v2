package com.crowvalley.tawelib.service;

import com.crowvalley.tawelib.dao.UserDAOImpl;
import com.crowvalley.tawelib.model.user.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @Mock
    private UserDAOImpl DAO;

    @InjectMocks
    private UserServiceImpl userService;

    private User user1;

    private User user2;

    private User user3;

    private Optional<User> optionalUser1;

    private Optional<User> optionalUser2;

    private Optional<User> optionalUser3;

    private List<User> users;

    @Before
    public void setup() {
        user1 = new User("User 1", "", "", "", "", "", "", "", "", "");
        user2 = new User("User 2", "", "", "", "", "", "", "", "", "");
        user3 = new User("User 3", "", "", "", "", "", "", "", "", "");
        optionalUser1 = Optional.of(user1);
        optionalUser2 = Optional.of(user2);
        optionalUser3 = Optional.of(user3);
        users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);
    }

    @Test
    public void testGet() {
        String username = "User 1";

        when(DAO.get(username)).thenReturn(optionalUser1);

        assertThat(userService.get(username)).as("Retrieve user from database with username 'User 1'").isEqualTo(optionalUser1);
    }

    @Test
    public void testGet_noUserFromDAO() {
        String username = "User 4";

        when(DAO.get(username)).thenReturn(Optional.empty());

        assertThat(userService.get(username)).as("Retrieve empty Optional from DAO").isEqualTo(Optional.empty());
    }

    @Test
    public void testGetAll() {
        when(DAO.getAll()).thenReturn(users);

        assertThat(userService.getAll()).as("Retrieve all users stored in the database").isEqualTo(users);
    }

    @Test
    public void testGetAll_noUsersFromDAO() {
        when(DAO.getAll()).thenReturn(new ArrayList<>());

        assertThat(userService.getAll().isEmpty()).as("Retrieve no users from DAO").isTrue();
    }

    @Test
    public void test_verifySave() {
        userService.save(user1);
        verify(DAO).save(eq(user1));
    }

    @Test
    public void test_verifyUpdate() {
        userService.update(user2);
        verify(DAO).update(eq(user2));
    }

    @Test
    public void test_verifyDelete() {
        userService.delete(user3);
        verify(DAO).delete(eq(user3));
    }
}
