package com.crowvalley.tawelib.dao;

import com.crowvalley.tawelib.model.user.Librarian;
import com.crowvalley.tawelib.model.user.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/spring/applicationContext.xml")
@Transactional
public class UserDAOIT {

    private static final String USERNAME_1 = "TEST_USER1";

    private static final String USERNAME_2 = "TEST_USER2";

    @Autowired
    private UserDAO userDAO;

    @Test
    public void testGetAllUsernames() {
        User user1 = new User();
        user1.setUsername(USERNAME_1);
        userDAO.saveOrUpdate(user1);

        User user2 = new User();
        user2.setUsername(USERNAME_2);
        userDAO.saveOrUpdate(user2);

        List<String> usernames = userDAO.getAllUsernames();

        assertThat(usernames)
                .as("List of all usernames retrieved from database contains usernames of newly persisted User entities")
                .contains(USERNAME_1, USERNAME_2);
    }

    @Test
    public void testGetWithUsername() {
        User user = new User();
        user.setUsername(USERNAME_1);
        userDAO.saveOrUpdate(user);

        Optional<User> optionalUser = userDAO.getWithUsername(USERNAME_1);
        assertThat(optionalUser)
                .as("User retrieved from database using username")
                .isPresent();

        User userFromDatabase = optionalUser.get();
        assertThat(userFromDatabase)
                .as("User entity retrieved from database is equal to updated User object persisted to database")
                .isEqualTo(user);
    }

    @Test
    public void testGetLibrarianUserWithStaffNumber() {
        Librarian librarian = new Librarian();
        librarian.setUsername(USERNAME_2);
        userDAO.saveOrUpdate(librarian);

        Long staffNumber = librarian.getStaffNum();

        Optional<Librarian> optionalLibrarian = userDAO.getLibrarianUserWithStaffNumber(staffNumber);
        assertThat(optionalLibrarian)
                .as("Librarian retrieved from database using staff number")
                .isPresent();

        User librarianFromDatabase = optionalLibrarian.get();
        assertThat(librarianFromDatabase)
                .as("Librarian entity retrieved from database is equal to updated Librarian object persisted to database")
                .isEqualTo(librarian);
    }

    @Test
    public void testSearch() {
        User user1 = new User();
        user1.setUsername(USERNAME_1);
        userDAO.saveOrUpdate(user1);

        User user2 = new User();
        user2.setUsername(USERNAME_2);
        userDAO.saveOrUpdate(user2);

        List<User> users = userDAO.search(USERNAME_1);
        assertThat(users)
                .as("List of Users searched using username contains user1")
                .contains(user1);

        users = userDAO.search(USERNAME_2);
        assertThat(users)
                .as("List of Users searched using username contains user2")
                .contains(user2);
    }

}
